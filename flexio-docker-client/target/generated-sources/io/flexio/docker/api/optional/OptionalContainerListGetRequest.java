package io.flexio.docker.api.optional;

import io.flexio.docker.api.ContainerListGetRequest;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalContainerListGetRequest {
  private final Optional<ContainerListGetRequest> optional;

  private final Optional<Boolean> all;

  private final Optional<Long> limit;

  private final Optional<Long> size;

  private final Optional<String> filters;

  private OptionalContainerListGetRequest(ContainerListGetRequest value) {
    this.optional = Optional.ofNullable(value);
    this.all = Optional.ofNullable(value != null ? value.all() : null);
    this.limit = Optional.ofNullable(value != null ? value.limit() : null);
    this.size = Optional.ofNullable(value != null ? value.size() : null);
    this.filters = Optional.ofNullable(value != null ? value.filters() : null);
  }

  public static OptionalContainerListGetRequest of(ContainerListGetRequest value) {
    return new OptionalContainerListGetRequest(value);
  }

  public Optional<Boolean> all() {
    return this.all;
  }

  public Optional<Long> limit() {
    return this.limit;
  }

  public Optional<Long> size() {
    return this.size;
  }

  public Optional<String> filters() {
    return this.filters;
  }

  public ContainerListGetRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ContainerListGetRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ContainerListGetRequest> filter(Predicate<ContainerListGetRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ContainerListGetRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ContainerListGetRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ContainerListGetRequest orElse(ContainerListGetRequest value) {
    return this.optional.orElse(value);
  }

  public ContainerListGetRequest orElseGet(Supplier<ContainerListGetRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ContainerListGetRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
