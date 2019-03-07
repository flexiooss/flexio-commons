package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalContainerCreationData;
import java.lang.String;
import java.util.Collection;

public interface ContainerCreationData {
  static Builder builder() {
    return new ContainerCreationData.Builder();
  }

  static ContainerCreationData.Builder from(ContainerCreationData value) {
    if(value != null) {
      return new ContainerCreationData.Builder()
          .image(value.image())
          .cmd(value.cmd())
          ;
    }
    else {
      return null;
    }
  }

  String image();

  ValueList<String> cmd();

  ContainerCreationData withImage(String value);

  ContainerCreationData withCmd(ValueList<String> value);

  int hashCode();

  ContainerCreationData changed(ContainerCreationData.Changer changer);

  OptionalContainerCreationData opt();

  class Builder {
    private String image;

    private ValueList<String> cmd;

    public ContainerCreationData build() {
      return new ContainerCreationDataImpl(this.image, this.cmd);
    }

    public ContainerCreationData.Builder image(String image) {
      this.image = image;
      return this;
    }

    public ContainerCreationData.Builder cmd() {
      this.cmd = null;
      return this;
    }

    public ContainerCreationData.Builder cmd(String... cmd) {
      this.cmd = cmd != null ? new ValueList.Builder<String>().with(cmd).build() : null;
      return this;
    }

    public ContainerCreationData.Builder cmd(ValueList<String> cmd) {
      this.cmd = cmd;
      return this;
    }

    public ContainerCreationData.Builder cmd(Collection<String> cmd) {
      this.cmd = cmd != null ? new ValueList.Builder<String>().with(cmd).build() : null;
      return this;
    }
  }

  interface Changer {
    ContainerCreationData.Builder configure(ContainerCreationData.Builder builder);
  }
}
