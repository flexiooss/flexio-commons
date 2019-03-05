package io.flexio.docker.api;

import io.flexio.docker.api.createcontainerpostresponse.Status201;
import io.flexio.docker.api.createcontainerpostresponse.Status400;
import io.flexio.docker.api.createcontainerpostresponse.Status404;
import io.flexio.docker.api.createcontainerpostresponse.Status409;
import io.flexio.docker.api.createcontainerpostresponse.Status500;
import io.flexio.docker.api.optional.OptionalCreateContainerPostResponse;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class CreateContainerPostResponseImpl implements CreateContainerPostResponse {
  private final Status201 status201;

  private final Status400 status400;

  private final Status404 status404;

  private final Status409 status409;

  private final Status500 status500;

  CreateContainerPostResponseImpl(Status201 status201, Status400 status400, Status404 status404, Status409 status409, Status500 status500) {
    this.status201 = status201;
    this.status400 = status400;
    this.status404 = status404;
    this.status409 = status409;
    this.status500 = status500;
  }

  public Status201 status201() {
    return this.status201;
  }

  public Status400 status400() {
    return this.status400;
  }

  public Status404 status404() {
    return this.status404;
  }

  public Status409 status409() {
    return this.status409;
  }

  public Status500 status500() {
    return this.status500;
  }

  public CreateContainerPostResponse withStatus201(Status201 value) {
    return CreateContainerPostResponse.from(this).status201(value).build();
  }

  public CreateContainerPostResponse withStatus400(Status400 value) {
    return CreateContainerPostResponse.from(this).status400(value).build();
  }

  public CreateContainerPostResponse withStatus404(Status404 value) {
    return CreateContainerPostResponse.from(this).status404(value).build();
  }

  public CreateContainerPostResponse withStatus409(Status409 value) {
    return CreateContainerPostResponse.from(this).status409(value).build();
  }

  public CreateContainerPostResponse withStatus500(Status500 value) {
    return CreateContainerPostResponse.from(this).status500(value).build();
  }

  public CreateContainerPostResponse changed(CreateContainerPostResponse.Changer changer) {
    return changer.configure(CreateContainerPostResponse.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreateContainerPostResponseImpl that = (CreateContainerPostResponseImpl) o;
        return Objects.equals(this.status201, that.status201) && 
        Objects.equals(this.status400, that.status400) && 
        Objects.equals(this.status404, that.status404) && 
        Objects.equals(this.status409, that.status409) && 
        Objects.equals(this.status500, that.status500);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.status201, this.status400, this.status404, this.status409, this.status500});
  }

  @Override
  public String toString() {
    return "CreateContainerPostResponse{" +
        "status201=" + Objects.toString(this.status201) +
        ", " + "status400=" + Objects.toString(this.status400) +
        ", " + "status404=" + Objects.toString(this.status404) +
        ", " + "status409=" + Objects.toString(this.status409) +
        ", " + "status500=" + Objects.toString(this.status500) +
        '}';
  }

  public OptionalCreateContainerPostResponse opt() {
    return OptionalCreateContainerPostResponse.of(this);
  }
}
