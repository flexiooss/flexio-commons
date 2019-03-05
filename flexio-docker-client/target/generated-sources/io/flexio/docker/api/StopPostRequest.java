package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalStopPostRequest;
import java.lang.Long;
import java.lang.String;

public interface StopPostRequest {
  static Builder builder() {
    return new StopPostRequest.Builder();
  }

  static StopPostRequest.Builder from(StopPostRequest value) {
    if(value != null) {
      return new StopPostRequest.Builder()
          .t(value.t())
          .containerId(value.containerId())
          ;
    }
    else {
      return null;
    }
  }

  Long t();

  String containerId();

  StopPostRequest withT(Long value);

  StopPostRequest withContainerId(String value);

  int hashCode();

  StopPostRequest changed(StopPostRequest.Changer changer);

  OptionalStopPostRequest opt();

  class Builder {
    private Long t;

    private String containerId;

    public StopPostRequest build() {
      return new StopPostRequestImpl(this.t, this.containerId);
    }

    public StopPostRequest.Builder t(Long t) {
      this.t = t;
      return this;
    }

    public StopPostRequest.Builder containerId(String containerId) {
      this.containerId = containerId;
      return this;
    }
  }

  interface Changer {
    StopPostRequest.Builder configure(StopPostRequest.Builder builder);
  }
}
