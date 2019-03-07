package io.flexio.docker.api.createimagepostresponse;

import io.flexio.docker.api.createimagepostresponse.optional.OptionalStatus200;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;

class Status200Impl implements Status200 {
  Status200Impl() {
  }

  public Status200 changed(Status200.Changer changer) {
    return changer.configure(Status200.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return true;
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{});
  }

  @Override
  public String toString() {
    return "Status200{" +
        '}';
  }

  public OptionalStatus200 opt() {
    return OptionalStatus200.of(this);
  }
}
