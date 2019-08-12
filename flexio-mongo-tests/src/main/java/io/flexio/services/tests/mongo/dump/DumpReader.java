package io.flexio.services.tests.mongo.dump;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface DumpReader {
    List<String> collections() throws IOException;

    InputStream collectionData(String name) throws IOException;
}
