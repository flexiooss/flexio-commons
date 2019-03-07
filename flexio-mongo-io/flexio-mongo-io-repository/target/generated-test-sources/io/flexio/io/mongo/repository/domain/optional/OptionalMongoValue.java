package io.flexio.io.mongo.repository.domain.optional;

import io.flexio.io.mongo.repository.domain.MongoValue;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalMongoValue {
  private final Optional<MongoValue> optional;

  private final Optional<String> name;

  private final Optional<String> slug;

  private OptionalMongoValue(MongoValue value) {
    this.optional = Optional.ofNullable(value);
    this.name = Optional.ofNullable(value != null ? value.name() : null);
    this.slug = Optional.ofNullable(value != null ? value.slug() : null);
  }

  public static OptionalMongoValue of(MongoValue value) {
    return new OptionalMongoValue(value);
  }

  public Optional<String> name() {
    return this.name;
  }

  public Optional<String> slug() {
    return this.slug;
  }

  public MongoValue get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<MongoValue> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<MongoValue> filter(Predicate<MongoValue> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<MongoValue, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<MongoValue, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public MongoValue orElse(MongoValue value) {
    return this.optional.orElse(value);
  }

  public MongoValue orElseGet(Supplier<MongoValue> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> MongoValue orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
