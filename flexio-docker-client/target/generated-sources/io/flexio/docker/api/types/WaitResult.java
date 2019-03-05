package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalWaitResult;
import java.lang.Long;

public interface WaitResult {
  static Builder builder() {
    return new WaitResult.Builder();
  }

  static WaitResult.Builder from(WaitResult value) {
    if(value != null) {
      return new WaitResult.Builder()
          .statusCode(value.statusCode())
          ;
    }
    else {
      return null;
    }
  }

  Long statusCode();

  WaitResult withStatusCode(Long value);

  int hashCode();

  WaitResult changed(WaitResult.Changer changer);

  OptionalWaitResult opt();

  class Builder {
    private Long statusCode;

    public WaitResult build() {
      return new WaitResultImpl(this.statusCode);
    }

    public WaitResult.Builder statusCode(Long statusCode) {
      this.statusCode = statusCode;
      return this;
    }
  }

  interface Changer {
    WaitResult.Builder configure(WaitResult.Builder builder);
  }
}
