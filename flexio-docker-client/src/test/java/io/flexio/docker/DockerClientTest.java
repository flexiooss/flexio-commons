package io.flexio.docker;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.flexio.docker.api.ContainerListGetResponse;
import io.flexio.docker.api.CreateImagePostResponse;
import io.flexio.docker.api.ValueList;
import io.flexio.docker.client.DockerEngineAPIClient;
import io.flexio.docker.client.DockerEngineAPIRequesterClient;
import io.flexio.docker.api.types.Container;
import io.flexio.docker.api.types.container.State;
import io.flexio.docker.descriptors.ContainerCreationLog;
import io.flexio.docker.descriptors.ContainerStartLog;
import org.codingmatters.rest.api.client.okhttp.HttpClientWrapper;
import org.codingmatters.rest.api.client.okhttp.OkHttpClientWrapper;
import org.codingmatters.rest.api.client.okhttp.OkHttpRequesterFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DockerClientTest {

    static private final Logger log = LoggerFactory.getLogger(DockerClientTest.class);
    public static final String ALPINE_IMAGE = "alpine:3.8";

    private HttpClientWrapper http = OkHttpClientWrapper.build();
    private DockerClient dockerClient = new DockerClient(http, DockerResource.resolveDockerUrl());

    static private final LinkedList<String> CONTAINERS = new LinkedList<String>() {{
        this.add("not-created");
        this.add("already-created");
        this.add("not-started");
        this.add("started");
    }};

    @Before
    public void setUp() throws Exception {
        DockerEngineAPIClient client = new DockerEngineAPIRequesterClient(
                new OkHttpRequesterFactory(http, () -> DockerResource.resolveDockerUrl()), new JsonFactory(), DockerResource.resolveDockerUrl()
        );
        this.cleanUpContainers(client);
        
        CreateImagePostResponse pullResponse = client.images().createImage().post(req -> req.fromImage(ALPINE_IMAGE));
        pullResponse.opt().status200().orElseThrow(() -> new AssertionError("failed pulling image : " + ALPINE_IMAGE + " got " + pullResponse));

        client.containers().createContainer().post(req ->
                req.name("already-created").payload(payload -> payload.image(ALPINE_IMAGE).cmd("echo", "hello world"))
        );

        String notStartedId = client.containers().createContainer().post(req1 ->
                req1.name("not-started").payload(payload -> payload.image(ALPINE_IMAGE).cmd("/bin/sh", "-c", "while true; do sleep 1000; done"))
        ).status201().payload().id();

        client.containers().container().stop().post(req->req.containerId(notStartedId));

        String startedId = client.containers().createContainer().post(req ->
                req.name("already-started").payload(payload -> payload.image(ALPINE_IMAGE).cmd("/bin/sh", "-c", "while true; do sleep 1000; done"))
        ).status201().payload().id();

        client.containers().container().start().post(req->req.containerId(startedId));
    }

    @After
    public void tearDown() throws Exception {
        DockerEngineAPIClient client = new DockerEngineAPIRequesterClient(
                new OkHttpRequesterFactory(http, () -> DockerResource.resolveDockerUrl()), new JsonFactory(), DockerResource.resolveDockerUrl() //"http://localhost:2375///"
        );
        this.cleanUpContainers(client);
    }

    @Test
    public void givenContainerIsNotCreated_whenEnsureContainerCreated_thenContainerIsCreatedAndContainerStatusIsStopped() throws Exception {
        Container totoContainer = Container.builder()
                .image(ALPINE_IMAGE)
                .names("not-created")
                .build();

        ContainerCreationLog creationLog = this.dockerClient.ensureContainerCreated(totoContainer);

        assertThat(creationLog.success(), is(true));
        assertThat(creationLog.action(), is(ContainerCreationLog.Action.CREATION));
        System.out.println(creationLog);
        assertThat(creationLog.container().state().status(), is(State.Status.created));
    }

    @Test
    public void givenContainerIsAlreadyCreated_whenEnsureContainerCreated_thenNothingDoneAndContainerStatusIsCreated() throws Exception {
        Container container = Container.builder()
                .image(ALPINE_IMAGE)
                .names("already-created")
                .build();

        ContainerCreationLog creationLog = this.dockerClient.ensureContainerCreated(container);

        System.out.println(creationLog);
        assertThat(creationLog.success(), is(true));
        assertThat(creationLog.action(), is(ContainerCreationLog.Action.NONE));
        assertThat(creationLog.container().state().status(), is(State.Status.created));
    }

    @Test
    public void givenContainerIsAlreadyStarted_whenEnsureContainerCreated_thenNothindDoneAndContainerStatusIsStarted() throws Exception {
        Container container = Container.builder()
                .image(ALPINE_IMAGE)
                .names("already-started")
                .build();

        ContainerCreationLog creationLog = this.dockerClient.ensureContainerCreated(container);

        System.out.println(creationLog);
        assertThat(creationLog.success(), is(true));
        assertThat(creationLog.action(), is(ContainerCreationLog.Action.NONE));
        assertThat(creationLog.container().state().status(), is(State.Status.running));
    }

    @Test
    public void givenContainerIsNotStarted_whenEnsureContainerStarted_thenContainerIsStarted() throws Exception {
        Container container = Container.builder()
                .image(ALPINE_IMAGE)
                .names("not-started")
                .build();

        ContainerStartLog startLog = this.dockerClient.ensureContainerStarted(container);

        assertThat(startLog.success(), is(true));
        assertThat(startLog.action(), is(ContainerStartLog.Action.START));
        System.out.println(startLog.container().state());
        assertThat(startLog.container().state().status(), is(State.Status.running));
    }

    @Test
    public void givenContainerIsAlreadyStarted_whenEnsureContainerStarted_thenNothingAppens() throws Exception {
        Container container = Container.builder()
                .image(ALPINE_IMAGE)
                .names("already-started")
                .build();

        ContainerStartLog startLog = this.dockerClient.ensureContainerStarted(container);

        System.out.println(startLog);
        assertThat(startLog.success(), is(true));
        assertThat(startLog.action(), is(ContainerStartLog.Action.NONE));
        assertThat(startLog.container().state().status(), is(State.Status.running));
    }


    @Test
    public void givenContainerIsNotCreated_whenEnsureContainerStarted_thenFailureNothingHappens() throws Exception {
        Container container = Container.builder()
                .image(ALPINE_IMAGE)
                .names("not-created")
                .build();

        ContainerStartLog startLog = this.dockerClient.ensureContainerStarted(container);

        System.out.println(startLog);
        assertThat(startLog.success(), is(false));
        assertThat(startLog.action(), is(ContainerStartLog.Action.NONE));
        assertThat(startLog.container().state().status(), is(State.Status.unexistent));
    }



    private void cleanUpContainers(DockerEngineAPIClient client) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String filterString = mapper.writeValueAsString(new HashMap<String, List<String>>() {{
            this.put("name", CONTAINERS);
        }});
        ContainerListGetResponse resp = client.containers().containerList().get(req -> req.all(true).filters(
                filterString
                //"{\"name\": [\"not-created\"]}"
        ));
        for (io.flexio.docker.api.types.ContainerInList container : resp.opt().status200().payload().orElse(new ValueList.Builder<io.flexio.docker.api.types.ContainerInList>().build())) {
            client.containers().container().kill().post(req->req.containerId(container.id()));
            client.containers().container().delete(req -> req.containerId(container.id()));
        }
    }
}