package io.flexio.docker.api.optional;

import io.flexio.docker.api.StartPostRequest;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalStartPostRequest {
  private final Optional<StartPostRequest> optional;

  private final Optional<String> containerId;

  private OptionalStartPostRequest(StartPostRequest value) {
    this.optional = Optional.ofNullable(value);
    this.containerId = Optional.ofNullable(value != null ? value.containerId() : null);
  }

  public static OptionalStartPostRequest of(StartPostRequest value) {
    return new OptionalStartPostRequest(value);
  }

  public Optional<String> containerId() {
    return this.containerId;
  }

  public StartPostRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<StartPostRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<StartPostRequest> filter(Predicate<StartPostRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<StartPostRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<StartPostRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public StartPostRequest orElse(StartPostRequest value) {
    return this.optional.orElse(value);
  }

  public StartPostRequest orElseGet(Supplier<StartPostRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> StartPostRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
