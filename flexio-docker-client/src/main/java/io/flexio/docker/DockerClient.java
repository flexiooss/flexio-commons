package io.flexio.docker;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flexio.docker.api.types.Container;
import io.flexio.docker.api.types.ContainerCreationData;
import io.flexio.docker.api.types.optional.OptionalContainer;
import io.flexio.docker.cmd.CommandLineDockerClient;
import io.flexio.docker.cmd.commands.CommandProvider;
import io.flexio.docker.descriptors.ContainerCreationLog;
import io.flexio.docker.descriptors.ContainerDeletionLog;
import io.flexio.docker.descriptors.ContainerStartLog;
import io.flexio.docker.descriptors.ContainerStopLog;
import org.codingmatters.poom.services.support.Env;
import org.codingmatters.poom.services.support.process.ProcessInvoker;
import org.codingmatters.rest.api.client.okhttp.OkHttpClientWrapper;

public interface DockerClient {

    OptionalContainer containerForName(String name);
    ContainerDeletionLog ensureContainerDeleted(Container container);
    ContainerCreationLog ensureContainerCreated(String containerName, ContainerCreationData containerCreationData);
    ContainerStartLog ensureContainerStarted(Container container);
    ContainerStopLog ensureContainerStopped(Container container);

    static DockerClient fromEnv() {
        String clientImplClassName = Env.optional(DOCKER_CLIENT_IMPL).orElse(new Env.Var(DOCKER_CLIENT_DEFAULT_IMPL)).asString();
        if(clientImplClassName.equals(ApiDockerClient.class.getName())) {
            return new ApiDockerClient(OkHttpClientWrapper.build(), resolveDockerUrl());
        } else {
            return new CommandLineDockerClient(CommandProvider.process(new ProcessInvoker(), new ObjectMapper()));
        }
    }

    String DOCKER_CLIENT_IMPL = "DOCKER_CLIENT_IMPL";
    String DOCKER_CLIENT_DEFAULT_IMPL = ApiDockerClient.class.getName();
//    String DOCKER_CLIENT_DEFAULT_IMPL = CommandLineDockerClient.class.getName();


    static String resolveDockerUrl() {
        String property = System.getProperty("docker.resource.docker.url",
                System.getenv("docker.resource.docker.url".replaceAll(".", "_").toUpperCase()) != null ?
                        System.getenv("docker.resource.docker.url") :
                        "http://localhost:2375"
        );
        return property;
    }
}
