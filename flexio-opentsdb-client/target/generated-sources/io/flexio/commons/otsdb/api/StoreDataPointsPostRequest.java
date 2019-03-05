package io.flexio.commons.otsdb.api;

import io.flexio.commons.otsdb.api.optional.OptionalStoreDataPointsPostRequest;
import io.flexio.commons.otsdb.api.types.DataPoint;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

public interface StoreDataPointsPostRequest {
  static Builder builder() {
    return new StoreDataPointsPostRequest.Builder();
  }

  static StoreDataPointsPostRequest.Builder from(StoreDataPointsPostRequest value) {
    if(value != null) {
      return new StoreDataPointsPostRequest.Builder()
          .summary(value.summary())
          .details(value.details())
          .sync(value.sync())
          .sync_timeout(value.sync_timeout())
          .authorization(value.authorization())
          .payload(value.payload())
          ;
    }
    else {
      return null;
    }
  }

  String summary();

  String details();

  Boolean sync();

  Long sync_timeout();

  String authorization();

  ValueList<DataPoint> payload();

  StoreDataPointsPostRequest withSummary(String value);

  StoreDataPointsPostRequest withDetails(String value);

  StoreDataPointsPostRequest withSync(Boolean value);

  StoreDataPointsPostRequest withSync_timeout(Long value);

  StoreDataPointsPostRequest withAuthorization(String value);

  StoreDataPointsPostRequest withPayload(ValueList<DataPoint> value);

  int hashCode();

  StoreDataPointsPostRequest changed(StoreDataPointsPostRequest.Changer changer);

  OptionalStoreDataPointsPostRequest opt();

  class Builder {
    private String summary;

    private String details;

    private Boolean sync;

    private Long sync_timeout;

    private String authorization;

    private ValueList<DataPoint> payload;

    public StoreDataPointsPostRequest build() {
      return new StoreDataPointsPostRequestImpl(this.summary, this.details, this.sync, this.sync_timeout, this.authorization, this.payload);
    }

    public StoreDataPointsPostRequest.Builder summary(String summary) {
      this.summary = summary;
      return this;
    }

    public StoreDataPointsPostRequest.Builder details(String details) {
      this.details = details;
      return this;
    }

    public StoreDataPointsPostRequest.Builder sync(Boolean sync) {
      this.sync = sync;
      return this;
    }

    public StoreDataPointsPostRequest.Builder sync_timeout(Long sync_timeout) {
      this.sync_timeout = sync_timeout;
      return this;
    }

    public StoreDataPointsPostRequest.Builder authorization(String authorization) {
      this.authorization = authorization;
      return this;
    }

    public StoreDataPointsPostRequest.Builder payload() {
      this.payload = null;
      return this;
    }

    public StoreDataPointsPostRequest.Builder payload(DataPoint... payload) {
      this.payload = payload != null ? new ValueList.Builder<DataPoint>().with(payload).build() : null;
      return this;
    }

    public StoreDataPointsPostRequest.Builder payload(ValueList<DataPoint> payload) {
      this.payload = payload;
      return this;
    }

    public StoreDataPointsPostRequest.Builder payload(Collection<DataPoint> payload) {
      this.payload = payload != null ? new ValueList.Builder<DataPoint>().with(payload).build() : null;
      return this;
    }

    public StoreDataPointsPostRequest.Builder payload(Consumer<DataPoint.Builder>... payloadElements) {
      if(payloadElements != null) {
        LinkedList<DataPoint> elements = new LinkedList<DataPoint>();
        for(Consumer<DataPoint.Builder> payload : payloadElements) {
          DataPoint.Builder builder = DataPoint.builder();
          payload.accept(builder);
          elements.add(builder.build());
        }
        this.payload(elements.toArray(new DataPoint[elements.size()]));
      }
      return this;
    }
  }

  interface Changer {
    StoreDataPointsPostRequest.Builder configure(StoreDataPointsPostRequest.Builder builder);
  }
}
