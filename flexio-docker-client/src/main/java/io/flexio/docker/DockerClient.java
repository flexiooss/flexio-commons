package io.flexio.docker;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.docker.api.*;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import io.flexio.docker.api.client.DockerEngineAPIRequesterClient;
import io.flexio.docker.api.optional.OptionalStartPostResponse;
import io.flexio.docker.api.types.Container;
import io.flexio.docker.api.types.ContainerCreationResult;
import io.flexio.docker.api.types.Image;
import io.flexio.docker.api.types.container.State;
import io.flexio.docker.api.types.optional.OptionalContainer;
import io.flexio.docker.descriptors.ContainerCreationLog;
import io.flexio.docker.descriptors.ContainerDeletionLog;
import io.flexio.docker.descriptors.ContainerStartLog;
import io.flexio.docker.descriptors.ContainerStopLog;
import org.codingmatters.rest.api.client.okhttp.HttpClientWrapper;
import org.codingmatters.rest.api.client.okhttp.OkHttpRequesterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.function.Supplier;

public class DockerClient {
    private final DockerEngineAPIClient client;
    private final String baseUrl;

    public DockerClient(HttpClientWrapper http, String baseUrl) {
        this.client = new DockerEngineAPIRequesterClient(new OkHttpRequesterFactory(http), new JsonFactory(), baseUrl);
        this.baseUrl = baseUrl;
    }

    public OptionalContainer containerForName(String name) {
        return this.runningContainer(Container.builder().names(name).build());
    }

    public ContainerDeletionLog ensureContainerDeleted(Container container) {
        OptionalContainer runningContainer = this.runningContainer(container);
        if(runningContainer.isPresent()) {
            String idOrName = container.opt().id().orElse(container.opt().names().get(0)
                    .orElseThrow(assertFails("cannot stop without at least a name or an id")));
            try {
                if (container.opt().state().running().orElse(false)) {
                    this.client.containers().container().kill().post(req -> {
                        req.containerId(idOrName);
                    });
                }
                this.client.containers().container().delete(req -> req.containerId(idOrName));

                return ContainerDeletionLog.builder()
                        .container(Container.from(container).state(state->state.status(State.Status.unexistent)).build())
                        .action(ContainerDeletionLog.Action.DELETE)
                        .success(true)
                        .build();
            } catch (IOException e) {
                throw communicationError(this.baseUrl, e);
            }
        } else {
            return ContainerDeletionLog.builder()
                    .container(Container.from(container).state(state->state.status(State.Status.unexistent)).build())
                    .action(ContainerDeletionLog.Action.NONE)
                    .success(true)
                    .build();
        }

    }

    public ContainerCreationLog ensureContainerCreated(Container container, String ... cmd) {
        this.ensureImageIsUpToDate(container.image());

        OptionalContainer runningContainer = this.runningContainer(container);
        if(runningContainer.isPresent()) {
            return this.ensureRunningContainerIsUpToDate(container, runningContainer);
        } else {
            return this.ensureNewContainerIsCreated(container, cmd);
        }
    }

    static private final Logger log = LoggerFactory.getLogger(DockerClient.class);

    private void ensureImageIsUpToDate(String imageTag) {
        try {
            CreateImagePostResponse response = this.client.images().createImage().post(req -> req.fromImage(imageTag));
            response.opt().status200().orElseThrow(assertFails("couldn't update image %s : %s", imageTag, response));
        } catch (IOException e) {
            log.error(String.format("couldn't update image %s : communication failure", imageTag), e);
        }
    }

    private OptionalContainer runningContainer(Container container) {
        try {
            ContainerListGetResponse listResponse = this.client.containers().containerList().get(req -> req
                    .all(true)
                    .filters(String.format("{\"name\": [\"%s\"]}", container.names().get(0))));
            ValueList<io.flexio.docker.api.types.ContainerInList> runningContainers = listResponse.opt().status200().payload()
                    .orElseThrow(assertFails("couldn't list containers : %s", listResponse));

            if(! runningContainers.isEmpty()) {
                return this.containerFor(runningContainers.get(0).id());
            }
        } catch (IOException e) {
            throw communicationError(this.baseUrl);
        }
        return OptionalContainer.of(null);
    }

    private OptionalContainer containerFor(String id) {
        try {
            Container container = this.client.containers().container().inspect().get(req -> req.containerId(id))
                    .opt().status200().payload()
                    .orElseThrow(assertFails("no such container %s", id));


            InspectImageGetResponse imageGetResponse = this.client.images().inspectImage().get(req -> req.imageId(container.image()));
            Image image = imageGetResponse
                    .opt().status200().payload()
                    .orElseThrow(assertFails("no such image %s", container.image()));

            return OptionalContainer.of(Container.from(container).image(image.repoTags().get(0)).build());
        } catch (IOException e) {
            e.printStackTrace();
            throw communicationError(this.baseUrl);
        }
    }

    private ContainerCreationLog ensureRunningContainerIsUpToDate(Container container, OptionalContainer runningContainer) {
        if(runningContainer.get().image().equals(container.image())) {
            return ContainerCreationLog.builder()
                    .container(runningContainer.get())
                    .action(ContainerCreationLog.Action.NONE)
                    .success(true)
                    .message("OK")
                    .build();
        } else {
            return ContainerCreationLog.builder()
                    .container(runningContainer.get())
                    .success(false)
                    .action(ContainerCreationLog.Action.UPDATE)
                    .message(String.format("container %s has wrong image (expected %s, but was %s)",
                            container.names(),
                            container.image(),
                            runningContainer.image().get()
                    ))
                    .build();
        }
    }

    private ContainerCreationLog ensureNewContainerIsCreated(Container container, String ... cmd) {
        OptionalContainer runningContainer= this.createContainer(container, cmd);
        if(runningContainer.isPresent()) {
            return ContainerCreationLog.builder()
                    .container(this.containerFor(runningContainer.id().get()).get())
                    .action(ContainerCreationLog.Action.CREATION)
                    .success(true)
                    .message("OK")
                    .build();
        } else {
            return ContainerCreationLog.builder()
                    .container(Container.from(container)
                            .state(State.builder().status(State.Status.unexistent).build())
                            .build())
                    .action(ContainerCreationLog.Action.CREATION)
                    .success(false)
                    .message(String.format("container %s creation failed", container.names()))
                    .build();
        }
    }

    private OptionalContainer createContainer(Container container, String ... cmd) {
        try {
            CreateContainerPostResponse response = this.client.containers().createContainer()
                    .post(req -> req.name(container.names().get(0)).payload(payload -> payload.image(container.image()).cmd(cmd)));
            ContainerCreationResult creationResult = response.opt().status201().payload()
                    .orElseThrow(assertFails("failed creating container %s : %s", container.names(), response));
            return OptionalContainer.of(
                    Container.from(container).id(creationResult.id()).build()
            );
        } catch (IOException e) {
            communicationError(this.baseUrl);
        }
        return OptionalContainer.of(null);
    }

    public ContainerStartLog ensureContainerStarted(Container container) {
        OptionalContainer runningContainer = this.runningContainer(container);
        if(runningContainer.isPresent()) {
            if(! runningContainer.state().running().orElse(false)) {
                return this.startContainer(runningContainer);
            } else {
                return ContainerStartLog.builder()
                        .action(ContainerStartLog.Action.NONE)
                        .success(true)
                        .message("container already running")
                        .container(runningContainer.get())
                        .build();
            }
        } else {
            return ContainerStartLog.builder()
                    .container(Container.from(container)
                            .state(state -> state.status(State.Status.unexistent))
                            .build())
                    .action(ContainerStartLog.Action.NONE)
                    .success(false)
                    .message(String.format("container %s doesn't exist, will not start it", container.names()))
                    .build();
        }
    }

    public ContainerStopLog ensureContainerStopped(Container container) {
        OptionalContainer runningContainer = this.runningContainer(container);
        if(runningContainer.state().running().orElse(false)) {
            try {
                this.client.containers().container().kill().post(req -> req.containerId(runningContainer.id().get()));
                return ContainerStopLog.builder()
                        .container(this.containerFor(runningContainer.id().get()).get())
                        .action(ContainerStopLog.Action.STOP)
                        .success(true)
                        .build();
            } catch (IOException e) {
                communicationError(this.baseUrl, e);
            }
        }
        return ContainerStopLog.builder()
                .container(this.containerFor(runningContainer.id().get()).get())
                .action(ContainerStopLog.Action.NONE)
                .success(true)
                .build();
    }

    private ContainerStartLog startContainer(OptionalContainer runningContainer) {
        try {
            OptionalStartPostResponse startResponse = this.client.containers().container().start().post(req -> req.containerId(runningContainer.id().get())).opt();

            ContainerStartLog.Builder log = ContainerStartLog.builder()
                    .action(ContainerStartLog.Action.START)
                    .container(this.containerFor(runningContainer.id().get()).get());

            if(startResponse.status309().isPresent() || startResponse.status404().isPresent() || startResponse.status500().isPresent()) {
                log.success(false)
                        .message(String.format("",
                                startResponse.status309().payload()
                                        .orElse(startResponse.status404().payload()
                                                .orElse(startResponse.status500().payload()
                                                        .orElseThrow(assertFails("unexpected response : %s", startResponse.get()))))
                                .message()
                        ));
            } else {
                while(! this.containerFor(runningContainer.id().get()).state().running().orElse(false)) {
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.container(this.containerFor(runningContainer.id().get()).get()).success(true).message("OK");
            }
            return log.build();
        } catch (IOException e) {
            throw communicationError(this.baseUrl, e);
        }
    }


    static private Supplier<AssertionError> assertFails(String message, Object ... vals) {
        return () -> new AssertionError(String.format("[docker client] " + message, vals));
    }

    static private AssertionError communicationError(String baseUrl) {
        return communicationError(baseUrl, null);
    }

    static private AssertionError communicationError(String baseUrl, IOException ioe) {
        if(ioe != null) {
            return new AssertionError("failed communicating with docker engine at " + baseUrl, ioe);
        } else {
            return new AssertionError("failed communicating with docker engine at " + baseUrl);
        }
    }
}
