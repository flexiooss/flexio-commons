package io.flexio.docker.api.inspectgetresponse;

import io.flexio.docker.api.inspectgetresponse.optional.OptionalStatus200;
import io.flexio.docker.api.types.Container;
import java.util.function.Consumer;

public interface Status200 {
  static Builder builder() {
    return new Status200.Builder();
  }

  static Status200.Builder from(Status200 value) {
    if(value != null) {
      return new Status200.Builder()
          .payload(value.payload())
          ;
    }
    else {
      return null;
    }
  }

  Container payload();

  Status200 withPayload(Container value);

  int hashCode();

  Status200 changed(Status200.Changer changer);

  OptionalStatus200 opt();

  class Builder {
    private Container payload;

    public Status200 build() {
      return new Status200Impl(this.payload);
    }

    public Status200.Builder payload(Container payload) {
      this.payload = payload;
      return this;
    }

    public Status200.Builder payload(Consumer<Container.Builder> payload) {
      Container.Builder builder = Container.builder();
      payload.accept(builder);
      return this.payload(builder.build());
    }
  }

  interface Changer {
    Status200.Builder configure(Status200.Builder builder);
  }
}
