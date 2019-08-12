package io.flexio.services.tests.mongo.dump;

import com.mongodb.MongoClient;

import java.io.IOException;

public class DatabaseRestorer {

    private final MongoClient client;
    private final DumpReader dumpReader;

    public DatabaseRestorer(MongoClient client, DumpReader dumpReader) {
        this.client = client;
        this.dumpReader = dumpReader;
    }

    public void restoreTo(String targetDb) throws IOException {
        this.client.getDatabase(targetDb).drop();

        for (String collection : this.dumpReader.collections()) {
            new CollectionRestorer(this.client, this.dumpReader.collectionData(collection)).restoreTo(targetDb, collection);
        }

    }
}
