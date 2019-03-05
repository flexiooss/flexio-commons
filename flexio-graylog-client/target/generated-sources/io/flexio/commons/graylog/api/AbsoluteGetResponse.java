package io.flexio.commons.graylog.api;

import io.flexio.commons.graylog.api.absolutegetresponse.Status200;
import io.flexio.commons.graylog.api.absolutegetresponse.Status400;
import io.flexio.commons.graylog.api.optional.OptionalAbsoluteGetResponse;
import java.util.function.Consumer;

public interface AbsoluteGetResponse {
  static Builder builder() {
    return new AbsoluteGetResponse.Builder();
  }

  static AbsoluteGetResponse.Builder from(AbsoluteGetResponse value) {
    if(value != null) {
      return new AbsoluteGetResponse.Builder()
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

  AbsoluteGetResponse withStatus200(Status200 value);

  AbsoluteGetResponse withStatus400(Status400 value);

  int hashCode();

  AbsoluteGetResponse changed(AbsoluteGetResponse.Changer changer);

  OptionalAbsoluteGetResponse opt();

  class Builder {
    private Status200 status200;

    private Status400 status400;

    public AbsoluteGetResponse build() {
      return new AbsoluteGetResponseImpl(this.status200, this.status400);
    }

    public AbsoluteGetResponse.Builder status200(Status200 status200) {
      this.status200 = status200;
      return this;
    }

    public AbsoluteGetResponse.Builder status200(Consumer<Status200.Builder> status200) {
      Status200.Builder builder = Status200.builder();
      status200.accept(builder);
      return this.status200(builder.build());
    }

    public AbsoluteGetResponse.Builder status400(Status400 status400) {
      this.status400 = status400;
      return this;
    }

    public AbsoluteGetResponse.Builder status400(Consumer<Status400.Builder> status400) {
      Status400.Builder builder = Status400.builder();
      status400.accept(builder);
      return this.status400(builder.build());
    }
  }

  interface Changer {
    AbsoluteGetResponse.Builder configure(AbsoluteGetResponse.Builder builder);
  }
}
