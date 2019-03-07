package io.flexio.docker.api.client;

import io.flexio.docker.api.ContainerDeleteRequest;
import io.flexio.docker.api.ContainerDeleteResponse;
import io.flexio.docker.api.ContainerListGetRequest;
import io.flexio.docker.api.ContainerListGetResponse;
import io.flexio.docker.api.CreateContainerPostRequest;
import io.flexio.docker.api.CreateContainerPostResponse;
import io.flexio.docker.api.CreateImagePostRequest;
import io.flexio.docker.api.CreateImagePostResponse;
import io.flexio.docker.api.DockerEngineAPIHandlers;
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
import java.lang.InterruptedException;
import java.lang.String;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class DockerEngineAPIHandlersClient implements DockerEngineAPIClient {
  private final DockerEngineAPIHandlers handlers;

  private final ExecutorService executor;

  public DockerEngineAPIHandlersClient(DockerEngineAPIHandlers handlers, ExecutorService executor) {
    this.handlers = handlers;
    this.executor = executor;
  }

  private <T> T call(Callable<T> callable, String action) throws IOException {
    try {
      return this.executor.submit(callable).get();
    } catch(InterruptedException | ExecutionException e) {
      throw new IOException("error invoking " + action, e);
    }
  }

  public DockerEngineAPIClient.Version version() {
    return new VersionImpl();
  }

  public DockerEngineAPIClient.Containers containers() {
    return new ContainersImpl();
  }

  public DockerEngineAPIClient.Images images() {
    return new ImagesImpl();
  }

  private class VersionImpl implements DockerEngineAPIClient.Version {
    public VersionGetResponse get(VersionGetRequest request) throws IOException {
      return call(() -> handlers.versionGetHandler().apply(request), "Version get");
    }

    public VersionGetResponse get(Consumer<VersionGetRequest.Builder> request) throws IOException {
      return call(() -> {
          	VersionGetRequest.Builder builder = VersionGetRequest.builder();
          	request.accept(builder);
          	return handlers.versionGetHandler().apply(builder.build());
          }, "Version get");
    }
  }

  private class ContainersImpl implements DockerEngineAPIClient.Containers {
    public DockerEngineAPIClient.Containers.ContainerList containerList() {
      return new ContainerListImpl();
    }

    public DockerEngineAPIClient.Containers.CreateContainer createContainer() {
      return new CreateContainerImpl();
    }

    public DockerEngineAPIClient.Containers.Container container() {
      return new ContainerImpl();
    }

    private class ContainerListImpl implements DockerEngineAPIClient.Containers.ContainerList {
      public ContainerListGetResponse get(ContainerListGetRequest request) throws IOException {
        return call(() -> handlers.containerListGetHandler().apply(request), "Container List get");
      }

      public ContainerListGetResponse get(Consumer<ContainerListGetRequest.Builder> request) throws IOException {
        return call(() -> {
            	ContainerListGetRequest.Builder builder = ContainerListGetRequest.builder();
            	request.accept(builder);
            	return handlers.containerListGetHandler().apply(builder.build());
            }, "Container List get");
      }
    }

    private class CreateContainerImpl implements DockerEngineAPIClient.Containers.CreateContainer {
      public CreateContainerPostResponse post(CreateContainerPostRequest request) throws IOException {
        return call(() -> handlers.createContainerPostHandler().apply(request), "Create Container post");
      }

      public CreateContainerPostResponse post(Consumer<CreateContainerPostRequest.Builder> request) throws IOException {
        return call(() -> {
            	CreateContainerPostRequest.Builder builder = CreateContainerPostRequest.builder();
            	request.accept(builder);
            	return handlers.createContainerPostHandler().apply(builder.build());
            }, "Create Container post");
      }
    }

    private class ContainerImpl implements DockerEngineAPIClient.Containers.Container {
      public DockerEngineAPIClient.Containers.Container.Inspect inspect() {
        return new InspectImpl();
      }

      public DockerEngineAPIClient.Containers.Container.Start start() {
        return new StartImpl();
      }

      public DockerEngineAPIClient.Containers.Container.Stop stop() {
        return new StopImpl();
      }

      public DockerEngineAPIClient.Containers.Container.Restart restart() {
        return new RestartImpl();
      }

      public DockerEngineAPIClient.Containers.Container.Kill kill() {
        return new KillImpl();
      }

      public DockerEngineAPIClient.Containers.Container.WaitFor waitFor() {
        return new WaitForImpl();
      }

      public ContainerDeleteResponse delete(ContainerDeleteRequest request) throws IOException {
        return call(() -> handlers.containerDeleteHandler().apply(request), "Container delete");
      }

      public ContainerDeleteResponse delete(Consumer<ContainerDeleteRequest.Builder> request) throws IOException {
        return call(() -> {
            	ContainerDeleteRequest.Builder builder = ContainerDeleteRequest.builder();
            	request.accept(builder);
            	return handlers.containerDeleteHandler().apply(builder.build());
            }, "Container delete");
      }

      private class InspectImpl implements DockerEngineAPIClient.Containers.Container.Inspect {
        public InspectGetResponse get(InspectGetRequest request) throws IOException {
          return call(() -> handlers.inspectGetHandler().apply(request), "Inspect get");
        }

        public InspectGetResponse get(Consumer<InspectGetRequest.Builder> request) throws IOException {
          return call(() -> {
              	InspectGetRequest.Builder builder = InspectGetRequest.builder();
              	request.accept(builder);
              	return handlers.inspectGetHandler().apply(builder.build());
              }, "Inspect get");
        }
      }

      private class StartImpl implements DockerEngineAPIClient.Containers.Container.Start {
        public StartPostResponse post(StartPostRequest request) throws IOException {
          return call(() -> handlers.startPostHandler().apply(request), "Start post");
        }

        public StartPostResponse post(Consumer<StartPostRequest.Builder> request) throws IOException {
          return call(() -> {
              	StartPostRequest.Builder builder = StartPostRequest.builder();
              	request.accept(builder);
              	return handlers.startPostHandler().apply(builder.build());
              }, "Start post");
        }
      }

      private class StopImpl implements DockerEngineAPIClient.Containers.Container.Stop {
        public StopPostResponse post(StopPostRequest request) throws IOException {
          return call(() -> handlers.stopPostHandler().apply(request), "Stop post");
        }

        public StopPostResponse post(Consumer<StopPostRequest.Builder> request) throws IOException {
          return call(() -> {
              	StopPostRequest.Builder builder = StopPostRequest.builder();
              	request.accept(builder);
              	return handlers.stopPostHandler().apply(builder.build());
              }, "Stop post");
        }
      }

      private class RestartImpl implements DockerEngineAPIClient.Containers.Container.Restart {
        public RestartPostResponse post(RestartPostRequest request) throws IOException {
          return call(() -> handlers.restartPostHandler().apply(request), "Restart post");
        }

        public RestartPostResponse post(Consumer<RestartPostRequest.Builder> request) throws IOException {
          return call(() -> {
              	RestartPostRequest.Builder builder = RestartPostRequest.builder();
              	request.accept(builder);
              	return handlers.restartPostHandler().apply(builder.build());
              }, "Restart post");
        }
      }

      private class KillImpl implements DockerEngineAPIClient.Containers.Container.Kill {
        public KillPostResponse post(KillPostRequest request) throws IOException {
          return call(() -> handlers.killPostHandler().apply(request), "Kill post");
        }

        public KillPostResponse post(Consumer<KillPostRequest.Builder> request) throws IOException {
          return call(() -> {
              	KillPostRequest.Builder builder = KillPostRequest.builder();
              	request.accept(builder);
              	return handlers.killPostHandler().apply(builder.build());
              }, "Kill post");
        }
      }

      private class WaitForImpl implements DockerEngineAPIClient.Containers.Container.WaitFor {
        public WaitForPostResponse post(WaitForPostRequest request) throws IOException {
          return call(() -> handlers.waitForPostHandler().apply(request), "WaitFor post");
        }

        public WaitForPostResponse post(Consumer<WaitForPostRequest.Builder> request) throws IOException {
          return call(() -> {
              	WaitForPostRequest.Builder builder = WaitForPostRequest.builder();
              	request.accept(builder);
              	return handlers.waitForPostHandler().apply(builder.build());
              }, "WaitFor post");
        }
      }
    }
  }

  private class ImagesImpl implements DockerEngineAPIClient.Images {
    public DockerEngineAPIClient.Images.ImageList imageList() {
      return new ImageListImpl();
    }

    public DockerEngineAPIClient.Images.CreateImage createImage() {
      return new CreateImageImpl();
    }

    public DockerEngineAPIClient.Images.InspectImage inspectImage() {
      return new InspectImageImpl();
    }

    private class ImageListImpl implements DockerEngineAPIClient.Images.ImageList {
      public ImageListGetResponse get(ImageListGetRequest request) throws IOException {
        return call(() -> handlers.imageListGetHandler().apply(request), "ImageList get");
      }

      public ImageListGetResponse get(Consumer<ImageListGetRequest.Builder> request) throws IOException {
        return call(() -> {
            	ImageListGetRequest.Builder builder = ImageListGetRequest.builder();
            	request.accept(builder);
            	return handlers.imageListGetHandler().apply(builder.build());
            }, "ImageList get");
      }
    }

    private class CreateImageImpl implements DockerEngineAPIClient.Images.CreateImage {
      public CreateImagePostResponse post(CreateImagePostRequest request) throws IOException {
        return call(() -> handlers.createImagePostHandler().apply(request), "CreateImage post");
      }

      public CreateImagePostResponse post(Consumer<CreateImagePostRequest.Builder> request) throws IOException {
        return call(() -> {
            	CreateImagePostRequest.Builder builder = CreateImagePostRequest.builder();
            	request.accept(builder);
            	return handlers.createImagePostHandler().apply(builder.build());
            }, "CreateImage post");
      }
    }

    private class InspectImageImpl implements DockerEngineAPIClient.Images.InspectImage {
      public InspectImageGetResponse get(InspectImageGetRequest request) throws IOException {
        return call(() -> handlers.inspectImageGetHandler().apply(request), "InspectImage get");
      }

      public InspectImageGetResponse get(Consumer<InspectImageGetRequest.Builder> request) throws IOException {
        return call(() -> {
            	InspectImageGetRequest.Builder builder = InspectImageGetRequest.builder();
            	request.accept(builder);
            	return handlers.inspectImageGetHandler().apply(builder.build());
            }, "InspectImage get");
      }
    }
  }
}
