package io.flexio.docker.api.createimagepostresponse;

import io.flexio.docker.api.createimagepostresponse.optional.OptionalStatus200;

public interface Status200 {
  static Builder builder() {
    return new Status200.Builder();
  }

  static Status200.Builder from(Status200 value) {
    if(value != null) {
      return new Status200.Builder()
          ;
    }
    else {
      return null;
    }
  }

  int hashCode();

  Status200 changed(Status200.Changer changer);

  OptionalStatus200 opt();

  class Builder {
    public Status200 build() {
      return new Status200Impl();
    }
  }

  interface Changer {
    Status200.Builder configure(Status200.Builder builder);
  }
}
