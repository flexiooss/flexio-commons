package io.flexio.commons.otsdb.api.types;

import io.flexio.commons.otsdb.api.types.optional.OptionalStorageResponse;
import java.lang.Long;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

public interface StorageResponse {
  static Builder builder() {
    return new StorageResponse.Builder();
  }

  static StorageResponse.Builder from(StorageResponse value) {
    if(value != null) {
      return new StorageResponse.Builder()
          .success(value.success())
          .failed(value.failed())
          .errors(value.errors())
          ;
    }
    else {
      return null;
    }
  }

  Long success();

  Long failed();

  ValueList<DataPointError> errors();

  StorageResponse withSuccess(Long value);

  StorageResponse withFailed(Long value);

  StorageResponse withErrors(ValueList<DataPointError> value);

  int hashCode();

  StorageResponse changed(StorageResponse.Changer changer);

  OptionalStorageResponse opt();

  class Builder {
    private Long success;

    private Long failed;

    private ValueList<DataPointError> errors;

    public StorageResponse build() {
      return new StorageResponseImpl(this.success, this.failed, this.errors);
    }

    public StorageResponse.Builder success(Long success) {
      this.success = success;
      return this;
    }

    public StorageResponse.Builder failed(Long failed) {
      this.failed = failed;
      return this;
    }

    public StorageResponse.Builder errors() {
      this.errors = null;
      return this;
    }

    public StorageResponse.Builder errors(DataPointError... errors) {
      this.errors = errors != null ? new ValueList.Builder<DataPointError>().with(errors).build() : null;
      return this;
    }

    public StorageResponse.Builder errors(ValueList<DataPointError> errors) {
      this.errors = errors;
      return this;
    }

    public StorageResponse.Builder errors(Collection<DataPointError> errors) {
      this.errors = errors != null ? new ValueList.Builder<DataPointError>().with(errors).build() : null;
      return this;
    }

    public StorageResponse.Builder errors(Consumer<DataPointError.Builder>... errorsElements) {
      if(errorsElements != null) {
        LinkedList<DataPointError> elements = new LinkedList<DataPointError>();
        for(Consumer<DataPointError.Builder> errors : errorsElements) {
          DataPointError.Builder builder = DataPointError.builder();
          errors.accept(builder);
          elements.add(builder.build());
        }
        this.errors(elements.toArray(new DataPointError[elements.size()]));
      }
      return this;
    }
  }

  interface Changer {
    StorageResponse.Builder configure(StorageResponse.Builder builder);
  }
}
