package io.flexio.docker.api.createcontainerpostresponse.optional;

import io.flexio.docker.api.createcontainerpostresponse.Status409;
import io.flexio.docker.api.types.optional.OptionalError;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalStatus409 {
  private final Optional<Status409> optional;

  private OptionalError payload = this.payload;

  private OptionalStatus409(Status409 value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalStatus409 of(Status409 value) {
    return new OptionalStatus409(value);
  }

  public synchronized OptionalError payload() {
    if(this.payload == null) {
      this.payload = OptionalError.of(this.optional.isPresent() ? this.optional.get().payload() : null);
    }
    return this.payload;
  }

  public Status409 get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<Status409> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<Status409> filter(Predicate<Status409> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<Status409, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<Status409, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public Status409 orElse(Status409 value) {
    return this.optional.orElse(value);
  }

  public Status409 orElseGet(Supplier<Status409> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> Status409 orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
