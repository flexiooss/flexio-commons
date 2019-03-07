package io.flexio.commons.otsdb.api.types;

import io.flexio.commons.otsdb.api.types.optional.OptionalDataPointError;
import java.lang.String;
import java.util.function.Consumer;

public interface DataPointError {
  static Builder builder() {
    return new DataPointError.Builder();
  }

  static DataPointError.Builder from(DataPointError value) {
    if(value != null) {
      return new DataPointError.Builder()
          .datapoint(value.datapoint())
          .error(value.error())
          ;
    }
    else {
      return null;
    }
  }

  DataPoint datapoint();

  String error();

  DataPointError withDatapoint(DataPoint value);

  DataPointError withError(String value);

  int hashCode();

  DataPointError changed(DataPointError.Changer changer);

  OptionalDataPointError opt();

  class Builder {
    private DataPoint datapoint;

    private String error;

    public DataPointError build() {
      return new DataPointErrorImpl(this.datapoint, this.error);
    }

    public DataPointError.Builder datapoint(DataPoint datapoint) {
      this.datapoint = datapoint;
      return this;
    }

    public DataPointError.Builder datapoint(Consumer<DataPoint.Builder> datapoint) {
      DataPoint.Builder builder = DataPoint.builder();
      datapoint.accept(builder);
      return this.datapoint(builder.build());
    }

    public DataPointError.Builder error(String error) {
      this.error = error;
      return this;
    }
  }

  interface Changer {
    DataPointError.Builder configure(DataPointError.Builder builder);
  }
}
