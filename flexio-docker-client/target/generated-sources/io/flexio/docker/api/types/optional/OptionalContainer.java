package io.flexio.docker.api.types.optional;

import io.flexio.docker.api.types.Container;
import io.flexio.docker.api.types.container.optional.OptionalNetworkSettings;
import io.flexio.docker.api.types.container.optional.OptionalState;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalContainer {
  private final Optional<Container> optional;

  private final Optional<String> id;

  private final OptionalValueList<String, Optional<String>> names;

  private final Optional<String> image;

  private OptionalState state = this.state;

  private OptionalNetworkSettings networkSettings = this.networkSettings;

  private OptionalContainer(Container value) {
    this.optional = Optional.ofNullable(value);
    this.id = Optional.ofNullable(value != null ? value.id() : null);
    this.names = new OptionalValueList<>(value != null ? value.names() : null, e -> Optional.ofNullable(e));
    this.image = Optional.ofNullable(value != null ? value.image() : null);
  }

  public static OptionalContainer of(Container value) {
    return new OptionalContainer(value);
  }

  public Optional<String> id() {
    return this.id;
  }

  public OptionalValueList<String, Optional<String>> names() {
    return this.names;
  }

  public Optional<String> image() {
    return this.image;
  }

  public synchronized OptionalState state() {
    if(this.state == null) {
      this.state = OptionalState.of(this.optional.isPresent() ? this.optional.get().state() : null);
    }
    return this.state;
  }

  public synchronized OptionalNetworkSettings networkSettings() {
    if(this.networkSettings == null) {
      this.networkSettings = OptionalNetworkSettings.of(this.optional.isPresent() ? this.optional.get().networkSettings() : null);
    }
    return this.networkSettings;
  }

  public Container get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<Container> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<Container> filter(Predicate<Container> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<Container, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<Container, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public Container orElse(Container value) {
    return this.optional.orElse(value);
  }

  public Container orElseGet(Supplier<Container> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> Container orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
