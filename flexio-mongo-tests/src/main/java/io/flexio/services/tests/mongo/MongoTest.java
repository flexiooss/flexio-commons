package io.flexio.services.tests.mongo;

import io.flexio.docker.DockerResource;

import java.util.function.Supplier;

public class MongoTest {

    static public final String MONGO = "mongo-ut";

    static public DockerResource.ContainerInitialStatus docker() {
        return DockerResource.client()
                .with(MONGO, container -> container.image("mongo:3.4.10"));
    }

    static public MongoResource mongo(Supplier<DockerResource> dockerSupplier) {
        return new MongoResource(
                () -> dockerSupplier.get().containerInfo(MONGO).networkSettings().iPAddress().orElseThrow(() -> new AssertionError("no ip address for container")),
                27017);
    }


}
