package io.flexio.docker.api.optional;

import io.flexio.docker.api.WaitForPostRequest;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalWaitForPostRequest {
  private final Optional<WaitForPostRequest> optional;

  private final Optional<String> condition;

  private final Optional<String> containerId;

  private OptionalWaitForPostRequest(WaitForPostRequest value) {
    this.optional = Optional.ofNullable(value);
    this.condition = Optional.ofNullable(value != null ? value.condition() : null);
    this.containerId = Optional.ofNullable(value != null ? value.containerId() : null);
  }

  public static OptionalWaitForPostRequest of(WaitForPostRequest value) {
    return new OptionalWaitForPostRequest(value);
  }

  public Optional<String> condition() {
    return this.condition;
  }

  public Optional<String> containerId() {
    return this.containerId;
  }

  public WaitForPostRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<WaitForPostRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<WaitForPostRequest> filter(Predicate<WaitForPostRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<WaitForPostRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<WaitForPostRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public WaitForPostRequest orElse(WaitForPostRequest value) {
    return this.optional.orElse(value);
  }

  public WaitForPostRequest orElseGet(Supplier<WaitForPostRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> WaitForPostRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
