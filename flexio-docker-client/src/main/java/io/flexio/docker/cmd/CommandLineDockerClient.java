package io.flexio.docker.cmd;

import io.flexio.docker.DockerClient;
import io.flexio.docker.api.types.Container;
import io.flexio.docker.api.types.ContainerCreationData;
import io.flexio.docker.api.types.container.State;
import io.flexio.docker.api.types.optional.OptionalContainer;
import io.flexio.docker.api.types.optional.OptionalImage;
import io.flexio.docker.cmd.commands.CommandProvider;
import io.flexio.docker.cmd.commands.exception.CommandException;
import io.flexio.docker.descriptors.ContainerCreationLog;
import io.flexio.docker.descriptors.ContainerDeletionLog;
import io.flexio.docker.descriptors.ContainerStartLog;
import io.flexio.docker.descriptors.ContainerStopLog;
import org.codingmatters.poom.services.logging.CategorizedLogger;

public class CommandLineDockerClient implements DockerClient {
    static private final CategorizedLogger log = CategorizedLogger.getLogger(CommandLineDockerClient.class);

    private final CommandProvider commandProvider;

    public CommandLineDockerClient(CommandProvider commandProvider) {
        this.commandProvider = commandProvider;
    }


    @Override
    public OptionalContainer containerForName(String nameOrId) {
        try {
            return this.commandProvider.dockerInspect(nameOrId).run();
        } catch (CommandException e) {
            log.error("error inspecting container", e);
            return OptionalContainer.of(null);
        }
    }

    @Override
    public ContainerDeletionLog ensureContainerDeleted(Container containerQuery) {
        String nameOrId = containerQuery.opt().id().orElseGet(() -> containerQuery.opt().names().get(0).orElse(null));
        OptionalContainer container = this.containerForName(nameOrId);
        if(container.isPresent()) {
            if(container.get().opt().state().running().orElse(false)) {
                try {
                    this.commandProvider.dockerStop(nameOrId).run();
                } catch (CommandException e) {
                    log.error("error stopping container", e);
                    return ContainerDeletionLog.builder()
                            .container(this.containerForName(nameOrId).get())
                            .action(ContainerDeletionLog.Action.DELETE)
                            .success(false)
                            .message("failed stopping container")
                            .build();
                }
            }
            try {
                this.commandProvider.dockerRm(nameOrId).run();
            } catch (CommandException e) {
                log.error("error deleting container", e);
                return ContainerDeletionLog.builder()
                        .container(this.containerForName(nameOrId).get())
                        .action(ContainerDeletionLog.Action.DELETE)
                        .success(false)
                        .message("failed removing container")
                        .build();
            }

            return ContainerDeletionLog.builder()
                    .container(container.get().withState(State.builder().status(State.Status.unexistent).build()))
                    .action(ContainerDeletionLog.Action.NONE)
                    .success(true)
                    .build();
        } else {
            return ContainerDeletionLog.builder()
                    .container(containerQuery.withState(State.builder().status(State.Status.unexistent).build()))
                    .action(ContainerDeletionLog.Action.NONE)
                    .success(true)
                    .build();
        }
    }

    @Override
    public ContainerCreationLog ensureContainerCreated(String containerName, ContainerCreationData containerCreationData) {
        try {
            this.commandProvider.dockerPull(containerCreationData.image()).run();
        } catch (CommandException e) {
            log.error("error pull image", e);
            return ContainerCreationLog.builder()
                    .action(ContainerCreationLog.Action.PULL)
                    .success(false)
                    .message("failed pulling image")
                    .build();
        }
        OptionalContainer container = this.containerForName(containerName);
        if(container.isPresent()) {
            OptionalImage targetImage;
            try {
                targetImage = this.commandProvider.dockerImageInspect(containerCreationData.image()).run();
            } catch (CommandException e) {
                return ContainerCreationLog.builder()
                        .action(ContainerCreationLog.Action.UPDATE)
                        .success(false)
                        .message("failed inspecting image")
                        .build();
            }
            if(! container.image().get().equals(targetImage.id().get())) {
                log.info("image {} has been updated, re creating container", containerCreationData.image());
                try {
                    if (container.state().running().orElse(false)) {
                        this.commandProvider.dockerStop(container.id().get()).run();
                    }
                    this.commandProvider.dockerRm(container.id().get()).run();
                    this.commandProvider.dockerCreate(containerName, containerCreationData).run();

                    return ContainerCreationLog.builder()
                            .action(ContainerCreationLog.Action.UPDATE)
                            .success(true)
                            .container(this.containerForName(containerName).get())
                            .build();
                } catch (CommandException e) {
                    return ContainerCreationLog.builder()
                            .action(ContainerCreationLog.Action.UPDATE)
                            .success(false)
                            .message("failed recreating container with updated image")
                            .build();
                }
            } else {
                log.info("container already created with up to date image");
//                if (container.state().running().orElse(false)) {
//                    this.commandProvider.dockerStop(container.id().get()).run();
//                }
                return ContainerCreationLog.builder()
                        .action(ContainerCreationLog.Action.NONE)
                        .success(true)
                        .container(this.containerForName(containerName).get())
                        .build();
            }
        } else {
            try {
                this.commandProvider.dockerCreate(containerName, containerCreationData).run();
                return ContainerCreationLog.builder()
                        .action(ContainerCreationLog.Action.CREATION)
                        .success(true)
                        .container(this.containerForName(containerName).get())
                        .build();
            } catch (CommandException e) {
                log.error("error creating container", e);
                return ContainerCreationLog.builder()
                        .action(ContainerCreationLog.Action.CREATION)
                        .success(false)
                        .message("container creation failed")
                        .build();
            }
        }
    }

    @Override
    public ContainerStartLog ensureContainerStarted(Container containerQuery) {
        String nameOrId = containerQuery.opt().id().orElseGet(() -> containerQuery.opt().names().get(0).orElse(null));
        OptionalContainer container = this.containerForName(nameOrId);
        if(container.isPresent()) {
            if(! container.get().opt().state().running().orElse(false)) {
                try {
                    this.commandProvider.dockerStart(nameOrId).run();
                } catch (CommandException e) {
                    log.error("failed starting container", e);
                    return ContainerStartLog.builder()
                            .action(ContainerStartLog.Action.START)
                            .container(container.get())
                            .success(false)
                            .message("container not created, cannot start")
                            .build();
                }
                return ContainerStartLog.builder()
                    .container(this.containerForName(nameOrId).get())
                    .action(ContainerStartLog.Action.START)
                    .success(true)
                    .build();
            } else {
                return ContainerStartLog.builder()
                        .container(this.containerForName(nameOrId).get())
                        .action(ContainerStartLog.Action.NONE)
                        .success(true)
                        .build();
            }
        } else {
            return ContainerStartLog.builder()
                    .action(ContainerStartLog.Action.NONE)
                    .container(containerQuery.withState(State.builder().status(State.Status.unexistent).build()))
                    .success(false)
                    .message("container not created, cannot start")
                    .build();
        }
    }

    @Override
    public ContainerStopLog ensureContainerStopped(Container containerQuery) {
        String nameOrId = containerQuery.opt().id().orElseGet(() -> containerQuery.opt().names().get(0).orElse(null));
        OptionalContainer container = this.containerForName(nameOrId);
        if(container.isPresent()) {
            if(container.get().opt().state().running().orElse(false)) {
                this.commandProvider.dockerStop(nameOrId);
                return ContainerStopLog.builder()
                        .container(this.containerForName(nameOrId).get())
                        .action(ContainerStopLog.Action.STOP)
                        .success(true)
                        .build();
            } else {
                return ContainerStopLog.builder()
                        .container(this.containerForName(nameOrId).get())
                        .action(ContainerStopLog.Action.NONE)
                        .success(true)
                        .build();
            }
        } else {
            return ContainerStopLog.builder()
                    .container(this.containerForName(nameOrId).get())
                    .action(ContainerStopLog.Action.NONE)
                    .success(true)
                    .build();
        }
    }
}
