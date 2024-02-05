package io.flexio.io.mongo.repository.property.query;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import io.flexio.docker.DockerResource;
import io.flexio.services.tests.mongo.MongoResource;
import io.flexio.services.tests.mongo.MongoTest;
import org.bson.Document;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RawMongoDbTest {
    public static final String DB = "test-db";
    public static final String COLLECTION = "test-collection";
    @ClassRule
    static public DockerResource docker = MongoTest.docker()
            .started().finallyStarted();

    @Rule
    public MongoResource mongo = MongoTest.mongo(() -> docker)
            .testDB(DB)
            ;


    @Test
    public void inSemantic() throws Exception {
        try(MongoClient client = this.mongo.newClient()) {
            MongoCollection<Document> collection = client.getDatabase(DB).getCollection(COLLECTION);

            collection.insertOne(new Document().append("k", "v1"));
            collection.insertOne(new Document().append("k", "v2"));
            collection.insertOne(new Document().append("k", "v3"));
            collection.insertOne(new Document().append("k", null));
            collection.insertOne(new Document());

            System.out.println(collection.find().into(new ArrayList<>()));

            System.out.println(collection.find(Filters.in("k", new Object[] {"v1", "v2"})).into(new ArrayList<>()));
            assertThat(
                    collection.find(Filters.in("k", new Object[] {"v1", "v2"})).into(new ArrayList<>()),
                    hasSize(2)
            );
            System.out.println(collection.find(Filters.in("k", new Object[0])).into(new ArrayList<>()));
            assertThat(
                    collection.find(Filters.in("k", new Object[0])).into(new ArrayList<>()),
                    hasSize(0)
            );
            System.out.println(collection.find(Filters.in("k", new Object[] {null})).into(new ArrayList<>()));
            assertThat(
                    collection.find(Filters.in("k", new Object[] {null})).into(new ArrayList<>()),
                    hasSize(2)
            );
            System.out.println(collection.find(Filters.in("k", new Object[] {"v1", null})).into(new ArrayList<>()));
            assertThat(
                    collection.find(Filters.in("k", new Object[] {"v1", null})).into(new ArrayList<>()),
                    hasSize(3)
            );
        }
    }
}
