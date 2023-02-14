package io.flexio.docker.cmd.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flexio.docker.api.types.ContainerCreationData;
import org.codingmatters.poom.services.support.process.ProcessInvoker;

public interface CommandProvider {
    DockerInspectCommand dockerInspect(String containerNameOrId);

    DockerStopCommand dockerStop(String containerNameOrId);

    DockerRmCommand dockerRm(String containerNameOrId);

    DockerStartCommand dockerStart(String containerNameOrId);

    DockerCreateCommand dockerCreate(String containerName, ContainerCreationData containerCreationData);

    DockerImageInspectCommand dockerImageInspect(String imageOrId);

    DockerPullCommand dockerPull(String image);

    static CommandProvider process(ProcessInvoker invoker, ObjectMapper mapper) {
        return new CommandProvider() {
            @Override
            public DockerInspectCommand dockerInspect(String containerNameOrId) {
                return DockerInspectCommand.process(invoker, mapper, containerNameOrId);
            }

            @Override
            public DockerStopCommand dockerStop(String containerNameOrId) {
                return DockerStopCommand.process(invoker, containerNameOrId);
            }

            @Override
            public DockerRmCommand dockerRm(String containerNameOrId) {
                return DockerRmCommand.process(invoker, containerNameOrId);
            }

            @Override
            public DockerStartCommand dockerStart(String containerNameOrId) {
                return DockerStartCommand.process(invoker, containerNameOrId);
            }

            @Override
            public DockerCreateCommand dockerCreate(String containerName, ContainerCreationData containerCreationData) {
                return DockerCreateCommand.process(invoker, containerName, containerCreationData);
            }

            @Override
            public DockerImageInspectCommand dockerImageInspect(String imageOrId) {
                return DockerImageInspectCommand.process(invoker, mapper, imageOrId);
            }

            @Override
            public DockerPullCommand dockerPull(String image) {
                return DockerPullCommand.process(invoker, image);
            }
        };
    }
}
