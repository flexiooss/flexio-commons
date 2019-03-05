package io.flexio.docker.api.containerlistgetresponse;

import io.flexio.docker.api.containerlistgetresponse.optional.OptionalStatus400;
import io.flexio.docker.api.types.Error;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class Status400Impl implements Status400 {
  private final Error payload;

  Status400Impl(Error payload) {
    this.payload = payload;
  }

  public Error payload() {
    return this.payload;
  }

  public Status400 withPayload(Error value) {
    return Status400.from(this).payload(value).build();
  }

  public Status400 changed(Status400.Changer changer) {
    return changer.configure(Status400.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Status400Impl that = (Status400Impl) o;
        return Objects.equals(this.payload, that.payload);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.payload});
  }

  @Override
  public String toString() {
    return "Status400{" +
        "payload=" + Objects.toString(this.payload) +
        '}';
  }

  public OptionalStatus400 opt() {
    return OptionalStatus400.of(this);
  }
}
