package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalStartPostRequest;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class StartPostRequestImpl implements StartPostRequest {
  private final String containerId;

  StartPostRequestImpl(String containerId) {
    this.containerId = containerId;
  }

  public String containerId() {
    return this.containerId;
  }

  public StartPostRequest withContainerId(String value) {
    return StartPostRequest.from(this).containerId(value).build();
  }

  public StartPostRequest changed(StartPostRequest.Changer changer) {
    return changer.configure(StartPostRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StartPostRequestImpl that = (StartPostRequestImpl) o;
        return Objects.equals(this.containerId, that.containerId);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.containerId});
  }

  @Override
  public String toString() {
    return "StartPostRequest{" +
        "containerId=" + Objects.toString(this.containerId) +
        '}';
  }

  public OptionalStartPostRequest opt() {
    return OptionalStartPostRequest.of(this);
  }
}
