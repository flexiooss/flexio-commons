package io.flexio.commons.otsdb.api.storedatapointspostresponse;

import io.flexio.commons.otsdb.api.storedatapointspostresponse.optional.OptionalStatus400;
import io.flexio.commons.otsdb.api.types.StorageResponse;
import java.util.function.Consumer;

public interface Status400 {
  static Builder builder() {
    return new Status400.Builder();
  }

  static Status400.Builder from(Status400 value) {
    if(value != null) {
      return new Status400.Builder()
          .payload(value.payload())
          ;
    }
    else {
      return null;
    }
  }

  StorageResponse payload();

  Status400 withPayload(StorageResponse value);

  int hashCode();

  Status400 changed(Status400.Changer changer);

  OptionalStatus400 opt();

  class Builder {
    private StorageResponse payload;

    public Status400 build() {
      return new Status400Impl(this.payload);
    }

    public Status400.Builder payload(StorageResponse payload) {
      this.payload = payload;
      return this;
    }

    public Status400.Builder payload(Consumer<StorageResponse.Builder> payload) {
      StorageResponse.Builder builder = StorageResponse.builder();
      payload.accept(builder);
      return this.payload(builder.build());
    }
  }

  interface Changer {
    Status400.Builder configure(Status400.Builder builder);
  }
}
