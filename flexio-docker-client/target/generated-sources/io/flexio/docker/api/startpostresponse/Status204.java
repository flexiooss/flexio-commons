package io.flexio.docker.api.startpostresponse;

import io.flexio.docker.api.startpostresponse.optional.OptionalStatus204;

public interface Status204 {
  static Builder builder() {
    return new Status204.Builder();
  }

  static Status204.Builder from(Status204 value) {
    if(value != null) {
      return new Status204.Builder()
          ;
    }
    else {
      return null;
    }
  }

  int hashCode();

  Status204 changed(Status204.Changer changer);

  OptionalStatus204 opt();

  class Builder {
    public Status204 build() {
      return new Status204Impl();
    }
  }

  interface Changer {
    Status204.Builder configure(Status204.Builder builder);
  }
}
