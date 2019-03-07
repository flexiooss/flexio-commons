package io.flexio.docker.api;

import io.flexio.docker.api.containerlistgetresponse.Status200;
import io.flexio.docker.api.containerlistgetresponse.Status400;
import io.flexio.docker.api.containerlistgetresponse.Status500;
import io.flexio.docker.api.optional.OptionalContainerListGetResponse;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ContainerListGetResponseImpl implements ContainerListGetResponse {
  private final Status200 status200;

  private final Status400 status400;

  private final Status500 status500;

  ContainerListGetResponseImpl(Status200 status200, Status400 status400, Status500 status500) {
    this.status200 = status200;
    this.status400 = status400;
    this.status500 = status500;
  }

  public Status200 status200() {
    return this.status200;
  }

  public Status400 status400() {
    return this.status400;
  }

  public Status500 status500() {
    return this.status500;
  }

  public ContainerListGetResponse withStatus200(Status200 value) {
    return ContainerListGetResponse.from(this).status200(value).build();
  }

  public ContainerListGetResponse withStatus400(Status400 value) {
    return ContainerListGetResponse.from(this).status400(value).build();
  }

  public ContainerListGetResponse withStatus500(Status500 value) {
    return ContainerListGetResponse.from(this).status500(value).build();
  }

  public ContainerListGetResponse changed(ContainerListGetResponse.Changer changer) {
    return changer.configure(ContainerListGetResponse.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainerListGetResponseImpl that = (ContainerListGetResponseImpl) o;
        return Objects.equals(this.status200, that.status200) && 
        Objects.equals(this.status400, that.status400) && 
        Objects.equals(this.status500, that.status500);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.status200, this.status400, this.status500});
  }

  @Override
  public String toString() {
    return "ContainerListGetResponse{" +
        "status200=" + Objects.toString(this.status200) +
        ", " + "status400=" + Objects.toString(this.status400) +
        ", " + "status500=" + Objects.toString(this.status500) +
        '}';
  }

  public OptionalContainerListGetResponse opt() {
    return OptionalContainerListGetResponse.of(this);
  }
}
