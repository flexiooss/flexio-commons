package io.flexio.services.tests.mongo.dump;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class ResourceDumpReader implements DumpReader {

    private final String resourceBase;

    public ResourceDumpReader(String resourceBase) {
        this.resourceBase = resourceBase.endsWith("/") ? resourceBase : resourceBase + "/";
    }

    @Override
    public List<String> collections() throws IOException {
        List<String> results = new LinkedList<>();

        try(InputStream in = this.resourceStream(this.resourceBase); BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            for(String resource = br.readLine() ; resource != null ; resource = br.readLine()) {
                if(resource.endsWith(".bson")) {
                    results.add(resource.substring(0, resource.length() - ".bson".length()));
                }
            }
        }

        return results;
    }

    @Override
    public InputStream collectionData(String name) throws IOException {
        return this.resourceStream(this.resourceBase + name + ".bson");
    }

    private InputStream resourceStream(String resource) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
    }
}
