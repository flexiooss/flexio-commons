package io.flexio.docker.descriptors.optional;

import io.flexio.docker.api.types.optional.OptionalContainer;
import io.flexio.docker.descriptors.ContainerDeletionLog;
import java.lang.Boolean;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalContainerDeletionLog {
  private final Optional<ContainerDeletionLog> optional;

  private OptionalContainer container = this.container;

  private final Optional<Boolean> success;

  private final Optional<ContainerDeletionLog.Action> action;

  private final Optional<String> message;

  private OptionalContainerDeletionLog(ContainerDeletionLog value) {
    this.optional = Optional.ofNullable(value);
    this.success = Optional.ofNullable(value != null ? value.success() : null);
    this.action = Optional.ofNullable(value != null ? value.action() : null);
    this.message = Optional.ofNullable(value != null ? value.message() : null);
  }

  public static OptionalContainerDeletionLog of(ContainerDeletionLog value) {
    return new OptionalContainerDeletionLog(value);
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

  public Optional<ContainerDeletionLog.Action> action() {
    return this.action;
  }

  public Optional<String> message() {
    return this.message;
  }

  public ContainerDeletionLog get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ContainerDeletionLog> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ContainerDeletionLog> filter(Predicate<ContainerDeletionLog> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ContainerDeletionLog, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ContainerDeletionLog, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ContainerDeletionLog orElse(ContainerDeletionLog value) {
    return this.optional.orElse(value);
  }

  public ContainerDeletionLog orElseGet(Supplier<ContainerDeletionLog> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ContainerDeletionLog orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
