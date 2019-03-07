package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalInspectGetRequest;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class InspectGetRequestImpl implements InspectGetRequest {
  private final String containerId;

  InspectGetRequestImpl(String containerId) {
    this.containerId = containerId;
  }

  public String containerId() {
    return this.containerId;
  }

  public InspectGetRequest withContainerId(String value) {
    return InspectGetRequest.from(this).containerId(value).build();
  }

  public InspectGetRequest changed(InspectGetRequest.Changer changer) {
    return changer.configure(InspectGetRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    InspectGetRequestImpl that = (InspectGetRequestImpl) o;
        return Objects.equals(this.containerId, that.containerId);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.containerId});
  }

  @Override
  public String toString() {
    return "InspectGetRequest{" +
        "containerId=" + Objects.toString(this.containerId) +
        '}';
  }

  public OptionalInspectGetRequest opt() {
    return OptionalInspectGetRequest.of(this);
  }
}
