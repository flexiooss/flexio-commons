package io.flexio.io.mongo.repository.domain.optional;

import io.flexio.io.mongo.repository.domain.MongoQuery;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalMongoQuery {
  private final Optional<MongoQuery> optional;

  private final Optional<String> name;

  private OptionalMongoQuery(MongoQuery value) {
    this.optional = Optional.ofNullable(value);
    this.name = Optional.ofNullable(value != null ? value.name() : null);
  }

  public static OptionalMongoQuery of(MongoQuery value) {
    return new OptionalMongoQuery(value);
  }

  public Optional<String> name() {
    return this.name;
  }

  public MongoQuery get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<MongoQuery> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<MongoQuery> filter(Predicate<MongoQuery> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<MongoQuery, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<MongoQuery, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public MongoQuery orElse(MongoQuery value) {
    return this.optional.orElse(value);
  }

  public MongoQuery orElseGet(Supplier<MongoQuery> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> MongoQuery orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
