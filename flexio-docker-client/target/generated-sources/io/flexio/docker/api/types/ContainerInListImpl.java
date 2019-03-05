package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalContainerInList;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ContainerInListImpl implements ContainerInList {
  private final String id;

  private final ValueList<String> names;

  private final String image;

  private final String state;

  private final String status;

  ContainerInListImpl(String id, ValueList<String> names, String image, String state, String status) {
    this.id = id;
    this.names = names;
    this.image = image;
    this.state = state;
    this.status = status;
  }

  public String id() {
    return this.id;
  }

  public ValueList<String> names() {
    return this.names;
  }

  public String image() {
    return this.image;
  }

  public String state() {
    return this.state;
  }

  public String status() {
    return this.status;
  }

  public ContainerInList withId(String value) {
    return ContainerInList.from(this).id(value).build();
  }

  public ContainerInList withNames(ValueList<String> value) {
    return ContainerInList.from(this).names(value).build();
  }

  public ContainerInList withImage(String value) {
    return ContainerInList.from(this).image(value).build();
  }

  public ContainerInList withState(String value) {
    return ContainerInList.from(this).state(value).build();
  }

  public ContainerInList withStatus(String value) {
    return ContainerInList.from(this).status(value).build();
  }

  public ContainerInList changed(ContainerInList.Changer changer) {
    return changer.configure(ContainerInList.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainerInListImpl that = (ContainerInListImpl) o;
        return Objects.equals(this.id, that.id) && 
        Objects.equals(this.names, that.names) && 
        Objects.equals(this.image, that.image) && 
        Objects.equals(this.state, that.state) && 
        Objects.equals(this.status, that.status);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.id, this.names, this.image, this.state, this.status});
  }

  @Override
  public String toString() {
    return "ContainerInList{" +
        "id=" + Objects.toString(this.id) +
        ", " + "names=" + Objects.toString(this.names) +
        ", " + "image=" + Objects.toString(this.image) +
        ", " + "state=" + Objects.toString(this.state) +
        ", " + "status=" + Objects.toString(this.status) +
        '}';
  }

  public OptionalContainerInList opt() {
    return OptionalContainerInList.of(this);
  }
}
