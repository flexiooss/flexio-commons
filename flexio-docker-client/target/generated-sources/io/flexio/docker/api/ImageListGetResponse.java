package io.flexio.docker.api;

import io.flexio.docker.api.imagelistgetresponse.Status200;
import io.flexio.docker.api.imagelistgetresponse.Status500;
import io.flexio.docker.api.optional.OptionalImageListGetResponse;
import java.util.function.Consumer;

public interface ImageListGetResponse {
  static Builder builder() {
    return new ImageListGetResponse.Builder();
  }

  static ImageListGetResponse.Builder from(ImageListGetResponse value) {
    if(value != null) {
      return new ImageListGetResponse.Builder()
          .status200(value.status200())
          .status500(value.status500())
          ;
    }
    else {
      return null;
    }
  }

  Status200 status200();

  Status500 status500();

  ImageListGetResponse withStatus200(Status200 value);

  ImageListGetResponse withStatus500(Status500 value);

  int hashCode();

  ImageListGetResponse changed(ImageListGetResponse.Changer changer);

  OptionalImageListGetResponse opt();

  class Builder {
    private Status200 status200;

    private Status500 status500;

    public ImageListGetResponse build() {
      return new ImageListGetResponseImpl(this.status200, this.status500);
    }

    public ImageListGetResponse.Builder status200(Status200 status200) {
      this.status200 = status200;
      return this;
    }

    public ImageListGetResponse.Builder status200(Consumer<Status200.Builder> status200) {
      Status200.Builder builder = Status200.builder();
      status200.accept(builder);
      return this.status200(builder.build());
    }

    public ImageListGetResponse.Builder status500(Status500 status500) {
      this.status500 = status500;
      return this;
    }

    public ImageListGetResponse.Builder status500(Consumer<Status500.Builder> status500) {
      Status500.Builder builder = Status500.builder();
      status500.accept(builder);
      return this.status500(builder.build());
    }
  }

  interface Changer {
    ImageListGetResponse.Builder configure(ImageListGetResponse.Builder builder);
  }
}
