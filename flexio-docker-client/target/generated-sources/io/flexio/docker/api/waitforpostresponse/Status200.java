package io.flexio.docker.api.waitforpostresponse;

import io.flexio.docker.api.types.WaitResult;
import io.flexio.docker.api.waitforpostresponse.optional.OptionalStatus200;
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

  WaitResult payload();

  Status200 withPayload(WaitResult value);

  int hashCode();

  Status200 changed(Status200.Changer changer);

  OptionalStatus200 opt();

  class Builder {
    private WaitResult payload;

    public Status200 build() {
      return new Status200Impl(this.payload);
    }

    public Status200.Builder payload(WaitResult payload) {
      this.payload = payload;
      return this;
    }

    public Status200.Builder payload(Consumer<WaitResult.Builder> payload) {
      WaitResult.Builder builder = WaitResult.builder();
      payload.accept(builder);
      return this.payload(builder.build());
    }
  }

  interface Changer {
    Status200.Builder configure(Status200.Builder builder);
  }
}
