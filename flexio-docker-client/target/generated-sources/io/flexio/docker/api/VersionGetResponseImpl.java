package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalVersionGetResponse;
import io.flexio.docker.api.versiongetresponse.Status200;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class VersionGetResponseImpl implements VersionGetResponse {
  private final Status200 status200;

  VersionGetResponseImpl(Status200 status200) {
    this.status200 = status200;
  }

  public Status200 status200() {
    return this.status200;
  }

  public VersionGetResponse withStatus200(Status200 value) {
    return VersionGetResponse.from(this).status200(value).build();
  }

  public VersionGetResponse changed(VersionGetResponse.Changer changer) {
    return changer.configure(VersionGetResponse.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    VersionGetResponseImpl that = (VersionGetResponseImpl) o;
        return Objects.equals(this.status200, that.status200);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.status200});
  }

  @Override
  public String toString() {
    return "VersionGetResponse{" +
        "status200=" + Objects.toString(this.status200) +
        '}';
  }

  public OptionalVersionGetResponse opt() {
    return OptionalVersionGetResponse.of(this);
  }
}
