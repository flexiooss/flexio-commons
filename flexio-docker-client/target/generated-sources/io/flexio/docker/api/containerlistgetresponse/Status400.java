package io.flexio.docker.api.containerlistgetresponse;

import io.flexio.docker.api.containerlistgetresponse.optional.OptionalStatus400;
import io.flexio.docker.api.types.Error;
import java.util.function.Consumer;

public interface Status400 {
  static Builder builder() {
    return new Status400.Builder();
  }

  static Status400.Builder from(Status400 value) {
    if(value != null) {
      return new Status400.Builder()
          .payload(value.payload())
          ;
    }
    else {
      return null;
    }
  }

  Error payload();

  Status400 withPayload(Error value);

  int hashCode();

  Status400 changed(Status400.Changer changer);

  OptionalStatus400 opt();

  class Builder {
    private Error payload;

    public Status400 build() {
      return new Status400Impl(this.payload);
    }

    public Status400.Builder payload(Error payload) {
      this.payload = payload;
      return this;
    }

    public Status400.Builder payload(Consumer<Error.Builder> payload) {
      Error.Builder builder = Error.builder();
      payload.accept(builder);
      return this.payload(builder.build());
    }
  }

  interface Changer {
    Status400.Builder configure(Status400.Builder builder);
  }
}
