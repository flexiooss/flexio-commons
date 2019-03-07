package io.flexio.commons.graylog.api.absolutegetresponse.optional;

import io.flexio.commons.graylog.api.absolutegetresponse.Status400;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.codingmatters.value.objects.values.optional.OptionalObjectValue;

public class OptionalStatus400 {
  private final Optional<Status400> optional;

  private OptionalObjectValue payload = this.payload;

  private OptionalStatus400(Status400 value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalStatus400 of(Status400 value) {
    return new OptionalStatus400(value);
  }

  public synchronized OptionalObjectValue payload() {
    if(this.payload == null) {
      this.payload = OptionalObjectValue.of(this.optional.isPresent() ? this.optional.get().payload() : null);
    }
    return this.payload;
  }

  public Status400 get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<Status400> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<Status400> filter(Predicate<Status400> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<Status400, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<Status400, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public Status400 orElse(Status400 value) {
    return this.optional.orElse(value);
  }

  public Status400 orElseGet(Supplier<Status400> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> Status400 orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
