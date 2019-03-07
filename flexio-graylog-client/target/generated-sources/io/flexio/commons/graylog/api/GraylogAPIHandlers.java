package io.flexio.commons.graylog.api;

import java.lang.Override;
import java.util.function.Function;

public interface GraylogAPIHandlers {
  Function<RelativeGetRequest, RelativeGetResponse> relativeGetHandler();

  Function<AbsoluteGetRequest, AbsoluteGetResponse> absoluteGetHandler();

  class Builder {
    Function<RelativeGetRequest, RelativeGetResponse> relativeGetHandler;

    Function<AbsoluteGetRequest, AbsoluteGetResponse> absoluteGetHandler;

    public Builder relativeGetHandler(Function<RelativeGetRequest, RelativeGetResponse> handler) {
      this.relativeGetHandler = handler;
      return this;
    }

    public Builder absoluteGetHandler(Function<AbsoluteGetRequest, AbsoluteGetResponse> handler) {
      this.absoluteGetHandler = handler;
      return this;
    }

    public GraylogAPIHandlers build() {
      return new DefaultImpl(this.relativeGetHandler, this.absoluteGetHandler);
    }

    private static class DefaultImpl implements GraylogAPIHandlers {
      private final Function<RelativeGetRequest, RelativeGetResponse> relativeGetHandler;

      private final Function<AbsoluteGetRequest, AbsoluteGetResponse> absoluteGetHandler;

      private DefaultImpl(Function<RelativeGetRequest, RelativeGetResponse> relativeGetHandler, Function<AbsoluteGetRequest, AbsoluteGetResponse> absoluteGetHandler) {
        this.relativeGetHandler = relativeGetHandler;
        this.absoluteGetHandler = absoluteGetHandler;
      }

      @Override
      public Function<RelativeGetRequest, RelativeGetResponse> relativeGetHandler() {
        return this.relativeGetHandler;
      }

      @Override
      public Function<AbsoluteGetRequest, AbsoluteGetResponse> absoluteGetHandler() {
        return this.absoluteGetHandler;
      }
    }
  }
}
