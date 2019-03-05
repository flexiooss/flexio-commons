package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalRestartPostRequest;
import java.lang.Long;
import java.lang.String;

public interface RestartPostRequest {
  static Builder builder() {
    return new RestartPostRequest.Builder();
  }

  static RestartPostRequest.Builder from(RestartPostRequest value) {
    if(value != null) {
      return new RestartPostRequest.Builder()
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

  RestartPostRequest withT(Long value);

  RestartPostRequest withContainerId(String value);

  int hashCode();

  RestartPostRequest changed(RestartPostRequest.Changer changer);

  OptionalRestartPostRequest opt();

  class Builder {
    private Long t;

    private String containerId;

    public RestartPostRequest build() {
      return new RestartPostRequestImpl(this.t, this.containerId);
    }

    public RestartPostRequest.Builder t(Long t) {
      this.t = t;
      return this;
    }

    public RestartPostRequest.Builder containerId(String containerId) {
      this.containerId = containerId;
      return this;
    }
  }

  interface Changer {
    RestartPostRequest.Builder configure(RestartPostRequest.Builder builder);
  }
}
