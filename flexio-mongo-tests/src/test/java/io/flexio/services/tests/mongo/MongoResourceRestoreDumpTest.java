package io.flexio.services.tests.mongo;

import com.mongodb.MongoClient;
import io.flexio.docker.DockerResource;
import org.hamcrest.Matchers;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class MongoResourceRestoreDumpTest {

    public static final String DB = "test-db";
    @ClassRule
    static public DockerResource docker = MongoTest.docker()
            .started().finallyStarted();

    @Rule
    public MongoResource mongo = MongoTest.mongo(() -> docker)
            .testDBFromDump(DB, "flx1_dev_legacy-tests");

    @Test
    public void givenResourceDumpDirExists__whenCreatingTestDatabaseFromDump__thenDBCrteatedWithContent() throws Exception {
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
