package org.codingmatters.poom.etag.handlers;

import org.codingmatters.poom.etag.api.ETaggedChangeRequest;
import org.codingmatters.poom.etag.api.ETaggedReadRequest;
import org.codingmatters.poom.etag.handlers.exception.UnETaggable;
import org.codingmatters.poom.etag.handlers.responses.ETaggedChangeResponse;
import org.codingmatters.poom.etag.handlers.responses.ETaggedReadResponse;
import org.codingmatters.poom.etag.storage.Etag;
import org.codingmatters.poom.services.domain.exceptions.RepositoryException;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.logging.CategorizedLogger;
import org.codingmatters.poom.services.support.date.UTC;
import org.codingmatters.poom.services.domain.entities.Entity;
import org.codingmatters.poom.services.domain.entities.PagedEntityList;

import java.util.function.Function;

public class ETaggedChange<Request extends ETaggedChangeRequest, Response> implements Function<Request, Response> {
    static private final CategorizedLogger log = CategorizedLogger.getLogger(ETaggedChange.class);

    static public <Request extends ETaggedChangeRequest, Response> IdFromRequestBuilder<Request, Response> forHandler(Function<Request, Response> handler, Class<? extends Response> responseType) {
        return new IdFromRequestBuilder<>(handler, responseType);
    }

    static public class IdFromRequestBuilder<Request extends ETaggedChangeRequest, Response> {
        private final Function<Request, Response> delegate;
        private final Class<? extends Response> responseType;

        private IdFromRequestBuilder(Function<Request, Response> delegate, Class<? extends Response> responseType) {
            this.delegate = delegate;
            this.responseType = responseType;
        }

        public IdFromRequestBuilder.Builder<Request, Response> idFromRequest(Function<Request, String> idFromRequest) {
            return new IdFromRequestBuilder.Builder<>(idFromRequest, this.delegate, this.responseType);
        }

        static public class Builder<Request extends ETaggedChangeRequest, Response> {

            private final Function<Request, String> idFromRequest;
            private final Function<Request, Response> delegate;
            private final Class<? extends Response> responseType;
            private String defaultCacheControl = "must-revalidate";

            private Builder(Function<Request, String> idFromRequest, Function<Request, Response> delegate, Class<? extends Response> responseType) {
                this.idFromRequest = idFromRequest;
                this.delegate = delegate;
                this.responseType = responseType;
            }

            public IdFromRequestBuilder.Builder<Request, Response> defaultCacheControl(String defaultCacheControl) {
                this.defaultCacheControl = defaultCacheControl;
                return this;
            }

            public ETaggedChange<Request, Response> using(Repository<Etag, PropertyQuery> etagRepository) {
                return new ETaggedChange<>(etagRepository, this.defaultCacheControl, this.delegate, this.responseType, this.idFromRequest);
            }
        }
    }

    private final Repository<Etag, PropertyQuery> etagRepository;
    private final String defaultCacheControl;
    private final Function<Request, Response> delegate;
    private final Class<? extends Response> responseType;
    private final Function<Request, String> idFromRequest;

    private ETaggedChange(
            Repository<Etag, PropertyQuery> etagRepository,
            String defaultCacheControl,
            Function<Request, Response> delegate,
            Class<? extends Response> responseType,
            Function<Request, String> idFromRequest
    ) {
        this.etagRepository = etagRepository;
        this.defaultCacheControl = defaultCacheControl;
        this.delegate = delegate;
        this.responseType = responseType;
        this.idFromRequest = idFromRequest;
    }

    @Override
    public Response apply(Request request) {
        if(request.ifMatch() != null && ! request.ifMatch().isEmpty()) {
            try {
                String id = this.idFromRequest.apply(request);
                PagedEntityList<Etag> stored = this.etagRepository.search(PropertyQuery.builder()
                        .filter("id == '%s' && etag == '%s'", id, request.ifMatch())
                        .build(), 0, 0);
                if(stored.total() == 0) {
                    try {
                        return (Response) ETaggedChangeResponse.create412(
                                this.responseType, log.tokenized().info("request etag doesn't match current entity's")
                        ).response();
                    } catch (UnETaggable e) {
                        return (Response) ETaggedChangeResponse.create500(this.responseType, log.tokenized().error("failed creating 304 response"));
                    }
                } else {
                    return this.delegateAndStoreEtag(request);
                }
            } catch (RepositoryException e) {
                return (Response) ETaggedChangeResponse.create500(this.responseType, log.tokenized().error("failed getting etag", e));
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
            Entity<Etag> current = this.etagRepository.retrieve(id);
            if(current == null) {
                this.etagRepository.createWithId(id, Etag.builder()
                        .etag(etagged.eTag())
                        .id(id)
                        .cacheControl(etagged.cacheControl())
                        .created(UTC.now())
                        .build());
            } else {
                this.etagRepository.update(current, current.value().withEtag(etagged.eTag()));
            }
            return etagged.response();
        } catch (UnETaggable e) {
            return (Response) ETaggedReadResponse.create500(this.responseType, log.tokenized().error("failed creating etagged response from delegate response : " + response, e));
        } catch (RepositoryException e) {
            return (Response) ETaggedReadResponse.create500(this.responseType, log.tokenized().error("failed storing etag", e));
        }
    }
}
