package io.flexio.services.tests.mongo.dump;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import org.bson.BSONDecoder;
import org.bson.BSONObject;
import org.bson.BasicBSONDecoder;

import java.io.IOException;
import java.io.InputStream;

public class CollectionRestorer {
    private final MongoClient client;
    private final InputStream in;
    private final BSONDecoder decoder = new BasicBSONDecoder();

    public CollectionRestorer(MongoClient client, InputStream in) {
        this.client = client;
        this.in = in;
    }

    public void restoreTo(String targetDb, String targetCollection) throws IOException {
        com.mongodb.DB db = client.getDB(targetDb);
        if(db.collectionExists(targetCollection)) {
            db.getCollection(targetCollection).drop();
        }

        while (in.available() > 0) {
            BSONObject obj = decoder.readObject(in);
            db.getCollection(targetCollection).insert(new BasicDBObject(obj.toMap()));
        }
    }
}
