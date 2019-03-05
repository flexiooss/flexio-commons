package io.flexio.commons.otsdb.api.optional;

import io.flexio.commons.otsdb.api.StoreDataPointsPostResponse;
import io.flexio.commons.otsdb.api.storedatapointspostresponse.optional.OptionalStatus204;
import io.flexio.commons.otsdb.api.storedatapointspostresponse.optional.OptionalStatus400;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalStoreDataPointsPostResponse {
  private final Optional<StoreDataPointsPostResponse> optional;

  private OptionalStatus204 status204 = this.status204;

  private OptionalStatus400 status400 = this.status400;

  private OptionalStoreDataPointsPostResponse(StoreDataPointsPostResponse value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalStoreDataPointsPostResponse of(StoreDataPointsPostResponse value) {
    return new OptionalStoreDataPointsPostResponse(value);
  }

  public synchronized OptionalStatus204 status204() {
    if(this.status204 == null) {
      this.status204 = OptionalStatus204.of(this.optional.isPresent() ? this.optional.get().status204() : null);
    }
    return this.status204;
  }

  public synchronized OptionalStatus400 status400() {
    if(this.status400 == null) {
      this.status400 = OptionalStatus400.of(this.optional.isPresent() ? this.optional.get().status400() : null);
    }
    return this.status400;
  }

  public StoreDataPointsPostResponse get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<StoreDataPointsPostResponse> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<StoreDataPointsPostResponse> filter(Predicate<StoreDataPointsPostResponse> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<StoreDataPointsPostResponse, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<StoreDataPointsPostResponse, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public StoreDataPointsPostResponse orElse(StoreDataPointsPostResponse value) {
    return this.optional.orElse(value);
  }

  public StoreDataPointsPostResponse orElseGet(Supplier<StoreDataPointsPostResponse> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> StoreDataPointsPostResponse orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
