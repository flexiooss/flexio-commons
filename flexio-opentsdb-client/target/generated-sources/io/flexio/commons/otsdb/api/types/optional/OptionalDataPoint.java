package io.flexio.commons.otsdb.api.types.optional;

import io.flexio.commons.otsdb.api.types.DataPoint;
import java.lang.Double;
import java.lang.Long;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.codingmatters.value.objects.values.optional.OptionalObjectValue;

public class OptionalDataPoint {
  private final Optional<DataPoint> optional;

  private final Optional<String> metric;

  private final Optional<Long> timestamp;

  private final Optional<Double> value;

  private OptionalObjectValue tags = this.tags;

  private OptionalDataPoint(DataPoint value) {
    this.optional = Optional.ofNullable(value);
    this.metric = Optional.ofNullable(value != null ? value.metric() : null);
    this.timestamp = Optional.ofNullable(value != null ? value.timestamp() : null);
    this.value = Optional.ofNullable(value != null ? value.value() : null);
  }

  public static OptionalDataPoint of(DataPoint value) {
    return new OptionalDataPoint(value);
  }

  public Optional<String> metric() {
    return this.metric;
  }

  public Optional<Long> timestamp() {
    return this.timestamp;
  }

  public Optional<Double> value() {
    return this.value;
  }

  public synchronized OptionalObjectValue tags() {
    if(this.tags == null) {
      this.tags = OptionalObjectValue.of(this.optional.isPresent() ? this.optional.get().tags() : null);
    }
    return this.tags;
  }

  public DataPoint get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<DataPoint> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<DataPoint> filter(Predicate<DataPoint> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<DataPoint, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<DataPoint, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public DataPoint orElse(DataPoint value) {
    return this.optional.orElse(value);
  }

  public DataPoint orElseGet(Supplier<DataPoint> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> DataPoint orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
