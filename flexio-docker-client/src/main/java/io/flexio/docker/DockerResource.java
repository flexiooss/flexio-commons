package io.flexio.docker;

import io.flexio.docker.api.types.Container;
import io.flexio.docker.api.types.ContainerCreationData;
import io.flexio.docker.api.types.optional.OptionalContainer;
import okhttp3.OkHttpClient;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;

import java.util.*;
import java.util.function.Function;

public interface DockerResource extends TestRule {

    static DockerResource client() {
        return new DockerResourceImpl();
    }

    ContainerInitialStatus with(String containerName, ContainerCreationData container);
    ContainerInitialStatus with(String containerName, Function<ContainerCreationData.Builder, ContainerCreationData.Builder> container);

    OptionalContainer containerInfo(String containerName);

    interface ContainerInitialStatus {
        ContainerFinalStatus stopped();
        ContainerFinalStatus started();
        ContainerFinalStatus deleted();
    }

    interface ContainerFinalStatus {
        DockerResource finallyStopped();
        DockerResource finallyStarted();
        DockerResource finallyDeleted();
    }

    class DockerResourceImpl extends ExternalResource implements DockerResource {

        static class Builder implements ContainerInitialStatus, ContainerFinalStatus {
            private final DockerResourceImpl from;
            private final String containerName;
            private final ContainerCreationData container;
            private ContainerStates.Status initialState = ContainerStates.Status.STARTED;
            private ContainerStates.Status finalState = ContainerStates.Status.STARTED;

            Builder(DockerResourceImpl from, String containerName, ContainerCreationData container) {
                this.from = from;
                this.containerName = containerName;
                this.container = container;
            }

            @Override
            public ContainerFinalStatus stopped() {
                this.initialState = ContainerStates.Status.STOPPED;
                return this;
            }

            @Override
            public ContainerFinalStatus started() {
                this.initialState = ContainerStates.Status.STARTED;
                return this;
            }

            @Override
            public ContainerFinalStatus deleted() {
                this.initialState = ContainerStates.Status.DELETED;
                return this;
            }

            @Override
            public DockerResource finallyStopped() {
                this.finalState = ContainerStates.Status.STOPPED;
                return this.build();
            }

            @Override
            public DockerResource finallyStarted() {
                this.finalState = ContainerStates.Status.STARTED;
                return this.build();
            }

            @Override
            public DockerResource finallyDeleted() {
                this.finalState = ContainerStates.Status.DELETED;
                return this.build();
            }

            private DockerResource build() {
                this.from.add(this.containerName, new ContainerStates(this.container, this.initialState, this.finalState));
                return this.from;
            }
        }

        static private class ContainerStates {
            enum Status {STOPPED, STARTED, DELETED}

            public final ContainerCreationData container;
            public final Status inintialStatus;
            public final Status finalStatus;

            public ContainerStates(ContainerCreationData container, Status inintialStatus, Status finalStatus) {
                this.container = container;
                this.inintialStatus = inintialStatus;
                this.finalStatus = finalStatus;
            }
        }

        private final Map<String, ContainerStates> managedContainers = Collections.synchronizedMap(new HashMap<>());
        private final DockerClient dockerClient = new DockerClient(
                new OkHttpClient.Builder().build(),
                System.getProperty("docker.resource.docker.url",
                        System.getenv("docker.resource.docker.url".replaceAll(".", "_").toUpperCase()) != null ?
                                System.getenv("docker.resource.docker.url") :
                                "http://localhost:2375"
                )
        );

        @Override
        public ContainerInitialStatus with(String containerName, ContainerCreationData container) {
            return new Builder(this, containerName, container);
        }

        @Override
        public ContainerInitialStatus with(String containerName, Function<ContainerCreationData.Builder, ContainerCreationData.Builder> container) {
            ContainerCreationData.Builder builder = ContainerCreationData.builder();
            return this.with(containerName, container.apply(builder).build());
        }

        @Override
        public OptionalContainer containerInfo(String containerName) {
            return this.dockerClient.containerForName(containerName);
        }

        public DockerResourceImpl add(String containerName, ContainerStates containerStates) {
            this.managedContainers.put(containerName, containerStates);
            return this;
        }

        @Override
        protected void before() throws Throwable {
            super.before();
            Set<String> containerNames = new HashSet<>(this.managedContainers.keySet());
            for (String containerName : containerNames) {
                ContainerStates containerStates = this.managedContainers.get(containerName);
                this.ensureStatus(containerName, containerStates, containerStates.inintialStatus);
            }
        }

        @Override
        protected void after() {
            Set<String> containerNames = new HashSet<>(this.managedContainers.keySet());
            for (String containerName : containerNames) {
                ContainerStates containerStates = this.managedContainers.get(containerName);
                this.ensureStatus(containerName, containerStates, containerStates.finalStatus);
            }

            super.after();
        }

        private void ensureStatus(String containerName, ContainerStates containerStates, ContainerStates.Status status) {
            Container container = Container.builder()
                    .names(containerName)
                    .image(containerStates.container.image())
                    .build();
            String[] cmd = containerStates.container.cmd() != null ?
                    containerStates.container.cmd().toArray(new String[0])
                    : null;
            switch (status) {
                case STARTED:
                    this.dockerClient.ensureContainerCreated(container, cmd);
                    this.dockerClient.ensureContainerStarted(container);
                    break;
                case STOPPED:
                    this.dockerClient.ensureContainerCreated(container, cmd);
                    this.dockerClient.ensureContainerStopped(container);
                    break;
                case DELETED:
                    this.dockerClient.ensureContainerDeleted(container);
                    break;
            }
        }
    }


}
