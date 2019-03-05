package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalRestartPostRequest;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class RestartPostRequestImpl implements RestartPostRequest {
  private final Long t;

  private final String containerId;

  RestartPostRequestImpl(Long t, String containerId) {
    this.t = t;
    this.containerId = containerId;
  }

  public Long t() {
    return this.t;
  }

  public String containerId() {
    return this.containerId;
  }

  public RestartPostRequest withT(Long value) {
    return RestartPostRequest.from(this).t(value).build();
  }

  public RestartPostRequest withContainerId(String value) {
    return RestartPostRequest.from(this).containerId(value).build();
  }

  public RestartPostRequest changed(RestartPostRequest.Changer changer) {
    return changer.configure(RestartPostRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RestartPostRequestImpl that = (RestartPostRequestImpl) o;
        return Objects.equals(this.t, that.t) && 
        Objects.equals(this.containerId, that.containerId);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.t, this.containerId});
  }

  @Override
  public String toString() {
    return "RestartPostRequest{" +
        "t=" + Objects.toString(this.t) +
        ", " + "containerId=" + Objects.toString(this.containerId) +
        '}';
  }

  public OptionalRestartPostRequest opt() {
    return OptionalRestartPostRequest.of(this);
  }
}
