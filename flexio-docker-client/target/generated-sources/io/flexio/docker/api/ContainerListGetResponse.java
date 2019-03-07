package io.flexio.docker.api;

import io.flexio.docker.api.containerlistgetresponse.Status200;
import io.flexio.docker.api.containerlistgetresponse.Status400;
import io.flexio.docker.api.containerlistgetresponse.Status500;
import io.flexio.docker.api.optional.OptionalContainerListGetResponse;
import java.util.function.Consumer;

public interface ContainerListGetResponse {
  static Builder builder() {
    return new ContainerListGetResponse.Builder();
  }

  static ContainerListGetResponse.Builder from(ContainerListGetResponse value) {
    if(value != null) {
      return new ContainerListGetResponse.Builder()
          .status200(value.status200())
          .status400(value.status400())
          .status500(value.status500())
          ;
    }
    else {
      return null;
    }
  }

  Status200 status200();

  Status400 status400();

  Status500 status500();

  ContainerListGetResponse withStatus200(Status200 value);

  ContainerListGetResponse withStatus400(Status400 value);

  ContainerListGetResponse withStatus500(Status500 value);

  int hashCode();

  ContainerListGetResponse changed(ContainerListGetResponse.Changer changer);

  OptionalContainerListGetResponse opt();

  class Builder {
    private Status200 status200;

    private Status400 status400;

    private Status500 status500;

    public ContainerListGetResponse build() {
      return new ContainerListGetResponseImpl(this.status200, this.status400, this.status500);
    }

    public ContainerListGetResponse.Builder status200(Status200 status200) {
      this.status200 = status200;
      return this;
    }

    public ContainerListGetResponse.Builder status200(Consumer<Status200.Builder> status200) {
      Status200.Builder builder = Status200.builder();
      status200.accept(builder);
      return this.status200(builder.build());
    }

    public ContainerListGetResponse.Builder status400(Status400 status400) {
      this.status400 = status400;
      return this;
    }

    public ContainerListGetResponse.Builder status400(Consumer<Status400.Builder> status400) {
      Status400.Builder builder = Status400.builder();
      status400.accept(builder);
      return this.status400(builder.build());
    }

    public ContainerListGetResponse.Builder status500(Status500 status500) {
      this.status500 = status500;
      return this;
    }

    public ContainerListGetResponse.Builder status500(Consumer<Status500.Builder> status500) {
      Status500.Builder builder = Status500.builder();
      status500.accept(builder);
      return this.status500(builder.build());
    }
  }

  interface Changer {
    ContainerListGetResponse.Builder configure(ContainerListGetResponse.Builder builder);
  }
}
