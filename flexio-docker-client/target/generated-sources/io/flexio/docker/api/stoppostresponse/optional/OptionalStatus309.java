package io.flexio.docker.api.stoppostresponse.optional;

import io.flexio.docker.api.stoppostresponse.Status309;
import io.flexio.docker.api.types.optional.OptionalError;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalStatus309 {
  private final Optional<Status309> optional;

  private OptionalError payload = this.payload;

  private OptionalStatus309(Status309 value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalStatus309 of(Status309 value) {
    return new OptionalStatus309(value);
  }

  public synchronized OptionalError payload() {
    if(this.payload == null) {
      this.payload = OptionalError.of(this.optional.isPresent() ? this.optional.get().payload() : null);
    }
    return this.payload;
  }

  public Status309 get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<Status309> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<Status309> filter(Predicate<Status309> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<Status309, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<Status309, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public Status309 orElse(Status309 value) {
    return this.optional.orElse(value);
  }

  public Status309 orElseGet(Supplier<Status309> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> Status309 orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
