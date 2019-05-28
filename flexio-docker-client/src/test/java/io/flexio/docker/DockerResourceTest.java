package io.flexio.docker;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.flexio.docker.api.ContainerListGetResponse;
import io.flexio.docker.api.ValueList;
import io.flexio.docker.client.DockerEngineAPIClient;
import io.flexio.docker.client.DockerEngineAPIRequesterClient;
import io.flexio.docker.api.types.container.State;
import okhttp3.OkHttpClient;
import org.codingmatters.rest.api.client.okhttp.OkHttpClientWrapper;
import org.codingmatters.rest.api.client.okhttp.OkHttpRequesterFactory;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.assertThat;

@Ignore
public class DockerResourceTest {

    private DockerResource docker = DockerResource.client()
            .with("started-started", container -> container.image("alpine:3.8").cmd("/bin/sh", "-c", "while true; do sleep 1000; done")).started().finallyStarted()
            .with("started-stopped", container -> container.image("alpine:3.8").cmd("/bin/sh", "-c", "while true; do sleep 1000; done")).started().finallyStopped()
            .with("stopped-started", container -> container.image("alpine:3.8").cmd("/bin/sh", "-c", "while true; do sleep 1000; done")).stopped().finallyStarted()
            .with("stopped-deleted", container -> container.image("alpine:3.8").cmd("/bin/sh", "-c", "while true; do sleep 1000; done")).stopped().finallyDeleted()
            ;

    private DockerClient dockerClient;

    @Before
    public void setUp() throws Exception {
        Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);

        this.dockerClient = new DockerClient(OkHttpClientWrapper.build(), "http://localhost:2375");
    }

    @After
    public void tearDown() throws Exception {
        this.cleanUpContainers(
                new DockerEngineAPIRequesterClient(
                        new OkHttpRequesterFactory(OkHttpClientWrapper.build(), () -> "http://localhost:2375"),
                        new JsonFactory(),
                        "http://localhost:2375"),
                Arrays.asList("started-started", "started-stopped", "stopped-started", "stopped-deleted"));
    }

    private void cleanUpContainers(DockerEngineAPIClient client, List<String> containers) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String filterString = mapper.writeValueAsString(new HashMap<String, List<String>>() {{
            this.put("name", containers);
        }});
        ContainerListGetResponse resp = client.containers().containerList().get(req -> req.all(true).filters(
                filterString
        ));
        for (io.flexio.docker.api.types.ContainerInList container : resp.opt().status200().payload().orElse(new ValueList.Builder<io.flexio.docker.api.types.ContainerInList>().build())) {
            client.containers().container().kill().post(req->req.containerId(container.id()));
            client.containers().container().delete(req -> req.containerId(container.id()));
        }
    }

    @Test
    public void startedThenStarted() throws Throwable {
        this.docker.apply(this.verify(
                () -> assertThat(this.dockerClient.containerForName("started-started").state().status().get(), is(State.Status.running))
        ), null).evaluate();
        assertThat(this.dockerClient.containerForName("started-started").state().status().get(), is(State.Status.running));
    }

    @Test
    public void startedThenStopped() throws Throwable {
        this.docker.apply(this.verify(
                () -> assertThat(this.dockerClient.containerForName("started-stopped").state().status().get(), is(State.Status.running))
        ), null);
        assertThat(this.dockerClient.containerForName("started-stopped").state().status().orElse(State.Status.exited), isAStoppedStatus());
    }

    @Test
    public void stoppedThenStarted() throws Throwable {
        this.docker.apply(this.verify(
                () -> assertThat(this.dockerClient.containerForName("stopped-started").state().status().get(), isAStoppedStatus())
        ), null).evaluate();
        assertThat(this.dockerClient.containerForName("stopped-started").state().status().get(), is(State.Status.running));
    }

    @Test
    public void stoppedThenDeleted() throws Throwable {
        this.docker.apply(this.verify(
                () -> assertThat(this.dockerClient.containerForName("stopped-deleted").state().status().get(), isAStoppedStatus())
        ), null).evaluate();
        assertThat(this.dockerClient.containerForName("stopped-deleted").isPresent(), is(false));
    }



    private Statement verify(Verify verify) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                verify.verify();
            }
        };
    }

    @FunctionalInterface
    interface Verify {
        void verify();
    }

    static private Matcher<State.Status> isAStoppedStatus() {
        return isOneOf(State.Status.exited, State.Status.created);
    }
}