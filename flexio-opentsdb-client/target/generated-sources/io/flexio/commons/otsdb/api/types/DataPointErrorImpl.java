package io.flexio.commons.otsdb.api.types;

import io.flexio.commons.otsdb.api.types.optional.OptionalDataPointError;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class DataPointErrorImpl implements DataPointError {
  private final DataPoint datapoint;

  private final String error;

  DataPointErrorImpl(DataPoint datapoint, String error) {
    this.datapoint = datapoint;
    this.error = error;
  }

  public DataPoint datapoint() {
    return this.datapoint;
  }

  public String error() {
    return this.error;
  }

  public DataPointError withDatapoint(DataPoint value) {
    return DataPointError.from(this).datapoint(value).build();
  }

  public DataPointError withError(String value) {
    return DataPointError.from(this).error(value).build();
  }

  public DataPointError changed(DataPointError.Changer changer) {
    return changer.configure(DataPointError.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DataPointErrorImpl that = (DataPointErrorImpl) o;
        return Objects.equals(this.datapoint, that.datapoint) && 
        Objects.equals(this.error, that.error);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.datapoint, this.error});
  }

  @Override
  public String toString() {
    return "DataPointError{" +
        "datapoint=" + Objects.toString(this.datapoint) +
        ", " + "error=" + Objects.toString(this.error) +
        '}';
  }

  public OptionalDataPointError opt() {
    return OptionalDataPointError.of(this);
  }
}
