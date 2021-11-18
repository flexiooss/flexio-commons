package io.flexio.services.tests.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import io.flexio.docker.DockerResource;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static io.flexio.services.tests.mongo.MongoTest.MONGO;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MongoResourceTest {

    static private final Logger log = LoggerFactory.getLogger(MongoResourceTest.class);

    @ClassRule
    static public DockerResource docker = MongoTest.docker()
            .started().finallyStarted();


    private MongoResource mongoResource;

    @Before
    public void setUp() throws Exception {
        this.mongoResource = new MongoResource(
                () -> docker.containerInfo(MONGO).networkSettings().iPAddress().orElseThrow(() -> new AssertionError("no ip address for container")),
                27017
        );
    }

    private MongoClient rawClient() {
        return MongoClients.create(
                String.format("mongodb://%s:%s",
                        docker.containerInfo(MONGO).networkSettings().iPAddress().orElseThrow(() -> new AssertionError("no ip address for container")),
                        27017)
        );
    }

    @Test
    public void givenTestDbSetup_whenDbCreate_thenAfterResourceDbIsDropped() throws Throwable {
        String db = UUID.randomUUID().toString();
        this.mongoResource.testDB(db);
        this.applyMongoResource(() -> {
            MongoClient client = this.mongoResource.newClient();
            MongoDatabase dataBase = client.getDatabase(db);
            dataBase.createCollection("test-collection");

            try (MongoClient rawClient = this.rawClient()) {
                assertThat(rawClient.listDatabaseNames(), hasItem(db));
            }
        });

        try (MongoClient rawClient = this.rawClient()) {
            assertThat(rawClient.listDatabaseNames(), not(hasItem(db)));
        }
    }

    @Test
    public void givenDbNotATestDb_whenDbCreated_thenAfterResourceDbIsNotDropped() throws Throwable {
        String db = UUID.randomUUID().toString();
        this.applyMongoResource(() -> {
            MongoClient client = this.mongoResource.newClient();
            MongoDatabase dataBase = client.getDatabase(db);
            dataBase.createCollection("test-collection");

            try (MongoClient rawClient = this.rawClient()) {
                assertThat(rawClient.listDatabaseNames(), hasItem(db));
            }
        });

        try (MongoClient rawClient = this.rawClient()) {
            assertThat(rawClient.listDatabaseNames(), hasItem(db));
            rawClient.getDatabase(db).drop();
        }
    }

    @Test
    public void givenCollectionMarkedAsTest_whenCollectionCreated_thenAfterResourceCollectonIsDroppedButNotDb() throws Throwable {
        String db = UUID.randomUUID().toString();
        this.mongoResource.testCollection(db, "test-collection");

        try (MongoClient rawClient = this.rawClient()) {
            rawClient.getDatabase(db).createCollection("resilient");
            assertThat(rawClient.listDatabaseNames(), hasItem(db));
        }

        this.applyMongoResource(() -> {
            MongoClient client = this.mongoResource.newClient();
            client.getDatabase(db).createCollection("test-collection");

            try (MongoClient rawClient = this.rawClient()) {
                assertThat(rawClient.listDatabaseNames(), hasItem(db));
                assertThat(rawClient.getDatabase(db).listCollectionNames(), hasItem("test-collection"));
            }
        });

        try (MongoClient rawClient = this.rawClient()) {
            assertThat(rawClient.getDatabase(db).listCollectionNames(), not(hasItem("test-collection")));
            assertThat(rawClient.getDatabase(db).listCollectionNames(), hasItem("resilient"));
            rawClient.getDatabase(db).drop();
        }
    }

    @Test
    public void importCollectionFromMongoExport() throws Throwable {
        String db = UUID.randomUUID().toString();
        this.mongoResource
                .testDB(db)
                .importCollectionContent("mongo-export.json", db, "imported-collection");

        this.applyMongoResource(() -> {
            try (MongoClient rawClient = this.rawClient()) {
                assertThat(rawClient.getDatabase(db).listCollectionNames(), hasItem("imported-collection"));
                assertThat(rawClient.getDatabase(db).getCollection("imported-collection").countDocuments(), is(11L));
            }
        });
    }

    private void applyMongoResource(Verify verify) throws Throwable {
        this.mongoResource.apply(this.verify(verify), null).evaluate();
    }


    private Statement verify(Verify verify) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                verify.verify();
            }
        };
    }

    @FunctionalInterface
    interface Verify {
        void verify();
    }
}