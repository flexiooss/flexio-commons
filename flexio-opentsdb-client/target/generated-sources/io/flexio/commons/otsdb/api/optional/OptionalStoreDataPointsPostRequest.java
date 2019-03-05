package io.flexio.commons.otsdb.api.optional;

import io.flexio.commons.otsdb.api.StoreDataPointsPostRequest;
import io.flexio.commons.otsdb.api.types.DataPoint;
import io.flexio.commons.otsdb.api.types.optional.OptionalDataPoint;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalStoreDataPointsPostRequest {
  private final Optional<StoreDataPointsPostRequest> optional;

  private final Optional<String> summary;

  private final Optional<String> details;

  private final Optional<Boolean> sync;

  private final Optional<Long> sync_timeout;

  private final Optional<String> authorization;

  private final OptionalValueList<DataPoint, OptionalDataPoint> payload;

  private OptionalStoreDataPointsPostRequest(StoreDataPointsPostRequest value) {
    this.optional = Optional.ofNullable(value);
    this.summary = Optional.ofNullable(value != null ? value.summary() : null);
    this.details = Optional.ofNullable(value != null ? value.details() : null);
    this.sync = Optional.ofNullable(value != null ? value.sync() : null);
    this.sync_timeout = Optional.ofNullable(value != null ? value.sync_timeout() : null);
    this.authorization = Optional.ofNullable(value != null ? value.authorization() : null);
    this.payload = new OptionalValueList<>(value != null ? value.payload() : null, e -> OptionalDataPoint.of(e));
  }

  public static OptionalStoreDataPointsPostRequest of(StoreDataPointsPostRequest value) {
    return new OptionalStoreDataPointsPostRequest(value);
  }

  public Optional<String> summary() {
    return this.summary;
  }

  public Optional<String> details() {
    return this.details;
  }

  public Optional<Boolean> sync() {
    return this.sync;
  }

  public Optional<Long> sync_timeout() {
    return this.sync_timeout;
  }

  public Optional<String> authorization() {
    return this.authorization;
  }

  public OptionalValueList<DataPoint, OptionalDataPoint> payload() {
    return this.payload;
  }

  public StoreDataPointsPostRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<StoreDataPointsPostRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<StoreDataPointsPostRequest> filter(Predicate<StoreDataPointsPostRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<StoreDataPointsPostRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<StoreDataPointsPostRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public StoreDataPointsPostRequest orElse(StoreDataPointsPostRequest value) {
    return this.optional.orElse(value);
  }

  public StoreDataPointsPostRequest orElseGet(Supplier<StoreDataPointsPostRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> StoreDataPointsPostRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
