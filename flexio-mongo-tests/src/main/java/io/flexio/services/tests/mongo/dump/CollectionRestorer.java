package io.flexio.services.tests.mongo.dump;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import org.bson.BSONDecoder;
import org.bson.BSONObject;
import org.bson.BasicBSONDecoder;
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
        log.debug("CollectionRestorer :: restoreTo : GETDB {}", targetCollection);
        com.mongodb.DB db = client.getDB(targetDb);

        log.debug("CollectionRestorer :: restoreTo : DROP COLLECTOIN {}", targetCollection);
        if(db.collectionExists(targetCollection)) {
            db.getCollection(targetCollection).drop();
        }

        log.debug("CollectionRestorer :: restoreTo : INSERT {}", targetCollection);
        while (in.available() > 0) {
            BSONObject obj = decoder.readObject(in);
            db.getCollection(targetCollection).insert(new BasicDBObject(obj.toMap()));
        }
        log.debug("CollectionRestorer :: restoreTo : DONE {}", targetCollection);
    }
}
