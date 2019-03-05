package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalContainerCreationResult;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ContainerCreationResultImpl implements ContainerCreationResult {
  private final String id;

  private final String warning;

  ContainerCreationResultImpl(String id, String warning) {
    this.id = id;
    this.warning = warning;
  }

  public String id() {
    return this.id;
  }

  public String warning() {
    return this.warning;
  }

  public ContainerCreationResult withId(String value) {
    return ContainerCreationResult.from(this).id(value).build();
  }

  public ContainerCreationResult withWarning(String value) {
    return ContainerCreationResult.from(this).warning(value).build();
  }

  public ContainerCreationResult changed(ContainerCreationResult.Changer changer) {
    return changer.configure(ContainerCreationResult.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainerCreationResultImpl that = (ContainerCreationResultImpl) o;
        return Objects.equals(this.id, that.id) && 
        Objects.equals(this.warning, that.warning);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.id, this.warning});
  }

  @Override
  public String toString() {
    return "ContainerCreationResult{" +
        "id=" + Objects.toString(this.id) +
        ", " + "warning=" + Objects.toString(this.warning) +
        '}';
  }

  public OptionalContainerCreationResult opt() {
    return OptionalContainerCreationResult.of(this);
  }
}
