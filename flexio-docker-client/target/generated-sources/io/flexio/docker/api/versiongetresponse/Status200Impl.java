package io.flexio.docker.api.versiongetresponse;

import io.flexio.docker.api.types.Version;
import io.flexio.docker.api.versiongetresponse.optional.OptionalStatus200;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class Status200Impl implements Status200 {
  private final Version payload;

  Status200Impl(Version payload) {
    this.payload = payload;
  }

  public Version payload() {
    return this.payload;
  }

  public Status200 withPayload(Version value) {
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
