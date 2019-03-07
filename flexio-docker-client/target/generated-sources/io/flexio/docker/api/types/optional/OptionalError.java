package io.flexio.docker.api.types.optional;

import io.flexio.docker.api.types.Error;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalError {
  private final Optional<Error> optional;

  private final Optional<String> message;

  private OptionalError(Error value) {
    this.optional = Optional.ofNullable(value);
    this.message = Optional.ofNullable(value != null ? value.message() : null);
  }

  public static OptionalError of(Error value) {
    return new OptionalError(value);
  }

  public Optional<String> message() {
    return this.message;
  }

  public Error get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<Error> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<Error> filter(Predicate<Error> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<Error, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<Error, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public Error orElse(Error value) {
    return this.optional.orElse(value);
  }

  public Error orElseGet(Supplier<Error> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> Error orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
