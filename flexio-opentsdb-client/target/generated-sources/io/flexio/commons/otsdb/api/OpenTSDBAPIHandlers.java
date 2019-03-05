package io.flexio.commons.otsdb.api;

import java.lang.Override;
import java.util.function.Function;

public interface OpenTSDBAPIHandlers {
  Function<StoreDataPointsPostRequest, StoreDataPointsPostResponse> storeDataPointsPostHandler();

  class Builder {
    Function<StoreDataPointsPostRequest, StoreDataPointsPostResponse> storeDataPointsPostHandler;

    public Builder storeDataPointsPostHandler(Function<StoreDataPointsPostRequest, StoreDataPointsPostResponse> handler) {
      this.storeDataPointsPostHandler = handler;
      return this;
    }

    public OpenTSDBAPIHandlers build() {
      return new DefaultImpl(this.storeDataPointsPostHandler);
    }

    private static class DefaultImpl implements OpenTSDBAPIHandlers {
      private final Function<StoreDataPointsPostRequest, StoreDataPointsPostResponse> storeDataPointsPostHandler;

      private DefaultImpl(Function<StoreDataPointsPostRequest, StoreDataPointsPostResponse> storeDataPointsPostHandler) {
        this.storeDataPointsPostHandler = storeDataPointsPostHandler;
      }

      @Override
      public Function<StoreDataPointsPostRequest, StoreDataPointsPostResponse> storeDataPointsPostHandler() {
        return this.storeDataPointsPostHandler;
      }
    }
  }
}
