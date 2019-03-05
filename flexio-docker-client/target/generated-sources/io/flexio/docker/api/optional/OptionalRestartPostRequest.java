package io.flexio.docker.api.optional;

import io.flexio.docker.api.RestartPostRequest;
import java.lang.Long;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalRestartPostRequest {
  private final Optional<RestartPostRequest> optional;

  private final Optional<Long> t;

  private final Optional<String> containerId;

  private OptionalRestartPostRequest(RestartPostRequest value) {
    this.optional = Optional.ofNullable(value);
    this.t = Optional.ofNullable(value != null ? value.t() : null);
    this.containerId = Optional.ofNullable(value != null ? value.containerId() : null);
  }

  public static OptionalRestartPostRequest of(RestartPostRequest value) {
    return new OptionalRestartPostRequest(value);
  }

  public Optional<Long> t() {
    return this.t;
  }

  public Optional<String> containerId() {
    return this.containerId;
  }

  public RestartPostRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<RestartPostRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<RestartPostRequest> filter(Predicate<RestartPostRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<RestartPostRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<RestartPostRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public RestartPostRequest orElse(RestartPostRequest value) {
    return this.optional.orElse(value);
  }

  public RestartPostRequest orElseGet(Supplier<RestartPostRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> RestartPostRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
