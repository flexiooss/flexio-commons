package io.flexio.commons.otsdb.api.types.optional;

import io.flexio.commons.otsdb.api.types.DataPointError;
import io.flexio.commons.otsdb.api.types.StorageResponse;
import java.lang.Long;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalStorageResponse {
  private final Optional<StorageResponse> optional;

  private final Optional<Long> success;

  private final Optional<Long> failed;

  private final OptionalValueList<DataPointError, OptionalDataPointError> errors;

  private OptionalStorageResponse(StorageResponse value) {
    this.optional = Optional.ofNullable(value);
    this.success = Optional.ofNullable(value != null ? value.success() : null);
    this.failed = Optional.ofNullable(value != null ? value.failed() : null);
    this.errors = new OptionalValueList<>(value != null ? value.errors() : null, e -> OptionalDataPointError.of(e));
  }

  public static OptionalStorageResponse of(StorageResponse value) {
    return new OptionalStorageResponse(value);
  }

  public Optional<Long> success() {
    return this.success;
  }

  public Optional<Long> failed() {
    return this.failed;
  }

  public OptionalValueList<DataPointError, OptionalDataPointError> errors() {
    return this.errors;
  }

  public StorageResponse get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<StorageResponse> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<StorageResponse> filter(Predicate<StorageResponse> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<StorageResponse, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<StorageResponse, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public StorageResponse orElse(StorageResponse value) {
    return this.optional.orElse(value);
  }

  public StorageResponse orElseGet(Supplier<StorageResponse> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> StorageResponse orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
