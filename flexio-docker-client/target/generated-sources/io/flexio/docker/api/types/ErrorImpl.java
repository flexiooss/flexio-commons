package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalError;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ErrorImpl implements Error {
  private final String message;

  ErrorImpl(String message) {
    this.message = message;
  }

  public String message() {
    return this.message;
  }

  public Error withMessage(String value) {
    return Error.from(this).message(value).build();
  }

  public Error changed(Error.Changer changer) {
    return changer.configure(Error.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ErrorImpl that = (ErrorImpl) o;
        return Objects.equals(this.message, that.message);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.message});
  }

  @Override
  public String toString() {
    return "Error{" +
        "message=" + Objects.toString(this.message) +
        '}';
  }

  public OptionalError opt() {
    return OptionalError.of(this);
  }
}
