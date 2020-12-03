package io.flexio.io.mongo.repository;

import org.bson.conversions.Bson;

@FunctionalInterface
public interface BsonFromQueryProvider<Q> {
    Bson from(Q query) throws Exception;
}
