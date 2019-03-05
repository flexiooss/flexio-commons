package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalWaitResult;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class WaitResultImpl implements WaitResult {
  private final Long statusCode;

  WaitResultImpl(Long statusCode) {
    this.statusCode = statusCode;
  }

  public Long statusCode() {
    return this.statusCode;
  }

  public WaitResult withStatusCode(Long value) {
    return WaitResult.from(this).statusCode(value).build();
  }

  public WaitResult changed(WaitResult.Changer changer) {
    return changer.configure(WaitResult.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WaitResultImpl that = (WaitResultImpl) o;
        return Objects.equals(this.statusCode, that.statusCode);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.statusCode});
  }

  @Override
  public String toString() {
    return "WaitResult{" +
        "statusCode=" + Objects.toString(this.statusCode) +
        '}';
  }

  public OptionalWaitResult opt() {
    return OptionalWaitResult.of(this);
  }
}
