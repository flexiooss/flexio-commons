package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalStartPostResponse;
import io.flexio.docker.api.startpostresponse.Status204;
import io.flexio.docker.api.startpostresponse.Status309;
import io.flexio.docker.api.startpostresponse.Status404;
import io.flexio.docker.api.startpostresponse.Status500;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class StartPostResponseImpl implements StartPostResponse {
  private final Status204 status204;

  private final Status309 status309;

  private final Status404 status404;

  private final Status500 status500;

  StartPostResponseImpl(Status204 status204, Status309 status309, Status404 status404, Status500 status500) {
    this.status204 = status204;
    this.status309 = status309;
    this.status404 = status404;
    this.status500 = status500;
  }

  public Status204 status204() {
    return this.status204;
  }

  public Status309 status309() {
    return this.status309;
  }

  public Status404 status404() {
    return this.status404;
  }

  public Status500 status500() {
    return this.status500;
  }

  public StartPostResponse withStatus204(Status204 value) {
    return StartPostResponse.from(this).status204(value).build();
  }

  public StartPostResponse withStatus309(Status309 value) {
    return StartPostResponse.from(this).status309(value).build();
  }

  public StartPostResponse withStatus404(Status404 value) {
    return StartPostResponse.from(this).status404(value).build();
  }

  public StartPostResponse withStatus500(Status500 value) {
    return StartPostResponse.from(this).status500(value).build();
  }

  public StartPostResponse changed(StartPostResponse.Changer changer) {
    return changer.configure(StartPostResponse.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StartPostResponseImpl that = (StartPostResponseImpl) o;
        return Objects.equals(this.status204, that.status204) && 
        Objects.equals(this.status309, that.status309) && 
        Objects.equals(this.status404, that.status404) && 
        Objects.equals(this.status500, that.status500);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.status204, this.status309, this.status404, this.status500});
  }

  @Override
  public String toString() {
    return "StartPostResponse{" +
        "status204=" + Objects.toString(this.status204) +
        ", " + "status309=" + Objects.toString(this.status309) +
        ", " + "status404=" + Objects.toString(this.status404) +
        ", " + "status500=" + Objects.toString(this.status500) +
        '}';
  }

  public OptionalStartPostResponse opt() {
    return OptionalStartPostResponse.of(this);
  }
}
