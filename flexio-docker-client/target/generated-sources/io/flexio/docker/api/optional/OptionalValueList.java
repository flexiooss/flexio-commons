package io.flexio.docker.api.optional;

import io.flexio.docker.api.ValueList;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalValueList<E, O> {
  private final Optional<ValueList<E>> optional;

  private Function<E, O> createOptional;

  public OptionalValueList(ValueList elements, Function<E, O> createOptional) {
    this.optional = Optional.ofNullable(elements);
    this.createOptional = createOptional;
  }

  public ValueList<E> get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ValueList<E>> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ValueList<E>> filter(Predicate<ValueList<E>> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ValueList<E>, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ValueList<E>, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ValueList<E> orElse(ValueList<E> value) {
    return this.optional.orElse(value);
  }

  public ValueList<E> orElseGet(Supplier<ValueList<E>> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ValueList<E> orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }

  public O get(int index) {
    if(this.optional.isPresent()) {
      return this.createOptional.apply(this.optional.get().size() > index ? this.optional.get().get(index) : null);
    } else {
      return this.createOptional.apply(null);
    }
  }
}
