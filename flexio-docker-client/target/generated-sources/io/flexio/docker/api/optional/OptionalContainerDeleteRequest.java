package io.flexio.docker.api.optional;

import io.flexio.docker.api.ContainerDeleteRequest;
import java.lang.Boolean;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalContainerDeleteRequest {
  private final Optional<ContainerDeleteRequest> optional;

  private final Optional<Boolean> v;

  private final Optional<Boolean> force;

  private final Optional<Boolean> link;

  private final Optional<String> containerId;

  private OptionalContainerDeleteRequest(ContainerDeleteRequest value) {
    this.optional = Optional.ofNullable(value);
    this.v = Optional.ofNullable(value != null ? value.v() : null);
    this.force = Optional.ofNullable(value != null ? value.force() : null);
    this.link = Optional.ofNullable(value != null ? value.link() : null);
    this.containerId = Optional.ofNullable(value != null ? value.containerId() : null);
  }

  public static OptionalContainerDeleteRequest of(ContainerDeleteRequest value) {
    return new OptionalContainerDeleteRequest(value);
  }

  public Optional<Boolean> v() {
    return this.v;
  }

  public Optional<Boolean> force() {
    return this.force;
  }

  public Optional<Boolean> link() {
    return this.link;
  }

  public Optional<String> containerId() {
    return this.containerId;
  }

  public ContainerDeleteRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ContainerDeleteRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ContainerDeleteRequest> filter(Predicate<ContainerDeleteRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ContainerDeleteRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ContainerDeleteRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ContainerDeleteRequest orElse(ContainerDeleteRequest value) {
    return this.optional.orElse(value);
  }

  public ContainerDeleteRequest orElseGet(Supplier<ContainerDeleteRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ContainerDeleteRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
