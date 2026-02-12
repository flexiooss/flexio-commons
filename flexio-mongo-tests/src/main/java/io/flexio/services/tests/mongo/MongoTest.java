package io.flexio.services.tests.mongo;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.docker.DockerResource;
import io.flexio.docker.api.types.json.ContainerWriter;
import io.flexio.docker.api.types.optional.OptionalContainer;
import org.codingmatters.poom.services.logging.CategorizedLogger;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

public class MongoTest {

    private static final CategorizedLogger log = CategorizedLogger.getLogger(MongoTest.class);

    static public final String MONGO = System.getProperty("ut.container.prefix", "") + "mongo-ut";
    static public final String MONGO_IMAGE = "harbor.ci.flexio.io/flexio/mongo-6.0:" + System.getProperty("ut.mongo.version", "1.2.0");

    static private final JsonFactory jsonFactory = new JsonFactory();

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
                () -> getHost(dockerSupplier),
                27017);
    }

    private static String getHost(Supplier<DockerResource> dockerSupplier) {
        for (int i = 0; i < 3; i++) {
            try {
                OptionalContainer optionalContainer = dockerSupplier.get().containerInfo(MONGO);
                Optional<String> ipAddressForContainer = optionalContainer.networkSettings().iPAddress();
                if (ipAddressForContainer.isEmpty() || ipAddressForContainer.get().isBlank()) {
                    if (optionalContainer.isPresent()) {
                        throw new IOException("no ip address for container " + new ContainerWriter().writeString(jsonFactory, optionalContainer.get()));
                    } else {
                        throw new IOException("no ip address for container, no container");
                    }
                }
                return ipAddressForContainer.get();
            } catch (Throwable t) {
                log.error("Error getting container address", t);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("Error sleeping", e);
                }
            }
        }
        throw new AssertionError("Could not get container ip after 3 retry");
    }


}
