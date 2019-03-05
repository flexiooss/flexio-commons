package io.flexio.docker.api.createcontainerpostresponse.optional;

import io.flexio.docker.api.createcontainerpostresponse.Status201;
import io.flexio.docker.api.types.optional.OptionalContainerCreationResult;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalStatus201 {
  private final Optional<Status201> optional;

  private OptionalContainerCreationResult payload = this.payload;

  private OptionalStatus201(Status201 value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalStatus201 of(Status201 value) {
    return new OptionalStatus201(value);
  }

  public synchronized OptionalContainerCreationResult payload() {
    if(this.payload == null) {
      this.payload = OptionalContainerCreationResult.of(this.optional.isPresent() ? this.optional.get().payload() : null);
    }
    return this.payload;
  }

  public Status201 get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<Status201> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<Status201> filter(Predicate<Status201> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<Status201, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<Status201, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public Status201 orElse(Status201 value) {
    return this.optional.orElse(value);
  }

  public Status201 orElseGet(Supplier<Status201> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> Status201 orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
