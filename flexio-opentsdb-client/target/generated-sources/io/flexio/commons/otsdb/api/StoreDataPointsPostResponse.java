package io.flexio.commons.otsdb.api;

import io.flexio.commons.otsdb.api.optional.OptionalStoreDataPointsPostResponse;
import io.flexio.commons.otsdb.api.storedatapointspostresponse.Status204;
import io.flexio.commons.otsdb.api.storedatapointspostresponse.Status400;
import java.util.function.Consumer;

public interface StoreDataPointsPostResponse {
  static Builder builder() {
    return new StoreDataPointsPostResponse.Builder();
  }

  static StoreDataPointsPostResponse.Builder from(StoreDataPointsPostResponse value) {
    if(value != null) {
      return new StoreDataPointsPostResponse.Builder()
          .status204(value.status204())
          .status400(value.status400())
          ;
    }
    else {
      return null;
    }
  }

  Status204 status204();

  Status400 status400();

  StoreDataPointsPostResponse withStatus204(Status204 value);

  StoreDataPointsPostResponse withStatus400(Status400 value);

  int hashCode();

  StoreDataPointsPostResponse changed(StoreDataPointsPostResponse.Changer changer);

  OptionalStoreDataPointsPostResponse opt();

  class Builder {
    private Status204 status204;

    private Status400 status400;

    public StoreDataPointsPostResponse build() {
      return new StoreDataPointsPostResponseImpl(this.status204, this.status400);
    }

    public StoreDataPointsPostResponse.Builder status204(Status204 status204) {
      this.status204 = status204;
      return this;
    }

    public StoreDataPointsPostResponse.Builder status204(Consumer<Status204.Builder> status204) {
      Status204.Builder builder = Status204.builder();
      status204.accept(builder);
      return this.status204(builder.build());
    }

    public StoreDataPointsPostResponse.Builder status400(Status400 status400) {
      this.status400 = status400;
      return this;
    }

    public StoreDataPointsPostResponse.Builder status400(Consumer<Status400.Builder> status400) {
      Status400.Builder builder = Status400.builder();
      status400.accept(builder);
      return this.status400(builder.build());
    }
  }

  interface Changer {
    StoreDataPointsPostResponse.Builder configure(StoreDataPointsPostResponse.Builder builder);
  }
}
