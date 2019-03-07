package io.flexio.commons.otsdb.api.storedatapointspostresponse;

import io.flexio.commons.otsdb.api.storedatapointspostresponse.optional.OptionalStatus400;
import io.flexio.commons.otsdb.api.types.StorageResponse;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class Status400Impl implements Status400 {
  private final StorageResponse payload;

  Status400Impl(StorageResponse payload) {
    this.payload = payload;
  }

  public StorageResponse payload() {
    return this.payload;
  }

  public Status400 withPayload(StorageResponse value) {
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
