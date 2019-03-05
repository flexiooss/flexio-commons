package io.flexio.docker.api.createcontainerpostresponse;

import io.flexio.docker.api.createcontainerpostresponse.optional.OptionalStatus404;
import io.flexio.docker.api.types.Error;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class Status404Impl implements Status404 {
  private final Error payload;

  Status404Impl(Error payload) {
    this.payload = payload;
  }

  public Error payload() {
    return this.payload;
  }

  public Status404 withPayload(Error value) {
    return Status404.from(this).payload(value).build();
  }

  public Status404 changed(Status404.Changer changer) {
    return changer.configure(Status404.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Status404Impl that = (Status404Impl) o;
        return Objects.equals(this.payload, that.payload);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.payload});
  }

  @Override
  public String toString() {
    return "Status404{" +
        "payload=" + Objects.toString(this.payload) +
        '}';
  }

  public OptionalStatus404 opt() {
    return OptionalStatus404.of(this);
  }
}
