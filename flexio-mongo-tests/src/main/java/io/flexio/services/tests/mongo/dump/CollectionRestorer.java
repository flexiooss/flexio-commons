package io.flexio.services.tests.mongo.dump;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class CollectionRestorer {
    static private final Logger log = LoggerFactory.getLogger(CollectionRestorer.class);

    private final MongoClient client;
    private final InputStream in;
    private final BSONDecoder decoder = new BasicBSONDecoder();

    public CollectionRestorer(MongoClient client, InputStream in) {
        this.client = client;
        this.in = in;
    }

    public void restoreTo(String targetDb, String targetCollection) throws IOException {
        MongoDatabase db = client.getDatabase(targetDb);

        if (collectionExists(targetCollection, db)) {
            db.getCollection(targetCollection).drop();
        }

        while (in.available() > 0) {
            BSONObject obj = decoder.readObject(in);
            db.getCollection(targetCollection).withDocumentClass(DBObject.class).insertOne(new BasicDBObject(obj.toMap()));
        }
    }

    private boolean collectionExists(String collectionName, MongoDatabase db) {
        for (final String name : db.listCollectionNames()) {
            if (name.equalsIgnoreCase(collectionName)) {
                return true;
            }
        }
        return false;
    }
}
