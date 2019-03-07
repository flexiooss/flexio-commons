package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalStopPostRequest;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class StopPostRequestImpl implements StopPostRequest {
  private final Long t;

  private final String containerId;

  StopPostRequestImpl(Long t, String containerId) {
    this.t = t;
    this.containerId = containerId;
  }

  public Long t() {
    return this.t;
  }

  public String containerId() {
    return this.containerId;
  }

  public StopPostRequest withT(Long value) {
    return StopPostRequest.from(this).t(value).build();
  }

  public StopPostRequest withContainerId(String value) {
    return StopPostRequest.from(this).containerId(value).build();
  }

  public StopPostRequest changed(StopPostRequest.Changer changer) {
    return changer.configure(StopPostRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StopPostRequestImpl that = (StopPostRequestImpl) o;
        return Objects.equals(this.t, that.t) && 
        Objects.equals(this.containerId, that.containerId);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.t, this.containerId});
  }

  @Override
  public String toString() {
    return "StopPostRequest{" +
        "t=" + Objects.toString(this.t) +
        ", " + "containerId=" + Objects.toString(this.containerId) +
        '}';
  }

  public OptionalStopPostRequest opt() {
    return OptionalStopPostRequest.of(this);
  }
}
