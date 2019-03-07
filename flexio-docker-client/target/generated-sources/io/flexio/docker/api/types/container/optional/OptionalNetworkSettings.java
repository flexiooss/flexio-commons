package io.flexio.docker.api.types.container.optional;

import io.flexio.docker.api.types.container.NetworkSettings;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalNetworkSettings {
  private final Optional<NetworkSettings> optional;

  private final Optional<String> iPAddress;

  private OptionalNetworkSettings(NetworkSettings value) {
    this.optional = Optional.ofNullable(value);
    this.iPAddress = Optional.ofNullable(value != null ? value.iPAddress() : null);
  }

  public static OptionalNetworkSettings of(NetworkSettings value) {
    return new OptionalNetworkSettings(value);
  }

  public Optional<String> iPAddress() {
    return this.iPAddress;
  }

  public NetworkSettings get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<NetworkSettings> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<NetworkSettings> filter(Predicate<NetworkSettings> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<NetworkSettings, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<NetworkSettings, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public NetworkSettings orElse(NetworkSettings value) {
    return this.optional.orElse(value);
  }

  public NetworkSettings orElseGet(Supplier<NetworkSettings> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> NetworkSettings orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
