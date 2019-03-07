package io.flexio.commons.otsdb.api.types;

import io.flexio.commons.otsdb.api.types.optional.OptionalStorageResponse;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class StorageResponseImpl implements StorageResponse {
  private final Long success;

  private final Long failed;

  private final ValueList<DataPointError> errors;

  StorageResponseImpl(Long success, Long failed, ValueList<DataPointError> errors) {
    this.success = success;
    this.failed = failed;
    this.errors = errors;
  }

  public Long success() {
    return this.success;
  }

  public Long failed() {
    return this.failed;
  }

  public ValueList<DataPointError> errors() {
    return this.errors;
  }

  public StorageResponse withSuccess(Long value) {
    return StorageResponse.from(this).success(value).build();
  }

  public StorageResponse withFailed(Long value) {
    return StorageResponse.from(this).failed(value).build();
  }

  public StorageResponse withErrors(ValueList<DataPointError> value) {
    return StorageResponse.from(this).errors(value).build();
  }

  public StorageResponse changed(StorageResponse.Changer changer) {
    return changer.configure(StorageResponse.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StorageResponseImpl that = (StorageResponseImpl) o;
        return Objects.equals(this.success, that.success) && 
        Objects.equals(this.failed, that.failed) && 
        Objects.equals(this.errors, that.errors);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.success, this.failed, this.errors});
  }

  @Override
  public String toString() {
    return "StorageResponse{" +
        "success=" + Objects.toString(this.success) +
        ", " + "failed=" + Objects.toString(this.failed) +
        ", " + "errors=" + Objects.toString(this.errors) +
        '}';
  }

  public OptionalStorageResponse opt() {
    return OptionalStorageResponse.of(this);
  }
}
