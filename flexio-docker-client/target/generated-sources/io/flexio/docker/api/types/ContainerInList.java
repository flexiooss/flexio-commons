package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalContainerInList;
import java.lang.String;
import java.util.Collection;

public interface ContainerInList {
  static Builder builder() {
    return new ContainerInList.Builder();
  }

  static ContainerInList.Builder from(ContainerInList value) {
    if(value != null) {
      return new ContainerInList.Builder()
          .id(value.id())
          .names(value.names())
          .image(value.image())
          .state(value.state())
          .status(value.status())
          ;
    }
    else {
      return null;
    }
  }

  String id();

  ValueList<String> names();

  String image();

  String state();

  String status();

  ContainerInList withId(String value);

  ContainerInList withNames(ValueList<String> value);

  ContainerInList withImage(String value);

  ContainerInList withState(String value);

  ContainerInList withStatus(String value);

  int hashCode();

  ContainerInList changed(ContainerInList.Changer changer);

  OptionalContainerInList opt();

  class Builder {
    private String id;

    private ValueList<String> names;

    private String image;

    private String state;

    private String status;

    public ContainerInList build() {
      return new ContainerInListImpl(this.id, this.names, this.image, this.state, this.status);
    }

    public ContainerInList.Builder id(String id) {
      this.id = id;
      return this;
    }

    public ContainerInList.Builder names() {
      this.names = null;
      return this;
    }

    public ContainerInList.Builder names(String... names) {
      this.names = names != null ? new ValueList.Builder<String>().with(names).build() : null;
      return this;
    }

    public ContainerInList.Builder names(ValueList<String> names) {
      this.names = names;
      return this;
    }

    public ContainerInList.Builder names(Collection<String> names) {
      this.names = names != null ? new ValueList.Builder<String>().with(names).build() : null;
      return this;
    }

    public ContainerInList.Builder image(String image) {
      this.image = image;
      return this;
    }

    public ContainerInList.Builder state(String state) {
      this.state = state;
      return this;
    }

    public ContainerInList.Builder status(String status) {
      this.status = status;
      return this;
    }
  }

  interface Changer {
    ContainerInList.Builder configure(ContainerInList.Builder builder);
  }
}
