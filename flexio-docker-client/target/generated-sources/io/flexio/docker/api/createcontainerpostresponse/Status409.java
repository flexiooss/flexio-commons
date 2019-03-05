package io.flexio.docker.api.createcontainerpostresponse;

import io.flexio.docker.api.createcontainerpostresponse.optional.OptionalStatus409;
import io.flexio.docker.api.types.Error;
import java.util.function.Consumer;

public interface Status409 {
  static Builder builder() {
    return new Status409.Builder();
  }

  static Status409.Builder from(Status409 value) {
    if(value != null) {
      return new Status409.Builder()
          .payload(value.payload())
          ;
    }
    else {
      return null;
    }
  }

  Error payload();

  Status409 withPayload(Error value);

  int hashCode();

  Status409 changed(Status409.Changer changer);

  OptionalStatus409 opt();

  class Builder {
    private Error payload;

    public Status409 build() {
      return new Status409Impl(this.payload);
    }

    public Status409.Builder payload(Error payload) {
      this.payload = payload;
      return this;
    }

    public Status409.Builder payload(Consumer<Error.Builder> payload) {
      Error.Builder builder = Error.builder();
      payload.accept(builder);
      return this.payload(builder.build());
    }
  }

  interface Changer {
    Status409.Builder configure(Status409.Builder builder);
  }
}
