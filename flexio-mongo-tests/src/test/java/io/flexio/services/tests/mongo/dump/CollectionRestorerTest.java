package io.flexio.services.tests.mongo.dump;

import com.mongodb.client.MongoClient;
import io.flexio.docker.DockerResource;
import io.flexio.services.tests.mongo.MongoResource;
import io.flexio.services.tests.mongo.MongoTest;
import org.bson.Document;
import org.hamcrest.Matchers;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;

public class CollectionRestorerTest {

    public static final String DB = "test-db";
    @ClassRule
    static public DockerResource docker = MongoTest.docker()
            .started().finallyStarted();

    @Rule
    public MongoResource mongo = MongoTest.mongo(() -> docker)
            .testDB(DB);


    @Test
    public void givenOneCollectionResourceToRestore__whenTargetingDbAndCollection__thenCollectionContentRestored() throws Exception {
        String targetDb = DB;
        String targetCollection = "flex-users";
        String resource = "flx1_dev_legacy-tests/" + targetCollection + ".bson";

        try (MongoClient client = this.mongo.newClient(); InputStream in = this.resourceStream(resource)) {
            new CollectionRestorer(client, in).restoreTo(targetDb, targetCollection);
        }

        try (MongoClient client = this.mongo.newClient()) {
            assertThat(client.getDatabase(DB).getCollection(targetCollection).countDocuments(), Matchers.is(4L));

            System.out.println("\n\n\n\n###################################################################");
            for (Document dbDocument : client.getDatabase(DB).getCollection(targetCollection).find()) {
                System.out.println(dbDocument);
            }
            System.out.println("###################################################################\n\n\n\n");
        }
    }

    private InputStream resourceStream(String resource) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
    }
}