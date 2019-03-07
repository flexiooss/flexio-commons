package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalContainerCreationData;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ContainerCreationDataImpl implements ContainerCreationData {
  private final String image;

  private final ValueList<String> cmd;

  ContainerCreationDataImpl(String image, ValueList<String> cmd) {
    this.image = image;
    this.cmd = cmd;
  }

  public String image() {
    return this.image;
  }

  public ValueList<String> cmd() {
    return this.cmd;
  }

  public ContainerCreationData withImage(String value) {
    return ContainerCreationData.from(this).image(value).build();
  }

  public ContainerCreationData withCmd(ValueList<String> value) {
    return ContainerCreationData.from(this).cmd(value).build();
  }

  public ContainerCreationData changed(ContainerCreationData.Changer changer) {
    return changer.configure(ContainerCreationData.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainerCreationDataImpl that = (ContainerCreationDataImpl) o;
        return Objects.equals(this.image, that.image) && 
        Objects.equals(this.cmd, that.cmd);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.image, this.cmd});
  }

  @Override
  public String toString() {
    return "ContainerCreationData{" +
        "image=" + Objects.toString(this.image) +
        ", " + "cmd=" + Objects.toString(this.cmd) +
        '}';
  }

  public OptionalContainerCreationData opt() {
    return OptionalContainerCreationData.of(this);
  }
}
