package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalInspectImageGetRequest;
import java.lang.String;

public interface InspectImageGetRequest {
  static Builder builder() {
    return new InspectImageGetRequest.Builder();
  }

  static InspectImageGetRequest.Builder from(InspectImageGetRequest value) {
    if(value != null) {
      return new InspectImageGetRequest.Builder()
          .imageId(value.imageId())
          ;
    }
    else {
      return null;
    }
  }

  String imageId();

  InspectImageGetRequest withImageId(String value);

  int hashCode();

  InspectImageGetRequest changed(InspectImageGetRequest.Changer changer);

  OptionalInspectImageGetRequest opt();

  class Builder {
    private String imageId;

    public InspectImageGetRequest build() {
      return new InspectImageGetRequestImpl(this.imageId);
    }

    public InspectImageGetRequest.Builder imageId(String imageId) {
      this.imageId = imageId;
      return this;
    }
  }

  interface Changer {
    InspectImageGetRequest.Builder configure(InspectImageGetRequest.Builder builder);
  }
}
