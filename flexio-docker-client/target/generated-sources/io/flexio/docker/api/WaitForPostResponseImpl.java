package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalWaitForPostResponse;
import io.flexio.docker.api.waitforpostresponse.Status200;
import io.flexio.docker.api.waitforpostresponse.Status404;
import io.flexio.docker.api.waitforpostresponse.Status500;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class WaitForPostResponseImpl implements WaitForPostResponse {
  private final Status200 status200;

  private final Status404 status404;

  private final Status500 status500;

  WaitForPostResponseImpl(Status200 status200, Status404 status404, Status500 status500) {
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

  public WaitForPostResponse withStatus200(Status200 value) {
    return WaitForPostResponse.from(this).status200(value).build();
  }

  public WaitForPostResponse withStatus404(Status404 value) {
    return WaitForPostResponse.from(this).status404(value).build();
  }

  public WaitForPostResponse withStatus500(Status500 value) {
    return WaitForPostResponse.from(this).status500(value).build();
  }

  public WaitForPostResponse changed(WaitForPostResponse.Changer changer) {
    return changer.configure(WaitForPostResponse.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WaitForPostResponseImpl that = (WaitForPostResponseImpl) o;
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
    return "WaitForPostResponse{" +
        "status200=" + Objects.toString(this.status200) +
        ", " + "status404=" + Objects.toString(this.status404) +
        ", " + "status500=" + Objects.toString(this.status500) +
        '}';
  }

  public OptionalWaitForPostResponse opt() {
    return OptionalWaitForPostResponse.of(this);
  }
}
