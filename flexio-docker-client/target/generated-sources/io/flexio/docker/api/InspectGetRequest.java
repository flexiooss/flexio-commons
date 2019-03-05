package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalInspectGetRequest;
import java.lang.String;

public interface InspectGetRequest {
  static Builder builder() {
    return new InspectGetRequest.Builder();
  }

  static InspectGetRequest.Builder from(InspectGetRequest value) {
    if(value != null) {
      return new InspectGetRequest.Builder()
          .containerId(value.containerId())
          ;
    }
    else {
      return null;
    }
  }

  String containerId();

  InspectGetRequest withContainerId(String value);

  int hashCode();

  InspectGetRequest changed(InspectGetRequest.Changer changer);

  OptionalInspectGetRequest opt();

  class Builder {
    private String containerId;

    public InspectGetRequest build() {
      return new InspectGetRequestImpl(this.containerId);
    }

    public InspectGetRequest.Builder containerId(String containerId) {
      this.containerId = containerId;
      return this;
    }
  }

  interface Changer {
    InspectGetRequest.Builder configure(InspectGetRequest.Builder builder);
  }
}
