package io.flexio.docker.api.versiongetresponse;

import io.flexio.docker.api.types.Version;
import io.flexio.docker.api.versiongetresponse.optional.OptionalStatus200;
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

  Version payload();

  Status200 withPayload(Version value);

  int hashCode();

  Status200 changed(Status200.Changer changer);

  OptionalStatus200 opt();

  class Builder {
    private Version payload;

    public Status200 build() {
      return new Status200Impl(this.payload);
    }

    public Status200.Builder payload(Version payload) {
      this.payload = payload;
      return this;
    }

    public Status200.Builder payload(Consumer<Version.Builder> payload) {
      Version.Builder builder = Version.builder();
      payload.accept(builder);
      return this.payload(builder.build());
    }
  }

  interface Changer {
    Status200.Builder configure(Status200.Builder builder);
  }
}
