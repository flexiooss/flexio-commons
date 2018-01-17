package io.flexio.docker;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.docker.api.*;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import io.flexio.docker.api.client.DockerEngineAPIRequesterClient;
import io.flexio.docker.api.types.ContainerCreationResult;
import io.flexio.docker.api.types.ContainerInList;
import io.flexio.docker.api.types.Image;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import org.codingmatters.rest.api.client.okhttp.OkHttpRequesterFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Ignore
public class ClientTest {

    static private final Logger log = LoggerFactory.getLogger(ClientTest.class);

    private OkHttpClient http = new OkHttpClient.Builder().eventListener(
            new EventListener() {
                @Override
                public void callStart(Call call) {
                    super.callStart(call);
                    System.out.println("calling : " + call.request().url());
                }
            }
    ).build();
    private DockerEngineAPIClient client = new DockerEngineAPIRequesterClient(
            new OkHttpRequesterFactory(http), new JsonFactory(), "http://localhost:2375///"
    );

    @Test
    public void listContainers() throws Exception {
        ContainerListGetResponse resp = this.client.containers().containerList().get(req -> req.all(true).filters("{\"name\": [\"yop\"]}"));

        if(resp.opt().status200().isPresent()) {
            log.info("got response from docker : {}", resp.opt().status200().payload().get());
        } else {
            throw new AssertionError(String.format(
                    "error getting response from docker : %s",
                    resp.opt().status400().payload().orElse(resp.opt().status500().payload().get()))
            );
        }
    }

    @Test
    public void createContainer() throws Exception {
        CreateContainerPostResponse resp = this.client.containers().createContainer().post(req ->
                req.name("yop").payload(payload -> payload.image("alpine:latest").cmd("echo", "hello world"))
        );
        System.out.println(resp);
        Assert.assertThat(resp.opt().status201().payload().id().orElse(null), is(notNullValue()));
    }

    @Test
    public void listImages() throws Exception {
        ImageListGetResponse resp = this.client.images().imageList().get(req -> req.all(true));
        for (Image image : resp.opt().status200().orElseThrow(() -> new AssertionError("failed to list images")).payload()) {
            System.out.println(image.repoTags());
        }
    }

    @Test
    public void pullImage() throws Exception {
        CreateImagePostResponse resp = this.client.images().createImage().post(req -> req.fromImage("mongo:3.4.10"));
        System.out.println(resp);
    }

    @Test
    public void version() throws Exception {
        VersionGetResponse resp = this.client.version().get(VersionGetRequest.builder().build());
        System.out.println(resp);
    }

    @Test
    public void remove() throws Exception {
        CreateContainerPostResponse creationResp = this.client.containers().createContainer().post(req ->
                req.name("yop").payload(payload -> payload.image("alpine:latest").cmd("echo", "hello world"))
        );
        String containerId = creationResp.opt().status201().payload().id().get();
        System.out.println(containerId);
        ContainerDeleteResponse resp = this.client.containers().container().delete(req -> req.containerId(containerId).v(true));
        System.out.println(resp);
        if(resp.opt().status204().isPresent()) {
            WaitForPostResponse waitResp = this.client.containers().container().waitFor().post(req -> req.containerId(containerId));
            System.out.println(waitResp);
        }
    }

    @Test
    public void createContainerByName() throws Exception {
        String imageTag = "alpine:latest";
        String containerName = "toto";

        ContainerInList targetContainer = null;
        for (ContainerInList container : this.client.containers().containerList().get(req -> req.all(false)).opt().status200().orElseThrow(() -> new AssertionError("failed to retrieve container list")).payload()) {
            if(container.names().contains(containerName)) {
                targetContainer = container;
                break;
            }
        }

        if(targetContainer != null) {
            log.info("container is alreay running : {}");
        } else {

            this.client.version().get(VersionGetRequest.builder().build()).opt().status200()
                    .orElseThrow(() -> new AssertionError("problem accessing docker instance"));
            boolean imageFound = false;
            for (Image image : this.client.images().imageList().get(req -> req.all(false)).opt().status200().payload().get()) {
                if (image.repoTags().contains(imageTag)) {
                    log.info("image found, no need to pull it");
                    imageFound = true;
                    break;
                }
            }
            if (!imageFound) {
                this.client.images().createImage().post(req -> req.fromImage(imageTag)).opt().status200().orElseThrow(() -> new AssertionError("error while pulling image"));
                log.info("image {} successfully pulled", imageTag);
            }

            ContainerCreationResult container = this.client.containers().createContainer().post(req -> req.name(containerName).payload(conf -> conf.image(imageTag))).opt().status201().orElseThrow(() -> new AssertionError("failed creating container")).payload();
            log.info("container created : {}", container);
        }

    }
}
