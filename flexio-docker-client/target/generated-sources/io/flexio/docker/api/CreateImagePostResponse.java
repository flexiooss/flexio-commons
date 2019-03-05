package io.flexio.docker.api;

import io.flexio.docker.api.createimagepostresponse.Status200;
import io.flexio.docker.api.createimagepostresponse.Status404;
import io.flexio.docker.api.createimagepostresponse.Status500;
import io.flexio.docker.api.optional.OptionalCreateImagePostResponse;
import java.util.function.Consumer;

public interface CreateImagePostResponse {
  static Builder builder() {
    return new CreateImagePostResponse.Builder();
  }

  static CreateImagePostResponse.Builder from(CreateImagePostResponse value) {
    if(value != null) {
      return new CreateImagePostResponse.Builder()
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

  CreateImagePostResponse withStatus200(Status200 value);

  CreateImagePostResponse withStatus404(Status404 value);

  CreateImagePostResponse withStatus500(Status500 value);

  int hashCode();

  CreateImagePostResponse changed(CreateImagePostResponse.Changer changer);

  OptionalCreateImagePostResponse opt();

  class Builder {
    private Status200 status200;

    private Status404 status404;

    private Status500 status500;

    public CreateImagePostResponse build() {
      return new CreateImagePostResponseImpl(this.status200, this.status404, this.status500);
    }

    public CreateImagePostResponse.Builder status200(Status200 status200) {
      this.status200 = status200;
      return this;
    }

    public CreateImagePostResponse.Builder status200(Consumer<Status200.Builder> status200) {
      Status200.Builder builder = Status200.builder();
      status200.accept(builder);
      return this.status200(builder.build());
    }

    public CreateImagePostResponse.Builder status404(Status404 status404) {
      this.status404 = status404;
      return this;
    }

    public CreateImagePostResponse.Builder status404(Consumer<Status404.Builder> status404) {
      Status404.Builder builder = Status404.builder();
      status404.accept(builder);
      return this.status404(builder.build());
    }

    public CreateImagePostResponse.Builder status500(Status500 status500) {
      this.status500 = status500;
      return this;
    }

    public CreateImagePostResponse.Builder status500(Consumer<Status500.Builder> status500) {
      Status500.Builder builder = Status500.builder();
      status500.accept(builder);
      return this.status500(builder.build());
    }
  }

  interface Changer {
    CreateImagePostResponse.Builder configure(CreateImagePostResponse.Builder builder);
  }
}
