package io.flexio.docker.api.client;

import io.flexio.docker.api.ContainerDeleteRequest;
import io.flexio.docker.api.ContainerDeleteResponse;
import io.flexio.docker.api.ContainerListGetRequest;
import io.flexio.docker.api.ContainerListGetResponse;
import io.flexio.docker.api.CreateContainerPostRequest;
import io.flexio.docker.api.CreateContainerPostResponse;
import io.flexio.docker.api.CreateImagePostRequest;
import io.flexio.docker.api.CreateImagePostResponse;
import io.flexio.docker.api.ImageListGetRequest;
import io.flexio.docker.api.ImageListGetResponse;
import io.flexio.docker.api.InspectGetRequest;
import io.flexio.docker.api.InspectGetResponse;
import io.flexio.docker.api.InspectImageGetRequest;
import io.flexio.docker.api.InspectImageGetResponse;
import io.flexio.docker.api.KillPostRequest;
import io.flexio.docker.api.KillPostResponse;
import io.flexio.docker.api.RestartPostRequest;
import io.flexio.docker.api.RestartPostResponse;
import io.flexio.docker.api.StartPostRequest;
import io.flexio.docker.api.StartPostResponse;
import io.flexio.docker.api.StopPostRequest;
import io.flexio.docker.api.StopPostResponse;
import io.flexio.docker.api.VersionGetRequest;
import io.flexio.docker.api.VersionGetResponse;
import io.flexio.docker.api.WaitForPostRequest;
import io.flexio.docker.api.WaitForPostResponse;
import java.io.IOException;
import java.lang.String;
import java.util.function.Consumer;

public interface DockerEngineAPIClient {
  String REQUESTER_CLASSNAME = "io.flexio.docker.api.client.DockerEngineAPIRequesterClient";

  String HANDLERS_CLASSNAME = "io.flexio.docker.api.client.DockerEngineAPIHandlersClient";

  String API_NAME = "docker-engine-api";

  Version version();

  Containers containers();

  Images images();

  interface Version {
    VersionGetResponse get(VersionGetRequest request) throws IOException;

    VersionGetResponse get(Consumer<VersionGetRequest.Builder> request) throws IOException;
  }

  interface Containers {
    ContainerList containerList();

    CreateContainer createContainer();

    Container container();

    interface ContainerList {
      ContainerListGetResponse get(ContainerListGetRequest request) throws IOException;

      ContainerListGetResponse get(Consumer<ContainerListGetRequest.Builder> request) throws IOException;
    }

    interface CreateContainer {
      CreateContainerPostResponse post(CreateContainerPostRequest request) throws IOException;

      CreateContainerPostResponse post(Consumer<CreateContainerPostRequest.Builder> request) throws IOException;
    }

    interface Container {
      ContainerDeleteResponse delete(ContainerDeleteRequest request) throws IOException;

      ContainerDeleteResponse delete(Consumer<ContainerDeleteRequest.Builder> request) throws IOException;

      Inspect inspect();

      Start start();

      Stop stop();

      Restart restart();

      Kill kill();

      WaitFor waitFor();

      interface Inspect {
        InspectGetResponse get(InspectGetRequest request) throws IOException;

        InspectGetResponse get(Consumer<InspectGetRequest.Builder> request) throws IOException;
      }

      interface Start {
        StartPostResponse post(StartPostRequest request) throws IOException;

        StartPostResponse post(Consumer<StartPostRequest.Builder> request) throws IOException;
      }

      interface Stop {
        StopPostResponse post(StopPostRequest request) throws IOException;

        StopPostResponse post(Consumer<StopPostRequest.Builder> request) throws IOException;
      }

      interface Restart {
        RestartPostResponse post(RestartPostRequest request) throws IOException;

        RestartPostResponse post(Consumer<RestartPostRequest.Builder> request) throws IOException;
      }

      interface Kill {
        KillPostResponse post(KillPostRequest request) throws IOException;

        KillPostResponse post(Consumer<KillPostRequest.Builder> request) throws IOException;
      }

      interface WaitFor {
        WaitForPostResponse post(WaitForPostRequest request) throws IOException;

        WaitForPostResponse post(Consumer<WaitForPostRequest.Builder> request) throws IOException;
      }
    }
  }

  interface Images {
    ImageList imageList();

    CreateImage createImage();

    InspectImage inspectImage();

    interface ImageList {
      ImageListGetResponse get(ImageListGetRequest request) throws IOException;

      ImageListGetResponse get(Consumer<ImageListGetRequest.Builder> request) throws IOException;
    }

    interface CreateImage {
      CreateImagePostResponse post(CreateImagePostRequest request) throws IOException;

      CreateImagePostResponse post(Consumer<CreateImagePostRequest.Builder> request) throws IOException;
    }

    interface InspectImage {
      InspectImageGetResponse get(InspectImageGetRequest request) throws IOException;

      InspectImageGetResponse get(Consumer<InspectImageGetRequest.Builder> request) throws IOException;
    }
  }
}
