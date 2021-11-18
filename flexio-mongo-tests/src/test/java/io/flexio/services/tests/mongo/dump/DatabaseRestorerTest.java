package io.flexio.services.tests.mongo.dump;

import com.mongodb.client.MongoClient;
import io.flexio.docker.DockerResource;
import io.flexio.services.tests.mongo.MongoResource;
import io.flexio.services.tests.mongo.MongoTest;
import org.hamcrest.Matchers;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class DatabaseRestorerTest {

    public static final String DB = "test-db";
    @ClassRule
    static public DockerResource docker = MongoTest.docker()
            .started().finallyStarted();

    @Rule
    public MongoResource mongo = MongoTest.mongo(() -> docker)
            .testDB(DB);

    @Test
    public void givenResourceDumpDirExists__whenRestoringDatabase__thenAllCollectionsAreRestored() throws Exception {
        try(MongoClient client = this.mongo.newClient()) {
            new DatabaseRestorer(client, new ResourceDumpReader("flx1_dev_legacy-tests")).restoreTo(DB);
        }

        try(MongoClient client = this.mongo.newClient()) {
            assertThat(
                    client.getDatabase(DB).listCollectionNames(),
                    Matchers.containsInAnyOrder(
                            "flex-account", "flex-channels", "flex-dashboards", "flex-information_types",
                            "flex-userprofile", "flex-users", "flex-usersgroups", "flexlogcategory", "flexlogs"
                    )
            );
        }
    }
}