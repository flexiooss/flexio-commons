package io.flexio.docker.descriptors.optional;

import io.flexio.docker.api.types.optional.OptionalContainer;
import io.flexio.docker.descriptors.ContainerCreationLog;
import java.lang.Boolean;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalContainerCreationLog {
  private final Optional<ContainerCreationLog> optional;

  private OptionalContainer container = this.container;

  private final Optional<Boolean> success;

  private final Optional<ContainerCreationLog.Action> action;

  private final Optional<String> message;

  private OptionalContainerCreationLog(ContainerCreationLog value) {
    this.optional = Optional.ofNullable(value);
    this.success = Optional.ofNullable(value != null ? value.success() : null);
    this.action = Optional.ofNullable(value != null ? value.action() : null);
    this.message = Optional.ofNullable(value != null ? value.message() : null);
  }

  public static OptionalContainerCreationLog of(ContainerCreationLog value) {
    return new OptionalContainerCreationLog(value);
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

  public Optional<ContainerCreationLog.Action> action() {
    return this.action;
  }

  public Optional<String> message() {
    return this.message;
  }

  public ContainerCreationLog get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ContainerCreationLog> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ContainerCreationLog> filter(Predicate<ContainerCreationLog> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ContainerCreationLog, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ContainerCreationLog, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ContainerCreationLog orElse(ContainerCreationLog value) {
    return this.optional.orElse(value);
  }

  public ContainerCreationLog orElseGet(Supplier<ContainerCreationLog> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ContainerCreationLog orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
