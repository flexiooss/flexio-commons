package org.codingmatters.poom.etag.handlers;

import org.codingmatters.poom.etag.api.ETaggedReadRequest;
import org.codingmatters.poom.etag.handlers.exception.UnETaggable;
import org.codingmatters.poom.etag.handlers.responses.ETaggedReadResponse;
import org.codingmatters.poom.etag.storage.Etag;
import org.codingmatters.poom.services.domain.entities.Entity;
import org.codingmatters.poom.services.domain.exceptions.RepositoryException;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.logging.CategorizedLogger;
import org.codingmatters.poom.services.support.date.UTC;
import org.codingmatters.poom.services.domain.entities.PagedEntityList;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class ETaggedRead<Request extends ETaggedReadRequest, Response> implements Function<Request, Response> {
    static private final CategorizedLogger log = CategorizedLogger.getLogger(ETaggedRead.class);

    static public <Request extends ETaggedReadRequest, Response> IdFromRequestBuilder<Request, Response> forHandler(Function<Request, Response> handler, Class<? extends Response> responseType) {
        return new IdFromRequestBuilder<>(handler, responseType);
    }

    static public class IdFromRequestBuilder<Request extends ETaggedReadRequest, Response> {
        private final Function<Request, Response> delegate;
        private final Class<? extends Response> responseType;

        private IdFromRequestBuilder(Function<Request, Response> delegate, Class<? extends Response> responseType) {
            this.delegate = delegate;
            this.responseType = responseType;
        }

        public Builder<Request, Response> idFromRequest(Function<Request, String> idFromRequest) {
            return new Builder<>(idFromRequest, this.delegate, this.responseType);
        }

        static public class Builder<Request extends ETaggedReadRequest, Response> {

            private final Function<Request, String> idFromRequest;
            private final Function<Request, Response> delegate;
            private final Class<? extends Response> responseType;
            private String defaultCacheControl = "must-revalidate";

            private Builder(Function<Request, String> idFromRequest, Function<Request, Response> delegate, Class<? extends Response> responseType) {
                this.idFromRequest = idFromRequest;
                this.delegate = delegate;
                this.responseType = responseType;
            }

            public Builder<Request, Response> defaultCacheControl(String defaultCacheControl) {
                this.defaultCacheControl = defaultCacheControl;
                return this;
            }

            public Builder<Request, Response> maxAgeDefaultCacheControl(long delay, TimeUnit unit) {
                return this.defaultCacheControl(String.format("max-age=%d", unit.toSeconds(delay)));
            }

            public ETaggedRead<Request, Response> using(Repository<Etag, PropertyQuery> etagRepository) {
                return new ETaggedRead<>(etagRepository, this.defaultCacheControl, this.delegate, this.responseType, this.idFromRequest);
            }
        }
    }

    private final Repository<Etag, PropertyQuery> etagRepository;
    private final String defaultCacheControl;
    private final Function<Request, Response> delegate;
    private Class<? extends Response> responseType;
    private final Function<Request, String> idFromRequest;

    private ETaggedRead(
            Repository<Etag, PropertyQuery> etagRepository,
            String defaultCacheControl,
            Function<Request, Response> delegate,
            Class<? extends Response> responseType,
            Function<Request, String> idFromRequest) {
        this.etagRepository = etagRepository;
        this.defaultCacheControl = defaultCacheControl;
        this.delegate = delegate;
        this.responseType = responseType;
        this.idFromRequest = idFromRequest;
    }

    @Override
    public Response apply(Request request) {
        if(request.ifNoneMatch() != null && ! request.ifNoneMatch().isEmpty()) {
            try {
                String id = this.idFromRequest.apply(request);
                PagedEntityList<Etag> stored = this.etagRepository.search(PropertyQuery.builder()
                        .filter("id == '%s' && etag == '%s'", id, request.ifNoneMatch())
                        .build(), 0, 0);
                if(stored.total() != 0) {
                    try {
                        return (Response) ETaggedReadResponse.create304(
                                this.responseType,
                                stored.get(0).value().id(),
                                stored.get(0).value().etag(),
                                stored.get(0).value().cacheControl()
                                ).response();
                    } catch (UnETaggable e) {
                        return (Response) ETaggedReadResponse.create500(this.responseType, log.tokenized().error("failed creating 304 response"));
                    }
                } else {
                    return this.delegateAndStoreEtag(request);
                }
            } catch (RepositoryException e) {
                return (Response) ETaggedReadResponse.create500(this.responseType, log.tokenized().error("failed getting etag", e));
            }
        } else {
            return this.delegateAndStoreEtag(request);
        }
    }

    private Response delegateAndStoreEtag(Request request) {
        Response response = this.delegate.apply(request);
        try {
            String id = this.idFromRequest.apply(request);
            ETaggedReadResponse<Response> etagged = ETaggedReadResponse.from(response);
            if(etagged.cacheControl() == null) {
                etagged.cacheControl(this.defaultCacheControl);
            }
            Entity<Etag> etagEntity = this.etagRepository.retrieve(id);
            Etag toStoreEtag = Etag.builder()
                    .etag(etagged.eTag())
                    .id(etagged.xEntityId())
                    .cacheControl(etagged.cacheControl())
                    .created(UTC.now())
                    .build();
            if(etagEntity == null) {
                this.etagRepository.createWithId(id, toStoreEtag);
            } else {
                this.etagRepository.update(etagEntity, toStoreEtag);
            }
            return etagged.response();
        } catch (UnETaggable e) {
            return (Response) ETaggedReadResponse.create500(this.responseType, log.tokenized().error("failed creating etagged response from delegate response : " + response, e));
        } catch (RepositoryException e) {
            return (Response) ETaggedReadResponse.create500(this.responseType, log.tokenized().error("failed storing etag", e));
        }
    }

}
