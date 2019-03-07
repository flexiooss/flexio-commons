package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalStartPostRequest;
import java.lang.String;

public interface StartPostRequest {
  static Builder builder() {
    return new StartPostRequest.Builder();
  }

  static StartPostRequest.Builder from(StartPostRequest value) {
    if(value != null) {
      return new StartPostRequest.Builder()
          .containerId(value.containerId())
          ;
    }
    else {
      return null;
    }
  }

  String containerId();

  StartPostRequest withContainerId(String value);

  int hashCode();

  StartPostRequest changed(StartPostRequest.Changer changer);

  OptionalStartPostRequest opt();

  class Builder {
    private String containerId;

    public StartPostRequest build() {
      return new StartPostRequestImpl(this.containerId);
    }

    public StartPostRequest.Builder containerId(String containerId) {
      this.containerId = containerId;
      return this;
    }
  }

  interface Changer {
    StartPostRequest.Builder configure(StartPostRequest.Builder builder);
  }
}
