package org.codingmatters.poom.etag.repository;

import com.mongodb.client.MongoClient;
import io.flexio.io.mongo.repository.MongoCollectionRepository;
import org.codingmatters.poom.etag.storage.Etag;
import org.codingmatters.poom.etag.storage.mongo.EtagMongoMapper;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.repositories.Repository;

public class EtagMongoRepository {

    private final MongoClient mongoClient;

    public EtagMongoRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Repository<Etag, PropertyQuery> at(String database, String collectionName) {
        EtagMongoMapper mapper = new EtagMongoMapper();

        return MongoCollectionRepository.<Etag, PropertyQuery>repository(database, collectionName)
                .withToDocument(mapper::toDocument)
                .withToValue(mapper::toValue)
                .buildWithPropertyQuery(mongoClient);
    }
}
