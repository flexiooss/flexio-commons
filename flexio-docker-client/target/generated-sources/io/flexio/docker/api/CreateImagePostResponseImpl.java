package io.flexio.docker.api;

import io.flexio.docker.api.createimagepostresponse.Status200;
import io.flexio.docker.api.createimagepostresponse.Status404;
import io.flexio.docker.api.createimagepostresponse.Status500;
import io.flexio.docker.api.optional.OptionalCreateImagePostResponse;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class CreateImagePostResponseImpl implements CreateImagePostResponse {
  private final Status200 status200;

  private final Status404 status404;

  private final Status500 status500;

  CreateImagePostResponseImpl(Status200 status200, Status404 status404, Status500 status500) {
    this.status200 = status200;
    this.status404 = status404;
    this.status500 = status500;
  }

  public Status200 status200() {
    return this.status200;
  }

  public Status404 status404() {
    return this.status404;
  }

  public Status500 status500() {
    return this.status500;
  }

  public CreateImagePostResponse withStatus200(Status200 value) {
    return CreateImagePostResponse.from(this).status200(value).build();
  }

  public CreateImagePostResponse withStatus404(Status404 value) {
    return CreateImagePostResponse.from(this).status404(value).build();
  }

  public CreateImagePostResponse withStatus500(Status500 value) {
    return CreateImagePostResponse.from(this).status500(value).build();
  }

  public CreateImagePostResponse changed(CreateImagePostResponse.Changer changer) {
    return changer.configure(CreateImagePostResponse.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreateImagePostResponseImpl that = (CreateImagePostResponseImpl) o;
        return Objects.equals(this.status200, that.status200) && 
        Objects.equals(this.status404, that.status404) && 
        Objects.equals(this.status500, that.status500);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.status200, this.status404, this.status500});
  }

  @Override
  public String toString() {
    return "CreateImagePostResponse{" +
        "status200=" + Objects.toString(this.status200) +
        ", " + "status404=" + Objects.toString(this.status404) +
        ", " + "status500=" + Objects.toString(this.status500) +
        '}';
  }

  public OptionalCreateImagePostResponse opt() {
    return OptionalCreateImagePostResponse.of(this);
  }
}
