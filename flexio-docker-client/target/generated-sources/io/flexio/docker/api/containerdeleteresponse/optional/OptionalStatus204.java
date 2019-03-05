package io.flexio.docker.api.containerdeleteresponse.optional;

import io.flexio.docker.api.containerdeleteresponse.Status204;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalStatus204 {
  private final Optional<Status204> optional;

  private OptionalStatus204(Status204 value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalStatus204 of(Status204 value) {
    return new OptionalStatus204(value);
  }

  public Status204 get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<Status204> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<Status204> filter(Predicate<Status204> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<Status204, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<Status204, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public Status204 orElse(Status204 value) {
    return this.optional.orElse(value);
  }

  public Status204 orElseGet(Supplier<Status204> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> Status204 orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
