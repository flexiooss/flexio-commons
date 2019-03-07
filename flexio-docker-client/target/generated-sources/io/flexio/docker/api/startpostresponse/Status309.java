package io.flexio.docker.api.startpostresponse;

import io.flexio.docker.api.startpostresponse.optional.OptionalStatus309;
import io.flexio.docker.api.types.Error;
import java.util.function.Consumer;

public interface Status309 {
  static Builder builder() {
    return new Status309.Builder();
  }

  static Status309.Builder from(Status309 value) {
    if(value != null) {
      return new Status309.Builder()
          .payload(value.payload())
          ;
    }
    else {
      return null;
    }
  }

  Error payload();

  Status309 withPayload(Error value);

  int hashCode();

  Status309 changed(Status309.Changer changer);

  OptionalStatus309 opt();

  class Builder {
    private Error payload;

    public Status309 build() {
      return new Status309Impl(this.payload);
    }

    public Status309.Builder payload(Error payload) {
      this.payload = payload;
      return this;
    }

    public Status309.Builder payload(Consumer<Error.Builder> payload) {
      Error.Builder builder = Error.builder();
      payload.accept(builder);
      return this.payload(builder.build());
    }
  }

  interface Changer {
    Status309.Builder configure(Status309.Builder builder);
  }
}
