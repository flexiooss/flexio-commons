package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalCreateContainerPostRequest;
import io.flexio.docker.api.types.ContainerCreationData;
import java.lang.String;
import java.util.function.Consumer;

public interface CreateContainerPostRequest {
  static Builder builder() {
    return new CreateContainerPostRequest.Builder();
  }

  static CreateContainerPostRequest.Builder from(CreateContainerPostRequest value) {
    if(value != null) {
      return new CreateContainerPostRequest.Builder()
          .name(value.name())
          .payload(value.payload())
          ;
    }
    else {
      return null;
    }
  }

  String name();

  ContainerCreationData payload();

  CreateContainerPostRequest withName(String value);

  CreateContainerPostRequest withPayload(ContainerCreationData value);

  int hashCode();

  CreateContainerPostRequest changed(CreateContainerPostRequest.Changer changer);

  OptionalCreateContainerPostRequest opt();

  class Builder {
    private String name;

    private ContainerCreationData payload;

    public CreateContainerPostRequest build() {
      return new CreateContainerPostRequestImpl(this.name, this.payload);
    }

    public CreateContainerPostRequest.Builder name(String name) {
      this.name = name;
      return this;
    }

    public CreateContainerPostRequest.Builder payload(ContainerCreationData payload) {
      this.payload = payload;
      return this;
    }

    public CreateContainerPostRequest.Builder payload(Consumer<ContainerCreationData.Builder> payload) {
      ContainerCreationData.Builder builder = ContainerCreationData.builder();
      payload.accept(builder);
      return this.payload(builder.build());
    }
  }

  interface Changer {
    CreateContainerPostRequest.Builder configure(CreateContainerPostRequest.Builder builder);
  }
}
