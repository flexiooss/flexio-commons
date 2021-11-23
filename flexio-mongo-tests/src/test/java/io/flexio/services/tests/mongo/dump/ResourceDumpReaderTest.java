package io.flexio.services.tests.mongo.dump;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResourceDumpReaderTest {

    @Test
    public void givenResourceDumpDirExists__whenListingCollections__thenCollectionNamesAreBuildedFromBsonFilenames() throws Exception {
        ResourceDumpReader reader = new ResourceDumpReader("flx1_dev_legacy-tests");

        assertThat(
                reader.collections(),
                containsInAnyOrder(
                        "flex-account", "flex-channels", "flex-dashboards", "flex-information_types",
                        "flex-userprofile", "flex-users", "flex-usersgroups", "flexlogcategory", "flexlogs")
        );
    }

    @Test
    public void givenResourceDumpDirExists__whenGettingollectionData__thenStreamOpensToCollectionDump() throws Exception {
        assertThat(
                this.content(new ResourceDumpReader("flx1_dev_legacy-tests").collectionData("flex-users")),
                is(this.content(Thread.currentThread().getContextClassLoader().getResourceAsStream("flx1_dev_legacy-tests/flex-users.bson")))
        );

    }

    private String content(InputStream stream) throws IOException {
        StringBuilder result = new StringBuilder();

        char[] buffer = new char[1024];
        try(Reader in = new InputStreamReader(stream)) {
            for (int read = in.read(buffer); read != -1; read = in.read(buffer)) {
                result.append(buffer, 0, read);
            }
        }

        return result.toString();
    }
}