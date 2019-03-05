package io.flexio.docker.api.inspectgetresponse;

import io.flexio.docker.api.inspectgetresponse.optional.OptionalStatus500;
import io.flexio.docker.api.types.Error;
import java.util.function.Consumer;

public interface Status500 {
  static Builder builder() {
    return new Status500.Builder();
  }

  static Status500.Builder from(Status500 value) {
    if(value != null) {
      return new Status500.Builder()
          .payload(value.payload())
          ;
    }
    else {
      return null;
    }
  }

  Error payload();

  Status500 withPayload(Error value);

  int hashCode();

  Status500 changed(Status500.Changer changer);

  OptionalStatus500 opt();

  class Builder {
    private Error payload;

    public Status500 build() {
      return new Status500Impl(this.payload);
    }

    public Status500.Builder payload(Error payload) {
      this.payload = payload;
      return this;
    }

    public Status500.Builder payload(Consumer<Error.Builder> payload) {
      Error.Builder builder = Error.builder();
      payload.accept(builder);
      return this.payload(builder.build());
    }
  }

  interface Changer {
    Status500.Builder configure(Status500.Builder builder);
  }
}
