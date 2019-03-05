package io.flexio.commons.graylog.api;

import io.flexio.commons.graylog.api.optional.OptionalRelativeGetResponse;
import io.flexio.commons.graylog.api.relativegetresponse.Status200;
import io.flexio.commons.graylog.api.relativegetresponse.Status400;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class RelativeGetResponseImpl implements RelativeGetResponse {
  private final Status200 status200;

  private final Status400 status400;

  RelativeGetResponseImpl(Status200 status200, Status400 status400) {
    this.status200 = status200;
    this.status400 = status400;
  }

  public Status200 status200() {
    return this.status200;
  }

  public Status400 status400() {
    return this.status400;
  }

  public RelativeGetResponse withStatus200(Status200 value) {
    return RelativeGetResponse.from(this).status200(value).build();
  }

  public RelativeGetResponse withStatus400(Status400 value) {
    return RelativeGetResponse.from(this).status400(value).build();
  }

  public RelativeGetResponse changed(RelativeGetResponse.Changer changer) {
    return changer.configure(RelativeGetResponse.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RelativeGetResponseImpl that = (RelativeGetResponseImpl) o;
        return Objects.equals(this.status200, that.status200) && 
        Objects.equals(this.status400, that.status400);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.status200, this.status400});
  }

  @Override
  public String toString() {
    return "RelativeGetResponse{" +
        "status200=" + Objects.toString(this.status200) +
        ", " + "status400=" + Objects.toString(this.status400) +
        '}';
  }

  public OptionalRelativeGetResponse opt() {
    return OptionalRelativeGetResponse.of(this);
  }
}
