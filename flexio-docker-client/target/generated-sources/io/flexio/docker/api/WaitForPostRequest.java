package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalWaitForPostRequest;
import java.lang.String;

public interface WaitForPostRequest {
  static Builder builder() {
    return new WaitForPostRequest.Builder();
  }

  static WaitForPostRequest.Builder from(WaitForPostRequest value) {
    if(value != null) {
      return new WaitForPostRequest.Builder()
          .condition(value.condition())
          .containerId(value.containerId())
          ;
    }
    else {
      return null;
    }
  }

  String condition();

  String containerId();

  WaitForPostRequest withCondition(String value);

  WaitForPostRequest withContainerId(String value);

  int hashCode();

  WaitForPostRequest changed(WaitForPostRequest.Changer changer);

  OptionalWaitForPostRequest opt();

  class Builder {
    private String condition;

    private String containerId;

    public WaitForPostRequest build() {
      return new WaitForPostRequestImpl(this.condition, this.containerId);
    }

    public WaitForPostRequest.Builder condition(String condition) {
      this.condition = condition;
      return this;
    }

    public WaitForPostRequest.Builder containerId(String containerId) {
      this.containerId = containerId;
      return this;
    }
  }

  interface Changer {
    WaitForPostRequest.Builder configure(WaitForPostRequest.Builder builder);
  }
}
