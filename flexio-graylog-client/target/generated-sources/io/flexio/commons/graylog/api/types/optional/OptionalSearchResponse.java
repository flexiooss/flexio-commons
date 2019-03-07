package io.flexio.commons.graylog.api.types.optional;

import io.flexio.commons.graylog.api.types.SearchResponse;
import java.lang.Long;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.codingmatters.value.objects.values.ObjectValue;
import org.codingmatters.value.objects.values.optional.OptionalObjectValue;

public class OptionalSearchResponse {
  private final Optional<SearchResponse> optional;

  private final Optional<String> from;

  private final Optional<String> to;

  private final OptionalValueList<ObjectValue, OptionalObjectValue> messages;

  private final OptionalValueList<String, Optional<String>> fields;

  private final Optional<Long> time;

  private final Optional<String> built_query;

  private OptionalObjectValue decoration_stats = this.decoration_stats;

  private final Optional<Long> total_results;

  private final OptionalValueList<ObjectValue, OptionalObjectValue> used_indices;

  private final Optional<String> query;

  private OptionalSearchResponse(SearchResponse value) {
    this.optional = Optional.ofNullable(value);
    this.from = Optional.ofNullable(value != null ? value.from() : null);
    this.to = Optional.ofNullable(value != null ? value.to() : null);
    this.messages = new OptionalValueList<>(value != null ? value.messages() : null, e -> OptionalObjectValue.of(e));
    this.fields = new OptionalValueList<>(value != null ? value.fields() : null, e -> Optional.ofNullable(e));
    this.time = Optional.ofNullable(value != null ? value.time() : null);
    this.built_query = Optional.ofNullable(value != null ? value.built_query() : null);
    this.total_results = Optional.ofNullable(value != null ? value.total_results() : null);
    this.used_indices = new OptionalValueList<>(value != null ? value.used_indices() : null, e -> OptionalObjectValue.of(e));
    this.query = Optional.ofNullable(value != null ? value.query() : null);
  }

  public static OptionalSearchResponse of(SearchResponse value) {
    return new OptionalSearchResponse(value);
  }

  public Optional<String> from() {
    return this.from;
  }

  public Optional<String> to() {
    return this.to;
  }

  public OptionalValueList<ObjectValue, OptionalObjectValue> messages() {
    return this.messages;
  }

  public OptionalValueList<String, Optional<String>> fields() {
    return this.fields;
  }

  public Optional<Long> time() {
    return this.time;
  }

  public Optional<String> built_query() {
    return this.built_query;
  }

  public synchronized OptionalObjectValue decoration_stats() {
    if(this.decoration_stats == null) {
      this.decoration_stats = OptionalObjectValue.of(this.optional.isPresent() ? this.optional.get().decoration_stats() : null);
    }
    return this.decoration_stats;
  }

  public Optional<Long> total_results() {
    return this.total_results;
  }

  public OptionalValueList<ObjectValue, OptionalObjectValue> used_indices() {
    return this.used_indices;
  }

  public Optional<String> query() {
    return this.query;
  }

  public SearchResponse get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<SearchResponse> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<SearchResponse> filter(Predicate<SearchResponse> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<SearchResponse, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<SearchResponse, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public SearchResponse orElse(SearchResponse value) {
    return this.optional.orElse(value);
  }

  public SearchResponse orElseGet(Supplier<SearchResponse> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> SearchResponse orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
