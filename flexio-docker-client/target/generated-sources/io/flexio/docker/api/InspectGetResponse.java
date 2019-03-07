package io.flexio.docker.api;

import io.flexio.docker.api.inspectgetresponse.Status200;
import io.flexio.docker.api.inspectgetresponse.Status404;
import io.flexio.docker.api.inspectgetresponse.Status500;
import io.flexio.docker.api.optional.OptionalInspectGetResponse;
import java.util.function.Consumer;

public interface InspectGetResponse {
  static Builder builder() {
    return new InspectGetResponse.Builder();
  }

  static InspectGetResponse.Builder from(InspectGetResponse value) {
    if(value != null) {
      return new InspectGetResponse.Builder()
          .status200(value.status200())
          .status404(value.status404())
          .status500(value.status500())
          ;
    }
    else {
      return null;
    }
  }

  Status200 status200();

  Status404 status404();

  Status500 status500();

  InspectGetResponse withStatus200(Status200 value);

  InspectGetResponse withStatus404(Status404 value);

  InspectGetResponse withStatus500(Status500 value);

  int hashCode();

  InspectGetResponse changed(InspectGetResponse.Changer changer);

  OptionalInspectGetResponse opt();

  class Builder {
    private Status200 status200;

    private Status404 status404;

    private Status500 status500;

    public InspectGetResponse build() {
      return new InspectGetResponseImpl(this.status200, this.status404, this.status500);
    }

    public InspectGetResponse.Builder status200(Status200 status200) {
      this.status200 = status200;
      return this;
    }

    public InspectGetResponse.Builder status200(Consumer<Status200.Builder> status200) {
      Status200.Builder builder = Status200.builder();
      status200.accept(builder);
      return this.status200(builder.build());
    }

    public InspectGetResponse.Builder status404(Status404 status404) {
      this.status404 = status404;
      return this;
    }

    public InspectGetResponse.Builder status404(Consumer<Status404.Builder> status404) {
      Status404.Builder builder = Status404.builder();
      status404.accept(builder);
      return this.status404(builder.build());
    }

    public InspectGetResponse.Builder status500(Status500 status500) {
      this.status500 = status500;
      return this;
    }

    public InspectGetResponse.Builder status500(Consumer<Status500.Builder> status500) {
      Status500.Builder builder = Status500.builder();
      status500.accept(builder);
      return this.status500(builder.build());
    }
  }

  interface Changer {
    InspectGetResponse.Builder configure(InspectGetResponse.Builder builder);
  }
}
