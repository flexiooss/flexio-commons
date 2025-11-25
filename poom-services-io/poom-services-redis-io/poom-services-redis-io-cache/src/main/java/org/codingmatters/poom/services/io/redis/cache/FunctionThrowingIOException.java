package org.codingmatters.poom.services.io.redis.cache;

import java.io.IOException;

@FunctionalInterface
public interface FunctionThrowingIOException<T, R> {
    R apply(T var1) throws IOException;
}
