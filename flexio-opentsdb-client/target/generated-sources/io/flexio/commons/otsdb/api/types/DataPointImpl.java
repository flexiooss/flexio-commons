package io.flexio.commons.otsdb.api.types;

import io.flexio.commons.otsdb.api.types.optional.OptionalDataPoint;
import java.lang.Double;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;
import org.codingmatters.value.objects.values.ObjectValue;

class DataPointImpl implements DataPoint {
  private final String metric;

  private final Long timestamp;

  private final Double value;

  private final ObjectValue tags;

  DataPointImpl(String metric, Long timestamp, Double value, ObjectValue tags) {
    this.metric = metric;
    this.timestamp = timestamp;
    this.value = value;
    this.tags = tags;
  }

  public String metric() {
    return this.metric;
  }

  public Long timestamp() {
    return this.timestamp;
  }

  public Double value() {
    return this.value;
  }

  public ObjectValue tags() {
    return this.tags;
  }

  public DataPoint withMetric(String value) {
    return DataPoint.from(this).metric(value).build();
  }

  public DataPoint withTimestamp(Long value) {
    return DataPoint.from(this).timestamp(value).build();
  }

  public DataPoint withValue(Double value) {
    return DataPoint.from(this).value(value).build();
  }

  public DataPoint withTags(ObjectValue value) {
    return DataPoint.from(this).tags(value).build();
  }

  public DataPoint changed(DataPoint.Changer changer) {
    return changer.configure(DataPoint.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DataPointImpl that = (DataPointImpl) o;
        return Objects.equals(this.metric, that.metric) && 
        Objects.equals(this.timestamp, that.timestamp) && 
        Objects.equals(this.value, that.value) && 
        Objects.equals(this.tags, that.tags);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.metric, this.timestamp, this.value, this.tags});
  }

  @Override
  public String toString() {
    return "DataPoint{" +
        "metric=" + Objects.toString(this.metric) +
        ", " + "timestamp=" + Objects.toString(this.timestamp) +
        ", " + "value=" + Objects.toString(this.value) +
        ", " + "tags=" + Objects.toString(this.tags) +
        '}';
  }

  public OptionalDataPoint opt() {
    return OptionalDataPoint.of(this);
  }
}
