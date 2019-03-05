package io.flexio.docker.api;

import java.lang.Override;
import java.util.function.Function;

public interface DockerEngineAPIHandlers {
  Function<VersionGetRequest, VersionGetResponse> versionGetHandler();

  Function<ContainerListGetRequest, ContainerListGetResponse> containerListGetHandler();

  Function<CreateContainerPostRequest, CreateContainerPostResponse> createContainerPostHandler();

  Function<ContainerDeleteRequest, ContainerDeleteResponse> containerDeleteHandler();

  Function<InspectGetRequest, InspectGetResponse> inspectGetHandler();

  Function<StartPostRequest, StartPostResponse> startPostHandler();

  Function<StopPostRequest, StopPostResponse> stopPostHandler();

  Function<RestartPostRequest, RestartPostResponse> restartPostHandler();

  Function<KillPostRequest, KillPostResponse> killPostHandler();

  Function<WaitForPostRequest, WaitForPostResponse> waitForPostHandler();

  Function<ImageListGetRequest, ImageListGetResponse> imageListGetHandler();

  Function<CreateImagePostRequest, CreateImagePostResponse> createImagePostHandler();

  Function<InspectImageGetRequest, InspectImageGetResponse> inspectImageGetHandler();

  class Builder {
    Function<VersionGetRequest, VersionGetResponse> versionGetHandler;

    Function<ContainerListGetRequest, ContainerListGetResponse> containerListGetHandler;

    Function<CreateContainerPostRequest, CreateContainerPostResponse> createContainerPostHandler;

    Function<ContainerDeleteRequest, ContainerDeleteResponse> containerDeleteHandler;

    Function<InspectGetRequest, InspectGetResponse> inspectGetHandler;

    Function<StartPostRequest, StartPostResponse> startPostHandler;

    Function<StopPostRequest, StopPostResponse> stopPostHandler;

    Function<RestartPostRequest, RestartPostResponse> restartPostHandler;

    Function<KillPostRequest, KillPostResponse> killPostHandler;

    Function<WaitForPostRequest, WaitForPostResponse> waitForPostHandler;

    Function<ImageListGetRequest, ImageListGetResponse> imageListGetHandler;

    Function<CreateImagePostRequest, CreateImagePostResponse> createImagePostHandler;

    Function<InspectImageGetRequest, InspectImageGetResponse> inspectImageGetHandler;

    public Builder versionGetHandler(Function<VersionGetRequest, VersionGetResponse> handler) {
      this.versionGetHandler = handler;
      return this;
    }

    public Builder containerListGetHandler(Function<ContainerListGetRequest, ContainerListGetResponse> handler) {
      this.containerListGetHandler = handler;
      return this;
    }

    public Builder createContainerPostHandler(Function<CreateContainerPostRequest, CreateContainerPostResponse> handler) {
      this.createContainerPostHandler = handler;
      return this;
    }

    public Builder containerDeleteHandler(Function<ContainerDeleteRequest, ContainerDeleteResponse> handler) {
      this.containerDeleteHandler = handler;
      return this;
    }

    public Builder inspectGetHandler(Function<InspectGetRequest, InspectGetResponse> handler) {
      this.inspectGetHandler = handler;
      return this;
    }

    public Builder startPostHandler(Function<StartPostRequest, StartPostResponse> handler) {
      this.startPostHandler = handler;
      return this;
    }

    public Builder stopPostHandler(Function<StopPostRequest, StopPostResponse> handler) {
      this.stopPostHandler = handler;
      return this;
    }

    public Builder restartPostHandler(Function<RestartPostRequest, RestartPostResponse> handler) {
      this.restartPostHandler = handler;
      return this;
    }

    public Builder killPostHandler(Function<KillPostRequest, KillPostResponse> handler) {
      this.killPostHandler = handler;
      return this;
    }

    public Builder waitForPostHandler(Function<WaitForPostRequest, WaitForPostResponse> handler) {
      this.waitForPostHandler = handler;
      return this;
    }

    public Builder imageListGetHandler(Function<ImageListGetRequest, ImageListGetResponse> handler) {
      this.imageListGetHandler = handler;
      return this;
    }

    public Builder createImagePostHandler(Function<CreateImagePostRequest, CreateImagePostResponse> handler) {
      this.createImagePostHandler = handler;
      return this;
    }

    public Builder inspectImageGetHandler(Function<InspectImageGetRequest, InspectImageGetResponse> handler) {
      this.inspectImageGetHandler = handler;
      return this;
    }

    public DockerEngineAPIHandlers build() {
      return new DefaultImpl(this.versionGetHandler, this.containerListGetHandler, this.createContainerPostHandler, this.containerDeleteHandler, this.inspectGetHandler, this.startPostHandler, this.stopPostHandler, this.restartPostHandler, this.killPostHandler, this.waitForPostHandler, this.imageListGetHandler, this.createImagePostHandler, this.inspectImageGetHandler);
    }

    private static class DefaultImpl implements DockerEngineAPIHandlers {
      private final Function<VersionGetRequest, VersionGetResponse> versionGetHandler;

      private final Function<ContainerListGetRequest, ContainerListGetResponse> containerListGetHandler;

      private final Function<CreateContainerPostRequest, CreateContainerPostResponse> createContainerPostHandler;

      private final Function<ContainerDeleteRequest, ContainerDeleteResponse> containerDeleteHandler;

      private final Function<InspectGetRequest, InspectGetResponse> inspectGetHandler;

      private final Function<StartPostRequest, StartPostResponse> startPostHandler;

      private final Function<StopPostRequest, StopPostResponse> stopPostHandler;

      private final Function<RestartPostRequest, RestartPostResponse> restartPostHandler;

      private final Function<KillPostRequest, KillPostResponse> killPostHandler;

      private final Function<WaitForPostRequest, WaitForPostResponse> waitForPostHandler;

      private final Function<ImageListGetRequest, ImageListGetResponse> imageListGetHandler;

      private final Function<CreateImagePostRequest, CreateImagePostResponse> createImagePostHandler;

      private final Function<InspectImageGetRequest, InspectImageGetResponse> inspectImageGetHandler;

      private DefaultImpl(Function<VersionGetRequest, VersionGetResponse> versionGetHandler, Function<ContainerListGetRequest, ContainerListGetResponse> containerListGetHandler, Function<CreateContainerPostRequest, CreateContainerPostResponse> createContainerPostHandler, Function<ContainerDeleteRequest, ContainerDeleteResponse> containerDeleteHandler, Function<InspectGetRequest, InspectGetResponse> inspectGetHandler, Function<StartPostRequest, StartPostResponse> startPostHandler, Function<StopPostRequest, StopPostResponse> stopPostHandler, Function<RestartPostRequest, RestartPostResponse> restartPostHandler, Function<KillPostRequest, KillPostResponse> killPostHandler, Function<WaitForPostRequest, WaitForPostResponse> waitForPostHandler, Function<ImageListGetRequest, ImageListGetResponse> imageListGetHandler, Function<CreateImagePostRequest, CreateImagePostResponse> createImagePostHandler, Function<InspectImageGetRequest, InspectImageGetResponse> inspectImageGetHandler) {
        this.versionGetHandler = versionGetHandler;
        this.containerListGetHandler = containerListGetHandler;
        this.createContainerPostHandler = createContainerPostHandler;
        this.containerDeleteHandler = containerDeleteHandler;
        this.inspectGetHandler = inspectGetHandler;
        this.startPostHandler = startPostHandler;
        this.stopPostHandler = stopPostHandler;
        this.restartPostHandler = restartPostHandler;
        this.killPostHandler = killPostHandler;
        this.waitForPostHandler = waitForPostHandler;
        this.imageListGetHandler = imageListGetHandler;
        this.createImagePostHandler = createImagePostHandler;
        this.inspectImageGetHandler = inspectImageGetHandler;
      }

      @Override
      public Function<VersionGetRequest, VersionGetResponse> versionGetHandler() {
        return this.versionGetHandler;
      }

      @Override
      public Function<ContainerListGetRequest, ContainerListGetResponse> containerListGetHandler() {
        return this.containerListGetHandler;
      }

      @Override
      public Function<CreateContainerPostRequest, CreateContainerPostResponse> createContainerPostHandler() {
        return this.createContainerPostHandler;
      }

      @Override
      public Function<ContainerDeleteRequest, ContainerDeleteResponse> containerDeleteHandler() {
        return this.containerDeleteHandler;
      }

      @Override
      public Function<InspectGetRequest, InspectGetResponse> inspectGetHandler() {
        return this.inspectGetHandler;
      }

      @Override
      public Function<StartPostRequest, StartPostResponse> startPostHandler() {
        return this.startPostHandler;
      }

      @Override
      public Function<StopPostRequest, StopPostResponse> stopPostHandler() {
        return this.stopPostHandler;
      }

      @Override
      public Function<RestartPostRequest, RestartPostResponse> restartPostHandler() {
        return this.restartPostHandler;
      }

      @Override
      public Function<KillPostRequest, KillPostResponse> killPostHandler() {
        return this.killPostHandler;
      }

      @Override
      public Function<WaitForPostRequest, WaitForPostResponse> waitForPostHandler() {
        return this.waitForPostHandler;
      }

      @Override
      public Function<ImageListGetRequest, ImageListGetResponse> imageListGetHandler() {
        return this.imageListGetHandler;
      }

      @Override
      public Function<CreateImagePostRequest, CreateImagePostResponse> createImagePostHandler() {
        return this.createImagePostHandler;
      }

      @Override
      public Function<InspectImageGetRequest, InspectImageGetResponse> inspectImageGetHandler() {
        return this.inspectImageGetHandler;
      }
    }
  }
}
