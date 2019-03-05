package io.flexio.commons.graylog.api.optional;

import io.flexio.commons.graylog.api.RelativeGetRequest;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalRelativeGetRequest {
  private final Optional<RelativeGetRequest> optional;

  private final Optional<String> query;

  private final Optional<String> range;

  private final Optional<Long> limit;

  private final Optional<Long> offset;

  private final Optional<String> filter;

  private final OptionalValueList<String, Optional<String>> fields;

  private final Optional<String> sort;

  private final Optional<Boolean> decorate;

  private final Optional<String> accept;

  private final Optional<String> authorization;

  private OptionalRelativeGetRequest(RelativeGetRequest value) {
    this.optional = Optional.ofNullable(value);
    this.query = Optional.ofNullable(value != null ? value.query() : null);
    this.range = Optional.ofNullable(value != null ? value.range() : null);
    this.limit = Optional.ofNullable(value != null ? value.limit() : null);
    this.offset = Optional.ofNullable(value != null ? value.offset() : null);
    this.filter = Optional.ofNullable(value != null ? value.filter() : null);
    this.fields = new OptionalValueList<>(value != null ? value.fields() : null, e -> Optional.ofNullable(e));
    this.sort = Optional.ofNullable(value != null ? value.sort() : null);
    this.decorate = Optional.ofNullable(value != null ? value.decorate() : null);
    this.accept = Optional.ofNullable(value != null ? value.accept() : null);
    this.authorization = Optional.ofNullable(value != null ? value.authorization() : null);
  }

  public static OptionalRelativeGetRequest of(RelativeGetRequest value) {
    return new OptionalRelativeGetRequest(value);
  }

  public Optional<String> query() {
    return this.query;
  }

  public Optional<String> range() {
    return this.range;
  }

  public Optional<Long> limit() {
    return this.limit;
  }

  public Optional<Long> offset() {
    return this.offset;
  }

  public Optional<String> filter() {
    return this.filter;
  }

  public OptionalValueList<String, Optional<String>> fields() {
    return this.fields;
  }

  public Optional<String> sort() {
    return this.sort;
  }

  public Optional<Boolean> decorate() {
    return this.decorate;
  }

  public Optional<String> accept() {
    return this.accept;
  }

  public Optional<String> authorization() {
    return this.authorization;
  }

  public RelativeGetRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<RelativeGetRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<RelativeGetRequest> filter(Predicate<RelativeGetRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<RelativeGetRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<RelativeGetRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public RelativeGetRequest orElse(RelativeGetRequest value) {
    return this.optional.orElse(value);
  }

  public RelativeGetRequest orElseGet(Supplier<RelativeGetRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> RelativeGetRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
