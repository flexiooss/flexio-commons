package io.flexio.docker.api.waitforpostresponse.optional;

import io.flexio.docker.api.types.optional.OptionalError;
import io.flexio.docker.api.waitforpostresponse.Status500;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalStatus500 {
  private final Optional<Status500> optional;

  private OptionalError payload = this.payload;

  private OptionalStatus500(Status500 value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalStatus500 of(Status500 value) {
    return new OptionalStatus500(value);
  }

  public synchronized OptionalError payload() {
    if(this.payload == null) {
      this.payload = OptionalError.of(this.optional.isPresent() ? this.optional.get().payload() : null);
    }
    return this.payload;
  }

  public Status500 get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<Status500> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<Status500> filter(Predicate<Status500> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<Status500, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<Status500, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public Status500 orElse(Status500 value) {
    return this.optional.orElse(value);
  }

  public Status500 orElseGet(Supplier<Status500> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> Status500 orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
