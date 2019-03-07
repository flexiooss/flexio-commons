package io.flexio.commons.graylog.api;

import io.flexio.commons.graylog.api.optional.OptionalRelativeGetResponse;
import io.flexio.commons.graylog.api.relativegetresponse.Status200;
import io.flexio.commons.graylog.api.relativegetresponse.Status400;
import java.util.function.Consumer;

public interface RelativeGetResponse {
  static Builder builder() {
    return new RelativeGetResponse.Builder();
  }

  static RelativeGetResponse.Builder from(RelativeGetResponse value) {
    if(value != null) {
      return new RelativeGetResponse.Builder()
          .status200(value.status200())
          .status400(value.status400())
          ;
    }
    else {
      return null;
    }
  }

  Status200 status200();

  Status400 status400();

  RelativeGetResponse withStatus200(Status200 value);

  RelativeGetResponse withStatus400(Status400 value);

  int hashCode();

  RelativeGetResponse changed(RelativeGetResponse.Changer changer);

  OptionalRelativeGetResponse opt();

  class Builder {
    private Status200 status200;

    private Status400 status400;

    public RelativeGetResponse build() {
      return new RelativeGetResponseImpl(this.status200, this.status400);
    }

    public RelativeGetResponse.Builder status200(Status200 status200) {
      this.status200 = status200;
      return this;
    }

    public RelativeGetResponse.Builder status200(Consumer<Status200.Builder> status200) {
      Status200.Builder builder = Status200.builder();
      status200.accept(builder);
      return this.status200(builder.build());
    }

    public RelativeGetResponse.Builder status400(Status400 status400) {
      this.status400 = status400;
      return this;
    }

    public RelativeGetResponse.Builder status400(Consumer<Status400.Builder> status400) {
      Status400.Builder builder = Status400.builder();
      status400.accept(builder);
      return this.status400(builder.build());
    }
  }

  interface Changer {
    RelativeGetResponse.Builder configure(RelativeGetResponse.Builder builder);
  }
}
