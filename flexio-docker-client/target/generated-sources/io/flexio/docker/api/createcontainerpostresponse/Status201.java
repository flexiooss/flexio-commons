package io.flexio.docker.api.createcontainerpostresponse;

import io.flexio.docker.api.createcontainerpostresponse.optional.OptionalStatus201;
import io.flexio.docker.api.types.ContainerCreationResult;
import java.util.function.Consumer;

public interface Status201 {
  static Builder builder() {
    return new Status201.Builder();
  }

  static Status201.Builder from(Status201 value) {
    if(value != null) {
      return new Status201.Builder()
          .payload(value.payload())
          ;
    }
    else {
      return null;
    }
  }

  ContainerCreationResult payload();

  Status201 withPayload(ContainerCreationResult value);

  int hashCode();

  Status201 changed(Status201.Changer changer);

  OptionalStatus201 opt();

  class Builder {
    private ContainerCreationResult payload;

    public Status201 build() {
      return new Status201Impl(this.payload);
    }

    public Status201.Builder payload(ContainerCreationResult payload) {
      this.payload = payload;
      return this;
    }

    public Status201.Builder payload(Consumer<ContainerCreationResult.Builder> payload) {
      ContainerCreationResult.Builder builder = ContainerCreationResult.builder();
      payload.accept(builder);
      return this.payload(builder.build());
    }
  }

  interface Changer {
    Status201.Builder configure(Status201.Builder builder);
  }
}
