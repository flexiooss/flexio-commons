package io.flexio.docker.cmd.commands.gen;

import io.flexio.docker.api.types.ContainerCreationData;
import org.codingmatters.poom.services.logging.CategorizedLogger;

import java.util.LinkedList;
import java.util.List;

public class DockerCommandGenerator {
    static private final CategorizedLogger log = CategorizedLogger.getLogger(DockerCommandGenerator.class);

    public String[] create(String containerName, ContainerCreationData containerCreationData) {
        List<String> result = new LinkedList<>();
        result.add("docker");
        result.add("create");

        result.add("--name");
        result.add(containerName);

        if(containerCreationData.opt().exposedPorts().isPresent()) {
            for (String s : containerCreationData.exposedPorts().propertyNames()) {
                result.add("-p");
                result.add(s + ":" + containerCreationData.exposedPorts().property(s).single().stringValue());
            }
        }

        if(containerCreationData.opt().env().isPresent()) {
            for (String e : containerCreationData.env()) {
                result.add("--env");
                result.add(e);
            }
        }

        result.add(containerCreationData.image());

        if(containerCreationData.opt().cmd().isPresent()) {
            for (String c : containerCreationData.cmd()) {
                result.add(c);
            }
        }

        if(containerCreationData.opt().hostConfig().isPresent()) {
            log.error("don't know what to do with host config : ", containerCreationData.hostConfig());
        }

        return result.toArray(new String[0]);
    }
}
