package io.flexio.docker.api.types.optional;

import io.flexio.docker.api.types.WaitResult;
import java.lang.Long;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalWaitResult {
  private final Optional<WaitResult> optional;

  private final Optional<Long> statusCode;

  private OptionalWaitResult(WaitResult value) {
    this.optional = Optional.ofNullable(value);
    this.statusCode = Optional.ofNullable(value != null ? value.statusCode() : null);
  }

  public static OptionalWaitResult of(WaitResult value) {
    return new OptionalWaitResult(value);
  }

  public Optional<Long> statusCode() {
    return this.statusCode;
  }

  public WaitResult get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<WaitResult> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<WaitResult> filter(Predicate<WaitResult> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<WaitResult, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<WaitResult, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public WaitResult orElse(WaitResult value) {
    return this.optional.orElse(value);
  }

  public WaitResult orElseGet(Supplier<WaitResult> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> WaitResult orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
