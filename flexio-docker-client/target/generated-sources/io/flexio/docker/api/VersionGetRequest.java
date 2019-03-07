package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalVersionGetRequest;

public interface VersionGetRequest {
  static Builder builder() {
    return new VersionGetRequest.Builder();
  }

  static VersionGetRequest.Builder from(VersionGetRequest value) {
    if(value != null) {
      return new VersionGetRequest.Builder()
          ;
    }
    else {
      return null;
    }
  }

  int hashCode();

  VersionGetRequest changed(VersionGetRequest.Changer changer);

  OptionalVersionGetRequest opt();

  class Builder {
    public VersionGetRequest build() {
      return new VersionGetRequestImpl();
    }
  }

  interface Changer {
    VersionGetRequest.Builder configure(VersionGetRequest.Builder builder);
  }
}
