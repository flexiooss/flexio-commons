package io.flexio.services.tests.mongo.dump;

import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DatabaseRestorer {
    static private final Logger log = LoggerFactory.getLogger(DatabaseRestorer.class);

    private final MongoClient client;
    private final DumpReader dumpReader;

    public DatabaseRestorer(MongoClient client, DumpReader dumpReader) {
        this.client = client;
        this.dumpReader = dumpReader;
    }

    public void restoreTo(String targetDb) throws IOException {
        this.client.getDatabase(targetDb).drop();

        log.debug("RESTORE TO START");
        for (String collection : this.dumpReader.collections()) {
            new CollectionRestorer(this.client, this.dumpReader.collectionData(collection)).restoreTo(targetDb, collection);
        }
        log.debug("RESTORE TO END");
    }
}
