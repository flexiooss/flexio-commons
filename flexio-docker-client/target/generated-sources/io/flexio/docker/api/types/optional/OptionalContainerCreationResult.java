package io.flexio.docker.api.types.optional;

import io.flexio.docker.api.types.ContainerCreationResult;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalContainerCreationResult {
  private final Optional<ContainerCreationResult> optional;

  private final Optional<String> id;

  private final Optional<String> warning;

  private OptionalContainerCreationResult(ContainerCreationResult value) {
    this.optional = Optional.ofNullable(value);
    this.id = Optional.ofNullable(value != null ? value.id() : null);
    this.warning = Optional.ofNullable(value != null ? value.warning() : null);
  }

  public static OptionalContainerCreationResult of(ContainerCreationResult value) {
    return new OptionalContainerCreationResult(value);
  }

  public Optional<String> id() {
    return this.id;
  }

  public Optional<String> warning() {
    return this.warning;
  }

  public ContainerCreationResult get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ContainerCreationResult> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ContainerCreationResult> filter(Predicate<ContainerCreationResult> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ContainerCreationResult, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ContainerCreationResult, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ContainerCreationResult orElse(ContainerCreationResult value) {
    return this.optional.orElse(value);
  }

  public ContainerCreationResult orElseGet(Supplier<ContainerCreationResult> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ContainerCreationResult orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
