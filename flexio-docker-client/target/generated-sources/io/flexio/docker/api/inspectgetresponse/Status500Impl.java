package io.flexio.docker.api.inspectgetresponse;

import io.flexio.docker.api.inspectgetresponse.optional.OptionalStatus500;
import io.flexio.docker.api.types.Error;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class Status500Impl implements Status500 {
  private final Error payload;

  Status500Impl(Error payload) {
    this.payload = payload;
  }

  public Error payload() {
    return this.payload;
  }

  public Status500 withPayload(Error value) {
    return Status500.from(this).payload(value).build();
  }

  public Status500 changed(Status500.Changer changer) {
    return changer.configure(Status500.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Status500Impl that = (Status500Impl) o;
        return Objects.equals(this.payload, that.payload);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.payload});
  }

  @Override
  public String toString() {
    return "Status500{" +
        "payload=" + Objects.toString(this.payload) +
        '}';
  }

  public OptionalStatus500 opt() {
    return OptionalStatus500.of(this);
  }
}
