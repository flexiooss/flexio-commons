package io.flexio.commons.otsdb.api.types;

import io.flexio.commons.otsdb.api.types.optional.OptionalDataPoint;
import java.lang.Double;
import java.lang.Long;
import java.lang.String;
import java.util.function.Consumer;
import org.codingmatters.value.objects.values.ObjectValue;

public interface DataPoint {
  static Builder builder() {
    return new DataPoint.Builder();
  }

  static DataPoint.Builder from(DataPoint value) {
    if(value != null) {
      return new DataPoint.Builder()
          .metric(value.metric())
          .timestamp(value.timestamp())
          .value(value.value())
          .tags(value.tags())
          ;
    }
    else {
      return null;
    }
  }

  String metric();

  Long timestamp();

  Double value();

  ObjectValue tags();

  DataPoint withMetric(String value);

  DataPoint withTimestamp(Long value);

  DataPoint withValue(Double value);

  DataPoint withTags(ObjectValue value);

  int hashCode();

  DataPoint changed(DataPoint.Changer changer);

  OptionalDataPoint opt();

  class Builder {
    private String metric;

    private Long timestamp;

    private Double value;

    private ObjectValue tags;

    public DataPoint build() {
      return new DataPointImpl(this.metric, this.timestamp, this.value, this.tags);
    }

    public DataPoint.Builder metric(String metric) {
      this.metric = metric;
      return this;
    }

    public DataPoint.Builder timestamp(Long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public DataPoint.Builder value(Double value) {
      this.value = value;
      return this;
    }

    public DataPoint.Builder tags(ObjectValue tags) {
      this.tags = tags;
      return this;
    }

    public DataPoint.Builder tags(Consumer<ObjectValue.Builder> tags) {
      ObjectValue.Builder builder = ObjectValue.builder();
      tags.accept(builder);
      return this.tags(builder.build());
    }
  }

  interface Changer {
    DataPoint.Builder configure(DataPoint.Builder builder);
  }
}
