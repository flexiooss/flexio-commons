package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalContainerCreationResult;
import java.lang.String;

public interface ContainerCreationResult {
  static Builder builder() {
    return new ContainerCreationResult.Builder();
  }

  static ContainerCreationResult.Builder from(ContainerCreationResult value) {
    if(value != null) {
      return new ContainerCreationResult.Builder()
          .id(value.id())
          .warning(value.warning())
          ;
    }
    else {
      return null;
    }
  }

  String id();

  String warning();

  ContainerCreationResult withId(String value);

  ContainerCreationResult withWarning(String value);

  int hashCode();

  ContainerCreationResult changed(ContainerCreationResult.Changer changer);

  OptionalContainerCreationResult opt();

  class Builder {
    private String id;

    private String warning;

    public ContainerCreationResult build() {
      return new ContainerCreationResultImpl(this.id, this.warning);
    }

    public ContainerCreationResult.Builder id(String id) {
      this.id = id;
      return this;
    }

    public ContainerCreationResult.Builder warning(String warning) {
      this.warning = warning;
      return this;
    }
  }

  interface Changer {
    ContainerCreationResult.Builder configure(ContainerCreationResult.Builder builder);
  }
}
