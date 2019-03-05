package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalImageListGetRequest;
import java.lang.Boolean;

public interface ImageListGetRequest {
  static Builder builder() {
    return new ImageListGetRequest.Builder();
  }

  static ImageListGetRequest.Builder from(ImageListGetRequest value) {
    if(value != null) {
      return new ImageListGetRequest.Builder()
          .all(value.all())
          .digests(value.digests())
          ;
    }
    else {
      return null;
    }
  }

  Boolean all();

  Boolean digests();

  ImageListGetRequest withAll(Boolean value);

  ImageListGetRequest withDigests(Boolean value);

  int hashCode();

  ImageListGetRequest changed(ImageListGetRequest.Changer changer);

  OptionalImageListGetRequest opt();

  class Builder {
    private Boolean all;

    private Boolean digests;

    public ImageListGetRequest build() {
      return new ImageListGetRequestImpl(this.all, this.digests);
    }

    public ImageListGetRequest.Builder all(Boolean all) {
      this.all = all;
      return this;
    }

    public ImageListGetRequest.Builder digests(Boolean digests) {
      this.digests = digests;
      return this;
    }
  }

  interface Changer {
    ImageListGetRequest.Builder configure(ImageListGetRequest.Builder builder);
  }
}
