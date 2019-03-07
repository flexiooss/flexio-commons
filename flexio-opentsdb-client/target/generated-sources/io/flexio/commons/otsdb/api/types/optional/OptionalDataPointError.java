package io.flexio.commons.otsdb.api.types.optional;

import io.flexio.commons.otsdb.api.types.DataPointError;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalDataPointError {
  private final Optional<DataPointError> optional;

  private OptionalDataPoint datapoint = this.datapoint;

  private final Optional<String> error;

  private OptionalDataPointError(DataPointError value) {
    this.optional = Optional.ofNullable(value);
    this.error = Optional.ofNullable(value != null ? value.error() : null);
  }

  public static OptionalDataPointError of(DataPointError value) {
    return new OptionalDataPointError(value);
  }

  public synchronized OptionalDataPoint datapoint() {
    if(this.datapoint == null) {
      this.datapoint = OptionalDataPoint.of(this.optional.isPresent() ? this.optional.get().datapoint() : null);
    }
    return this.datapoint;
  }

  public Optional<String> error() {
    return this.error;
  }

  public DataPointError get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<DataPointError> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<DataPointError> filter(Predicate<DataPointError> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<DataPointError, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<DataPointError, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public DataPointError orElse(DataPointError value) {
    return this.optional.orElse(value);
  }

  public DataPointError orElseGet(Supplier<DataPointError> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> DataPointError orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
