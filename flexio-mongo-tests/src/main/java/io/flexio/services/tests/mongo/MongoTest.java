package io.flexio.services.tests.mongo;

import io.flexio.docker.DockerResource;

import java.util.Optional;
import java.util.function.Supplier;

public class MongoTest {

    static public final String MONGO = System.getProperty("ut.container.prefix", "") + "mongo-ut";
    static public final String MONGO_IMAGE = "harbor.ci.flexio.io/flexio/mongo-6.0:" + System.getProperty("ut.mongo.version", "1.2.0");

    static public DockerResource.ContainerInitialStatus docker() {
        DockerResource docker = DockerResource.client();
        return withMongo(docker);
    }

    public static DockerResource.ContainerInitialStatus withMongo(DockerResource docker) {
        return docker
                .with(MONGO, container -> {
                    System.out.println("###### MONGO IMAGE " + MONGO_IMAGE);
                    return container.image(MONGO_IMAGE);
                });
    }

    static public MongoResource mongo(Supplier<DockerResource> dockerSupplier) {
        return new MongoResource(
                () -> {
                    Optional<String> ipAddressForContainer = dockerSupplier.get().containerInfo(MONGO).networkSettings().iPAddress();
                    if (ipAddressForContainer.isEmpty() || ipAddressForContainer.get().isBlank()) {
                        throw new AssertionError("no ip address for container");
                    }
                    return ipAddressForContainer.get();
                },
                27017);
    }


}
