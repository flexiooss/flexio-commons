package io.flexio.docker.descriptors.optional;

import io.flexio.docker.api.types.optional.OptionalContainer;
import io.flexio.docker.descriptors.ContainerStopLog;
import java.lang.Boolean;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalContainerStopLog {
  private final Optional<ContainerStopLog> optional;

  private OptionalContainer container = this.container;

  private final Optional<Boolean> success;

  private final Optional<ContainerStopLog.Action> action;

  private final Optional<String> message;

  private OptionalContainerStopLog(ContainerStopLog value) {
    this.optional = Optional.ofNullable(value);
    this.success = Optional.ofNullable(value != null ? value.success() : null);
    this.action = Optional.ofNullable(value != null ? value.action() : null);
    this.message = Optional.ofNullable(value != null ? value.message() : null);
  }

  public static OptionalContainerStopLog of(ContainerStopLog value) {
    return new OptionalContainerStopLog(value);
  }

  public synchronized OptionalContainer container() {
    if(this.container == null) {
      this.container = OptionalContainer.of(this.optional.isPresent() ? this.optional.get().container() : null);
    }
    return this.container;
  }

  public Optional<Boolean> success() {
    return this.success;
  }

  public Optional<ContainerStopLog.Action> action() {
    return this.action;
  }

  public Optional<String> message() {
    return this.message;
  }

  public ContainerStopLog get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ContainerStopLog> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ContainerStopLog> filter(Predicate<ContainerStopLog> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ContainerStopLog, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ContainerStopLog, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ContainerStopLog orElse(ContainerStopLog value) {
    return this.optional.orElse(value);
  }

  public ContainerStopLog orElseGet(Supplier<ContainerStopLog> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ContainerStopLog orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
