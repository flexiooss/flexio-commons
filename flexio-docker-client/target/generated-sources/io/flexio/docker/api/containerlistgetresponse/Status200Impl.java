package io.flexio.docker.api.containerlistgetresponse;

import io.flexio.docker.api.ValueList;
import io.flexio.docker.api.containerlistgetresponse.optional.OptionalStatus200;
import io.flexio.docker.api.types.ContainerInList;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class Status200Impl implements Status200 {
  private final ValueList<ContainerInList> payload;

  Status200Impl(ValueList<ContainerInList> payload) {
    this.payload = payload;
  }

  public ValueList<ContainerInList> payload() {
    return this.payload;
  }

  public Status200 withPayload(ValueList<ContainerInList> value) {
    return Status200.from(this).payload(value).build();
  }

  public Status200 changed(Status200.Changer changer) {
    return changer.configure(Status200.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Status200Impl that = (Status200Impl) o;
        return Objects.equals(this.payload, that.payload);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.payload});
  }

  @Override
  public String toString() {
    return "Status200{" +
        "payload=" + Objects.toString(this.payload) +
        '}';
  }

  public OptionalStatus200 opt() {
    return OptionalStatus200.of(this);
  }
}
