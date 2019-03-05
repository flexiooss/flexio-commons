package io.flexio.docker.api.stoppostresponse;

import io.flexio.docker.api.stoppostresponse.optional.OptionalStatus204;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;

class Status204Impl implements Status204 {
  Status204Impl() {
  }

  public Status204 changed(Status204.Changer changer) {
    return changer.configure(Status204.from(this)).build();
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
    return "Status204{" +
        '}';
  }

  public OptionalStatus204 opt() {
    return OptionalStatus204.of(this);
  }
}
