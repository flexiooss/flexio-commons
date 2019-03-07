package io.flexio.docker.api;

import io.flexio.docker.api.containerdeleteresponse.Status204;
import io.flexio.docker.api.containerdeleteresponse.Status400;
import io.flexio.docker.api.optional.OptionalContainerDeleteResponse;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ContainerDeleteResponseImpl implements ContainerDeleteResponse {
  private final Status204 status204;

  private final Status400 status400;

  ContainerDeleteResponseImpl(Status204 status204, Status400 status400) {
    this.status204 = status204;
    this.status400 = status400;
  }

  public Status204 status204() {
    return this.status204;
  }

  public Status400 status400() {
    return this.status400;
  }

  public ContainerDeleteResponse withStatus204(Status204 value) {
    return ContainerDeleteResponse.from(this).status204(value).build();
  }

  public ContainerDeleteResponse withStatus400(Status400 value) {
    return ContainerDeleteResponse.from(this).status400(value).build();
  }

  public ContainerDeleteResponse changed(ContainerDeleteResponse.Changer changer) {
    return changer.configure(ContainerDeleteResponse.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainerDeleteResponseImpl that = (ContainerDeleteResponseImpl) o;
        return Objects.equals(this.status204, that.status204) && 
        Objects.equals(this.status400, that.status400);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.status204, this.status400});
  }

  @Override
  public String toString() {
    return "ContainerDeleteResponse{" +
        "status204=" + Objects.toString(this.status204) +
        ", " + "status400=" + Objects.toString(this.status400) +
        '}';
  }

  public OptionalContainerDeleteResponse opt() {
    return OptionalContainerDeleteResponse.of(this);
  }
}
