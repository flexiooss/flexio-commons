package io.flexio.docker.api;

import io.flexio.docker.api.containerdeleteresponse.Status204;
import io.flexio.docker.api.containerdeleteresponse.Status400;
import io.flexio.docker.api.optional.OptionalContainerDeleteResponse;
import java.util.function.Consumer;

public interface ContainerDeleteResponse {
  static Builder builder() {
    return new ContainerDeleteResponse.Builder();
  }

  static ContainerDeleteResponse.Builder from(ContainerDeleteResponse value) {
    if(value != null) {
      return new ContainerDeleteResponse.Builder()
          .status204(value.status204())
          .status400(value.status400())
          ;
    }
    else {
      return null;
    }
  }

  Status204 status204();

  Status400 status400();

  ContainerDeleteResponse withStatus204(Status204 value);

  ContainerDeleteResponse withStatus400(Status400 value);

  int hashCode();

  ContainerDeleteResponse changed(ContainerDeleteResponse.Changer changer);

  OptionalContainerDeleteResponse opt();

  class Builder {
    private Status204 status204;

    private Status400 status400;

    public ContainerDeleteResponse build() {
      return new ContainerDeleteResponseImpl(this.status204, this.status400);
    }

    public ContainerDeleteResponse.Builder status204(Status204 status204) {
      this.status204 = status204;
      return this;
    }

    public ContainerDeleteResponse.Builder status204(Consumer<Status204.Builder> status204) {
      Status204.Builder builder = Status204.builder();
      status204.accept(builder);
      return this.status204(builder.build());
    }

    public ContainerDeleteResponse.Builder status400(Status400 status400) {
      this.status400 = status400;
      return this;
    }

    public ContainerDeleteResponse.Builder status400(Consumer<Status400.Builder> status400) {
      Status400.Builder builder = Status400.builder();
      status400.accept(builder);
      return this.status400(builder.build());
    }
  }

  interface Changer {
    ContainerDeleteResponse.Builder configure(ContainerDeleteResponse.Builder builder);
  }
}
