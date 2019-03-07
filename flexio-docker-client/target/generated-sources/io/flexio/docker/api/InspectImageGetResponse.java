package io.flexio.docker.api;

import io.flexio.docker.api.inspectimagegetresponse.Status200;
import io.flexio.docker.api.inspectimagegetresponse.Status404;
import io.flexio.docker.api.inspectimagegetresponse.Status500;
import io.flexio.docker.api.optional.OptionalInspectImageGetResponse;
import java.util.function.Consumer;

public interface InspectImageGetResponse {
  static Builder builder() {
    return new InspectImageGetResponse.Builder();
  }

  static InspectImageGetResponse.Builder from(InspectImageGetResponse value) {
    if(value != null) {
      return new InspectImageGetResponse.Builder()
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

  InspectImageGetResponse withStatus200(Status200 value);

  InspectImageGetResponse withStatus404(Status404 value);

  InspectImageGetResponse withStatus500(Status500 value);

  int hashCode();

  InspectImageGetResponse changed(InspectImageGetResponse.Changer changer);

  OptionalInspectImageGetResponse opt();

  class Builder {
    private Status200 status200;

    private Status404 status404;

    private Status500 status500;

    public InspectImageGetResponse build() {
      return new InspectImageGetResponseImpl(this.status200, this.status404, this.status500);
    }

    public InspectImageGetResponse.Builder status200(Status200 status200) {
      this.status200 = status200;
      return this;
    }

    public InspectImageGetResponse.Builder status200(Consumer<Status200.Builder> status200) {
      Status200.Builder builder = Status200.builder();
      status200.accept(builder);
      return this.status200(builder.build());
    }

    public InspectImageGetResponse.Builder status404(Status404 status404) {
      this.status404 = status404;
      return this;
    }

    public InspectImageGetResponse.Builder status404(Consumer<Status404.Builder> status404) {
      Status404.Builder builder = Status404.builder();
      status404.accept(builder);
      return this.status404(builder.build());
    }

    public InspectImageGetResponse.Builder status500(Status500 status500) {
      this.status500 = status500;
      return this;
    }

    public InspectImageGetResponse.Builder status500(Consumer<Status500.Builder> status500) {
      Status500.Builder builder = Status500.builder();
      status500.accept(builder);
      return this.status500(builder.build());
    }
  }

  interface Changer {
    InspectImageGetResponse.Builder configure(InspectImageGetResponse.Builder builder);
  }
}
