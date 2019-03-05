package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalVersionGetRequest;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;

class VersionGetRequestImpl implements VersionGetRequest {
  VersionGetRequestImpl() {
  }

  public VersionGetRequest changed(VersionGetRequest.Changer changer) {
    return changer.configure(VersionGetRequest.from(this)).build();
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
    return "VersionGetRequest{" +
        '}';
  }

  public OptionalVersionGetRequest opt() {
    return OptionalVersionGetRequest.of(this);
  }
}
