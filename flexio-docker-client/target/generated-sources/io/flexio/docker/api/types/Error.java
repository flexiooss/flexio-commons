package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalError;
import java.lang.String;

public interface Error {
  static Builder builder() {
    return new Error.Builder();
  }

  static Error.Builder from(Error value) {
    if(value != null) {
      return new Error.Builder()
          .message(value.message())
          ;
    }
    else {
      return null;
    }
  }

  String message();

  Error withMessage(String value);

  int hashCode();

  Error changed(Error.Changer changer);

  OptionalError opt();

  class Builder {
    private String message;

    public Error build() {
      return new ErrorImpl(this.message);
    }

    public Error.Builder message(String message) {
      this.message = message;
      return this;
    }
  }

  interface Changer {
    Error.Builder configure(Error.Builder builder);
  }
}
