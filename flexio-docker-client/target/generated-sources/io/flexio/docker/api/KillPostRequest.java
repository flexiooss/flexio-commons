package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalKillPostRequest;
import java.lang.String;

public interface KillPostRequest {
  static Builder builder() {
    return new KillPostRequest.Builder();
  }

  static KillPostRequest.Builder from(KillPostRequest value) {
    if(value != null) {
      return new KillPostRequest.Builder()
          .signal(value.signal())
          .containerId(value.containerId())
          ;
    }
    else {
      return null;
    }
  }

  String signal();

  String containerId();

  KillPostRequest withSignal(String value);

  KillPostRequest withContainerId(String value);

  int hashCode();

  KillPostRequest changed(KillPostRequest.Changer changer);

  OptionalKillPostRequest opt();

  class Builder {
    private String signal;

    private String containerId;

    public KillPostRequest build() {
      return new KillPostRequestImpl(this.signal, this.containerId);
    }

    public KillPostRequest.Builder signal(String signal) {
      this.signal = signal;
      return this;
    }

    public KillPostRequest.Builder containerId(String containerId) {
      this.containerId = containerId;
      return this;
    }
  }

  interface Changer {
    KillPostRequest.Builder configure(KillPostRequest.Builder builder);
  }
}
