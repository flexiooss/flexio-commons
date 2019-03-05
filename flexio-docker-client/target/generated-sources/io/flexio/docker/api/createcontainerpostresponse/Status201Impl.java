package io.flexio.docker.api.createcontainerpostresponse;

import io.flexio.docker.api.createcontainerpostresponse.optional.OptionalStatus201;
import io.flexio.docker.api.types.ContainerCreationResult;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class Status201Impl implements Status201 {
  private final ContainerCreationResult payload;

  Status201Impl(ContainerCreationResult payload) {
    this.payload = payload;
  }

  public ContainerCreationResult payload() {
    return this.payload;
  }

  public Status201 withPayload(ContainerCreationResult value) {
    return Status201.from(this).payload(value).build();
  }

  public Status201 changed(Status201.Changer changer) {
    return changer.configure(Status201.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Status201Impl that = (Status201Impl) o;
        return Objects.equals(this.payload, that.payload);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.payload});
  }

  @Override
  public String toString() {
    return "Status201{" +
        "payload=" + Objects.toString(this.payload) +
        '}';
  }

  public OptionalStatus201 opt() {
    return OptionalStatus201.of(this);
  }
}
