package io.flexio.docker.api.createcontainerpostresponse;

import io.flexio.docker.api.createcontainerpostresponse.optional.OptionalStatus409;
import io.flexio.docker.api.types.Error;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class Status409Impl implements Status409 {
  private final Error payload;

  Status409Impl(Error payload) {
    this.payload = payload;
  }

  public Error payload() {
    return this.payload;
  }

  public Status409 withPayload(Error value) {
    return Status409.from(this).payload(value).build();
  }

  public Status409 changed(Status409.Changer changer) {
    return changer.configure(Status409.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Status409Impl that = (Status409Impl) o;
        return Objects.equals(this.payload, that.payload);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.payload});
  }

  @Override
  public String toString() {
    return "Status409{" +
        "payload=" + Objects.toString(this.payload) +
        '}';
  }

  public OptionalStatus409 opt() {
    return OptionalStatus409.of(this);
  }
}
