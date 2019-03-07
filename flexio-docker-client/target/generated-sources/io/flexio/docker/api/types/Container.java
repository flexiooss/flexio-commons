package io.flexio.docker.api.types;

import io.flexio.docker.api.types.container.NetworkSettings;
import io.flexio.docker.api.types.container.State;
import io.flexio.docker.api.types.optional.OptionalContainer;
import java.lang.String;
import java.util.Collection;
import java.util.function.Consumer;

public interface Container {
  static Builder builder() {
    return new Container.Builder();
  }

  static Container.Builder from(Container value) {
    if(value != null) {
      return new Container.Builder()
          .id(value.id())
          .names(value.names())
          .image(value.image())
          .state(value.state())
          .networkSettings(value.networkSettings())
          ;
    }
    else {
      return null;
    }
  }

  String id();

  ValueList<String> names();

  String image();

  State state();

  NetworkSettings networkSettings();

  Container withId(String value);

  Container withNames(ValueList<String> value);

  Container withImage(String value);

  Container withState(State value);

  Container withNetworkSettings(NetworkSettings value);

  int hashCode();

  Container changed(Container.Changer changer);

  OptionalContainer opt();

  class Builder {
    private String id;

    private ValueList<String> names;

    private String image;

    private State state;

    private NetworkSettings networkSettings;

    public Container build() {
      return new ContainerImpl(this.id, this.names, this.image, this.state, this.networkSettings);
    }

    public Container.Builder id(String id) {
      this.id = id;
      return this;
    }

    public Container.Builder names() {
      this.names = null;
      return this;
    }

    public Container.Builder names(String... names) {
      this.names = names != null ? new ValueList.Builder<String>().with(names).build() : null;
      return this;
    }

    public Container.Builder names(ValueList<String> names) {
      this.names = names;
      return this;
    }

    public Container.Builder names(Collection<String> names) {
      this.names = names != null ? new ValueList.Builder<String>().with(names).build() : null;
      return this;
    }

    public Container.Builder image(String image) {
      this.image = image;
      return this;
    }

    public Container.Builder state(State state) {
      this.state = state;
      return this;
    }

    public Container.Builder state(Consumer<State.Builder> state) {
      State.Builder builder = State.builder();
      state.accept(builder);
      return this.state(builder.build());
    }

    public Container.Builder networkSettings(NetworkSettings networkSettings) {
      this.networkSettings = networkSettings;
      return this;
    }

    public Container.Builder networkSettings(Consumer<NetworkSettings.Builder> networkSettings) {
      NetworkSettings.Builder builder = NetworkSettings.builder();
      networkSettings.accept(builder);
      return this.networkSettings(builder.build());
    }
  }

  interface Changer {
    Container.Builder configure(Container.Builder builder);
  }
}
