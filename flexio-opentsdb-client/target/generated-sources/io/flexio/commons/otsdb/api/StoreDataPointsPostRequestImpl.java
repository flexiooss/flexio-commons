package io.flexio.commons.otsdb.api;

import io.flexio.commons.otsdb.api.optional.OptionalStoreDataPointsPostRequest;
import io.flexio.commons.otsdb.api.types.DataPoint;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class StoreDataPointsPostRequestImpl implements StoreDataPointsPostRequest {
  private final String summary;

  private final String details;

  private final Boolean sync;

  private final Long sync_timeout;

  private final String authorization;

  private final ValueList<DataPoint> payload;

  StoreDataPointsPostRequestImpl(String summary, String details, Boolean sync, Long sync_timeout, String authorization, ValueList<DataPoint> payload) {
    this.summary = summary;
    this.details = details;
    this.sync = sync;
    this.sync_timeout = sync_timeout;
    this.authorization = authorization;
    this.payload = payload;
  }

  public String summary() {
    return this.summary;
  }

  public String details() {
    return this.details;
  }

  public Boolean sync() {
    return this.sync;
  }

  public Long sync_timeout() {
    return this.sync_timeout;
  }

  public String authorization() {
    return this.authorization;
  }

  public ValueList<DataPoint> payload() {
    return this.payload;
  }

  public StoreDataPointsPostRequest withSummary(String value) {
    return StoreDataPointsPostRequest.from(this).summary(value).build();
  }

  public StoreDataPointsPostRequest withDetails(String value) {
    return StoreDataPointsPostRequest.from(this).details(value).build();
  }

  public StoreDataPointsPostRequest withSync(Boolean value) {
    return StoreDataPointsPostRequest.from(this).sync(value).build();
  }

  public StoreDataPointsPostRequest withSync_timeout(Long value) {
    return StoreDataPointsPostRequest.from(this).sync_timeout(value).build();
  }

  public StoreDataPointsPostRequest withAuthorization(String value) {
    return StoreDataPointsPostRequest.from(this).authorization(value).build();
  }

  public StoreDataPointsPostRequest withPayload(ValueList<DataPoint> value) {
    return StoreDataPointsPostRequest.from(this).payload(value).build();
  }

  public StoreDataPointsPostRequest changed(StoreDataPointsPostRequest.Changer changer) {
    return changer.configure(StoreDataPointsPostRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StoreDataPointsPostRequestImpl that = (StoreDataPointsPostRequestImpl) o;
        return Objects.equals(this.summary, that.summary) && 
        Objects.equals(this.details, that.details) && 
        Objects.equals(this.sync, that.sync) && 
        Objects.equals(this.sync_timeout, that.sync_timeout) && 
        Objects.equals(this.authorization, that.authorization) && 
        Objects.equals(this.payload, that.payload);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.summary, this.details, this.sync, this.sync_timeout, this.authorization, this.payload});
  }

  @Override
  public String toString() {
    return "StoreDataPointsPostRequest{" +
        "summary=" + Objects.toString(this.summary) +
        ", " + "details=" + Objects.toString(this.details) +
        ", " + "sync=" + Objects.toString(this.sync) +
        ", " + "sync_timeout=" + Objects.toString(this.sync_timeout) +
        ", " + "authorization=" + Objects.toString(this.authorization) +
        ", " + "payload=" + Objects.toString(this.payload) +
        '}';
  }

  public OptionalStoreDataPointsPostRequest opt() {
    return OptionalStoreDataPointsPostRequest.of(this);
  }
}
