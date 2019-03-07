package io.flexio.docker.api.inspectgetresponse.optional;

import io.flexio.docker.api.inspectgetresponse.Status404;
import io.flexio.docker.api.types.optional.OptionalError;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalStatus404 {
  private final Optional<Status404> optional;

  private OptionalError payload = this.payload;

  private OptionalStatus404(Status404 value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalStatus404 of(Status404 value) {
    return new OptionalStatus404(value);
  }

  public synchronized OptionalError payload() {
    if(this.payload == null) {
      this.payload = OptionalError.of(this.optional.isPresent() ? this.optional.get().payload() : null);
    }
    return this.payload;
  }

  public Status404 get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<Status404> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<Status404> filter(Predicate<Status404> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<Status404, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<Status404, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public Status404 orElse(Status404 value) {
    return this.optional.orElse(value);
  }

  public Status404 orElseGet(Supplier<Status404> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> Status404 orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
