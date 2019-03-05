package io.flexio.docker.api.startpostresponse;

import io.flexio.docker.api.startpostresponse.optional.OptionalStatus309;
import io.flexio.docker.api.types.Error;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class Status309Impl implements Status309 {
  private final Error payload;

  Status309Impl(Error payload) {
    this.payload = payload;
  }

  public Error payload() {
    return this.payload;
  }

  public Status309 withPayload(Error value) {
    return Status309.from(this).payload(value).build();
  }

  public Status309 changed(Status309.Changer changer) {
    return changer.configure(Status309.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Status309Impl that = (Status309Impl) o;
        return Objects.equals(this.payload, that.payload);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.payload});
  }

  @Override
  public String toString() {
    return "Status309{" +
        "payload=" + Objects.toString(this.payload) +
        '}';
  }

  public OptionalStatus309 opt() {
    return OptionalStatus309.of(this);
  }
}
