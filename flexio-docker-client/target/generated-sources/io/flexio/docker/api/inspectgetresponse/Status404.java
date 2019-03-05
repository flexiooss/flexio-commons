package io.flexio.docker.api.inspectgetresponse;

import io.flexio.docker.api.inspectgetresponse.optional.OptionalStatus404;
import io.flexio.docker.api.types.Error;
import java.util.function.Consumer;

public interface Status404 {
  static Builder builder() {
    return new Status404.Builder();
  }

  static Status404.Builder from(Status404 value) {
    if(value != null) {
      return new Status404.Builder()
          .payload(value.payload())
          ;
    }
    else {
      return null;
    }
  }

  Error payload();

  Status404 withPayload(Error value);

  int hashCode();

  Status404 changed(Status404.Changer changer);

  OptionalStatus404 opt();

  class Builder {
    private Error payload;

    public Status404 build() {
      return new Status404Impl(this.payload);
    }

    public Status404.Builder payload(Error payload) {
      this.payload = payload;
      return this;
    }

    public Status404.Builder payload(Consumer<Error.Builder> payload) {
      Error.Builder builder = Error.builder();
      payload.accept(builder);
      return this.payload(builder.build());
    }
  }

  interface Changer {
    Status404.Builder configure(Status404.Builder builder);
  }
}
