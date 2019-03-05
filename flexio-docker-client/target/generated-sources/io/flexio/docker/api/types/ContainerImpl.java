package io.flexio.docker.api.types;

import io.flexio.docker.api.types.container.NetworkSettings;
import io.flexio.docker.api.types.container.State;
import io.flexio.docker.api.types.optional.OptionalContainer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ContainerImpl implements Container {
  private final String id;

  private final ValueList<String> names;

  private final String image;

  private final State state;

  private final NetworkSettings networkSettings;

  ContainerImpl(String id, ValueList<String> names, String image, State state, NetworkSettings networkSettings) {
    this.id = id;
    this.names = names;
    this.image = image;
    this.state = state;
    this.networkSettings = networkSettings;
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

  public State state() {
    return this.state;
  }

  public NetworkSettings networkSettings() {
    return this.networkSettings;
  }

  public Container withId(String value) {
    return Container.from(this).id(value).build();
  }

  public Container withNames(ValueList<String> value) {
    return Container.from(this).names(value).build();
  }

  public Container withImage(String value) {
    return Container.from(this).image(value).build();
  }

  public Container withState(State value) {
    return Container.from(this).state(value).build();
  }

  public Container withNetworkSettings(NetworkSettings value) {
    return Container.from(this).networkSettings(value).build();
  }

  public Container changed(Container.Changer changer) {
    return changer.configure(Container.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainerImpl that = (ContainerImpl) o;
        return Objects.equals(this.id, that.id) && 
        Objects.equals(this.names, that.names) && 
        Objects.equals(this.image, that.image) && 
        Objects.equals(this.state, that.state) && 
        Objects.equals(this.networkSettings, that.networkSettings);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.id, this.names, this.image, this.state, this.networkSettings});
  }

  @Override
  public String toString() {
    return "Container{" +
        "id=" + Objects.toString(this.id) +
        ", " + "names=" + Objects.toString(this.names) +
        ", " + "image=" + Objects.toString(this.image) +
        ", " + "state=" + Objects.toString(this.state) +
        ", " + "networkSettings=" + Objects.toString(this.networkSettings) +
        '}';
  }

  public OptionalContainer opt() {
    return OptionalContainer.of(this);
  }
}
