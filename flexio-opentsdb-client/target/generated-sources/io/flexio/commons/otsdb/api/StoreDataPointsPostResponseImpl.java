package io.flexio.commons.otsdb.api;

import io.flexio.commons.otsdb.api.optional.OptionalStoreDataPointsPostResponse;
import io.flexio.commons.otsdb.api.storedatapointspostresponse.Status204;
import io.flexio.commons.otsdb.api.storedatapointspostresponse.Status400;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class StoreDataPointsPostResponseImpl implements StoreDataPointsPostResponse {
  private final Status204 status204;

  private final Status400 status400;

  StoreDataPointsPostResponseImpl(Status204 status204, Status400 status400) {
    this.status204 = status204;
    this.status400 = status400;
  }

  public Status204 status204() {
    return this.status204;
  }

  public Status400 status400() {
    return this.status400;
  }

  public StoreDataPointsPostResponse withStatus204(Status204 value) {
    return StoreDataPointsPostResponse.from(this).status204(value).build();
  }

  public StoreDataPointsPostResponse withStatus400(Status400 value) {
    return StoreDataPointsPostResponse.from(this).status400(value).build();
  }

  public StoreDataPointsPostResponse changed(StoreDataPointsPostResponse.Changer changer) {
    return changer.configure(StoreDataPointsPostResponse.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StoreDataPointsPostResponseImpl that = (StoreDataPointsPostResponseImpl) o;
        return Objects.equals(this.status204, that.status204) && 
        Objects.equals(this.status400, that.status400);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.status204, this.status400});
  }

  @Override
  public String toString() {
    return "StoreDataPointsPostResponse{" +
        "status204=" + Objects.toString(this.status204) +
        ", " + "status400=" + Objects.toString(this.status400) +
        '}';
  }

  public OptionalStoreDataPointsPostResponse opt() {
    return OptionalStoreDataPointsPostResponse.of(this);
  }
}
