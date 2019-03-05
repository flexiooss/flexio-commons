package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalKillPostRequest;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class KillPostRequestImpl implements KillPostRequest {
  private final String signal;

  private final String containerId;

  KillPostRequestImpl(String signal, String containerId) {
    this.signal = signal;
    this.containerId = containerId;
  }

  public String signal() {
    return this.signal;
  }

  public String containerId() {
    return this.containerId;
  }

  public KillPostRequest withSignal(String value) {
    return KillPostRequest.from(this).signal(value).build();
  }

  public KillPostRequest withContainerId(String value) {
    return KillPostRequest.from(this).containerId(value).build();
  }

  public KillPostRequest changed(KillPostRequest.Changer changer) {
    return changer.configure(KillPostRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    KillPostRequestImpl that = (KillPostRequestImpl) o;
        return Objects.equals(this.signal, that.signal) && 
        Objects.equals(this.containerId, that.containerId);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.signal, this.containerId});
  }

  @Override
  public String toString() {
    return "KillPostRequest{" +
        "signal=" + Objects.toString(this.signal) +
        ", " + "containerId=" + Objects.toString(this.containerId) +
        '}';
  }

  public OptionalKillPostRequest opt() {
    return OptionalKillPostRequest.of(this);
  }
}
