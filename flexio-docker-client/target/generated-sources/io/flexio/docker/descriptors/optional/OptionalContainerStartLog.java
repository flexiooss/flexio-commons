package io.flexio.docker.descriptors.optional;

import io.flexio.docker.api.types.optional.OptionalContainer;
import io.flexio.docker.descriptors.ContainerStartLog;
import java.lang.Boolean;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalContainerStartLog {
  private final Optional<ContainerStartLog> optional;

  private OptionalContainer container = this.container;

  private final Optional<Boolean> success;

  private final Optional<ContainerStartLog.Action> action;

  private final Optional<String> message;

  private OptionalContainerStartLog(ContainerStartLog value) {
    this.optional = Optional.ofNullable(value);
    this.success = Optional.ofNullable(value != null ? value.success() : null);
    this.action = Optional.ofNullable(value != null ? value.action() : null);
    this.message = Optional.ofNullable(value != null ? value.message() : null);
  }

  public static OptionalContainerStartLog of(ContainerStartLog value) {
    return new OptionalContainerStartLog(value);
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

  public Optional<ContainerStartLog.Action> action() {
    return this.action;
  }

  public Optional<String> message() {
    return this.message;
  }

  public ContainerStartLog get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ContainerStartLog> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ContainerStartLog> filter(Predicate<ContainerStartLog> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ContainerStartLog, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ContainerStartLog, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ContainerStartLog orElse(ContainerStartLog value) {
    return this.optional.orElse(value);
  }

  public ContainerStartLog orElseGet(Supplier<ContainerStartLog> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ContainerStartLog orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
