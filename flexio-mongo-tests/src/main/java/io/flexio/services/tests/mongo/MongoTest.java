package io.flexio.services.tests.mongo;

import io.flexio.docker.DockerResource;

import java.util.function.Supplier;

public class MongoTest {

    static public final String MONGO = System.getProperty("ut.container.prefix", "") + "mongo-ut";
    static public final String MONGO_IMAGE = "mongo:" + System.getProperty("ut.mongo.version", "4.0.3");

    static public DockerResource.ContainerInitialStatus docker() {
        return DockerResource.client()
                .with(MONGO, container -> {
                    System.out.println("###### MONGO IMAGE " + MONGO_IMAGE);
                    return container.image(MONGO_IMAGE);
                });
    }

    static public MongoResource mongo(Supplier<DockerResource> dockerSupplier) {
        return new MongoResource(
                () -> dockerSupplier.get().containerInfo(MONGO).networkSettings().iPAddress().orElseThrow(() -> new AssertionError("no ip address for container")),
                27017);
    }


}
