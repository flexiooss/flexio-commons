package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalVersionGetResponse;
import io.flexio.docker.api.versiongetresponse.Status200;
import java.util.function.Consumer;

public interface VersionGetResponse {
  static Builder builder() {
    return new VersionGetResponse.Builder();
  }

  static VersionGetResponse.Builder from(VersionGetResponse value) {
    if(value != null) {
      return new VersionGetResponse.Builder()
          .status200(value.status200())
          ;
    }
    else {
      return null;
    }
  }

  Status200 status200();

  VersionGetResponse withStatus200(Status200 value);

  int hashCode();

  VersionGetResponse changed(VersionGetResponse.Changer changer);

  OptionalVersionGetResponse opt();

  class Builder {
    private Status200 status200;

    public VersionGetResponse build() {
      return new VersionGetResponseImpl(this.status200);
    }

    public VersionGetResponse.Builder status200(Status200 status200) {
      this.status200 = status200;
      return this;
    }

    public VersionGetResponse.Builder status200(Consumer<Status200.Builder> status200) {
      Status200.Builder builder = Status200.builder();
      status200.accept(builder);
      return this.status200(builder.build());
    }
  }

  interface Changer {
    VersionGetResponse.Builder configure(VersionGetResponse.Builder builder);
  }
}
