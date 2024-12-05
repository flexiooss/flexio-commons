package org.codingmatters.poom.etag.handlers.responses;

import org.codingmatters.poom.etag.handlers.exception.UnETaggable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

public class ETaggedResponse<T> {

    private final Class<? extends T> responseClass;
    private final Map<String, Object> responseAsMap;

    protected ETaggedResponse(Class<? extends T> responseClass, Map<String, Object> responseAsMap) {
        this.responseClass = responseClass;
        this.responseAsMap = responseAsMap;
    }

    public String xEntityId() {
        return (String) this.status().get("x-entity-id");
    }

    public String eTag() {
        return (String) this.status().get("ETag");
    }

    public String cacheControl() {
        return (String) this.status().get("Cache-Control");
    }


    protected Map<Object, Object> status() {
        Optional<String> status = this.responseAsMap.keySet().stream().filter(key -> key.startsWith("status") && this.responseAsMap.get(key) != null).findFirst();
        if(status.isPresent()) {
            return (Map<Object, Object>) this.responseAsMap.get(status.get());
        } else {
            return null;
        }
    }

    public T response() throws UnETaggable {
        try {
            Method builderMethod = this.responseClass.getMethod("fromMap", Map.class);
            Object builder = builderMethod.invoke(null, this.responseAsMap);
            Method buildMethod = builder.getClass().getMethod("build");

            return (T) buildMethod.invoke(builder);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new UnETaggable("failed building etagged response", e);
        }
    }
}
