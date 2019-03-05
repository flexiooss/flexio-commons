package io.flexio.commons.graylog.api;

import io.flexio.commons.graylog.api.absolutegetresponse.Status200;
import io.flexio.commons.graylog.api.absolutegetresponse.Status400;
import io.flexio.commons.graylog.api.optional.OptionalAbsoluteGetResponse;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class AbsoluteGetResponseImpl implements AbsoluteGetResponse {
  private final Status200 status200;

  private final Status400 status400;

  AbsoluteGetResponseImpl(Status200 status200, Status400 status400) {
    this.status200 = status200;
    this.status400 = status400;
  }

  public Status200 status200() {
    return this.status200;
  }

  public Status400 status400() {
    return this.status400;
  }

  public AbsoluteGetResponse withStatus200(Status200 value) {
    return AbsoluteGetResponse.from(this).status200(value).build();
  }

  public AbsoluteGetResponse withStatus400(Status400 value) {
    return AbsoluteGetResponse.from(this).status400(value).build();
  }

  public AbsoluteGetResponse changed(AbsoluteGetResponse.Changer changer) {
    return changer.configure(AbsoluteGetResponse.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AbsoluteGetResponseImpl that = (AbsoluteGetResponseImpl) o;
        return Objects.equals(this.status200, that.status200) && 
        Objects.equals(this.status400, that.status400);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.status200, this.status400});
  }

  @Override
  public String toString() {
    return "AbsoluteGetResponse{" +
        "status200=" + Objects.toString(this.status200) +
        ", " + "status400=" + Objects.toString(this.status400) +
        '}';
  }

  public OptionalAbsoluteGetResponse opt() {
    return OptionalAbsoluteGetResponse.of(this);
  }
}
