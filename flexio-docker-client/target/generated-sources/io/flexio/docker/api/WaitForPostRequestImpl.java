package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalWaitForPostRequest;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class WaitForPostRequestImpl implements WaitForPostRequest {
  private final String condition;

  private final String containerId;

  WaitForPostRequestImpl(String condition, String containerId) {
    this.condition = condition;
    this.containerId = containerId;
  }

  public String condition() {
    return this.condition;
  }

  public String containerId() {
    return this.containerId;
  }

  public WaitForPostRequest withCondition(String value) {
    return WaitForPostRequest.from(this).condition(value).build();
  }

  public WaitForPostRequest withContainerId(String value) {
    return WaitForPostRequest.from(this).containerId(value).build();
  }

  public WaitForPostRequest changed(WaitForPostRequest.Changer changer) {
    return changer.configure(WaitForPostRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WaitForPostRequestImpl that = (WaitForPostRequestImpl) o;
        return Objects.equals(this.condition, that.condition) && 
        Objects.equals(this.containerId, that.containerId);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.condition, this.containerId});
  }

  @Override
  public String toString() {
    return "WaitForPostRequest{" +
        "condition=" + Objects.toString(this.condition) +
        ", " + "containerId=" + Objects.toString(this.containerId) +
        '}';
  }

  public OptionalWaitForPostRequest opt() {
    return OptionalWaitForPostRequest.of(this);
  }
}
