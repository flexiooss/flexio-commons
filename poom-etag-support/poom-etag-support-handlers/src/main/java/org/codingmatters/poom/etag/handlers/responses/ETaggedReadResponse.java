package org.codingmatters.poom.etag.handlers.responses;

import org.codingmatters.poom.etag.handlers.exception.UnETaggable;

import java.util.HashMap;
import java.util.Map;

public class ETaggedReadResponse<T> extends ETaggedResponse<T> {

    public static <T> ETaggedReadResponse<T> from(T response) throws UnETaggable {
        Class<? extends T> responseValueObject = ReflecHelper.lookupValueObject(response.getClass());
        return new ETaggedReadResponse<>(responseValueObject, ReflecHelper.asMap(response, responseValueObject));
    }

    public static <T> ETaggedReadResponse<T> create304(Class<? extends T> responseType, String xEntityId, String eTag, String cacheControl) {
        Map<String, Object> responseMap = new HashMap<>();
        Map<String, Object> status304 = new HashMap<>();
        status304.put("x-entity-id", xEntityId);
        status304.put("ETag", eTag);
        status304.put("Cache-Control", cacheControl);

        responseMap.put("status304", status304);

        return new ETaggedReadResponse<>(responseType, responseMap);
    }

    public static <T> ETaggedReadResponse<T> create500(Class responseType, String errorToken) {
        Map<String, Object> responseMap = new HashMap<>();
        Map<String, Object> status500 = new HashMap<>();
        status500.put("errorToken", errorToken);
        responseMap.put("status500", status500);

        return new ETaggedReadResponse<>(responseType, responseMap);
    }


    private ETaggedReadResponse(Class<? extends T> responseClass, Map<String, Object> responseAsMap) {
        super(responseClass, responseAsMap);
    }

    public ETaggedReadResponse<T> xEntityId(String changed) {
        this.status().put("x-entity-id", changed);
        return this;
    }

    public ETaggedReadResponse<T> eTag(String changed) {
        this.status().put("ETag", changed);
        return this;
    }

    public ETaggedReadResponse<T> cacheControl(String changed) {
        this.status().put("Cache-Control", changed);
        return this;
    }

}
