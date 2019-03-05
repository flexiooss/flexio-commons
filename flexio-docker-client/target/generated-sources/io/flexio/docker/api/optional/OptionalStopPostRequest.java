package io.flexio.docker.api.optional;

import io.flexio.docker.api.StopPostRequest;
import java.lang.Long;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalStopPostRequest {
  private final Optional<StopPostRequest> optional;

  private final Optional<Long> t;

  private final Optional<String> containerId;

  private OptionalStopPostRequest(StopPostRequest value) {
    this.optional = Optional.ofNullable(value);
    this.t = Optional.ofNullable(value != null ? value.t() : null);
    this.containerId = Optional.ofNullable(value != null ? value.containerId() : null);
  }

  public static OptionalStopPostRequest of(StopPostRequest value) {
    return new OptionalStopPostRequest(value);
  }

  public Optional<Long> t() {
    return this.t;
  }

  public Optional<String> containerId() {
    return this.containerId;
  }

  public StopPostRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<StopPostRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<StopPostRequest> filter(Predicate<StopPostRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<StopPostRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<StopPostRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public StopPostRequest orElse(StopPostRequest value) {
    return this.optional.orElse(value);
  }

  public StopPostRequest orElseGet(Supplier<StopPostRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> StopPostRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
