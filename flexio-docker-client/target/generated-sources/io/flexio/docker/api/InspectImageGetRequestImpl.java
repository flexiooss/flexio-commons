package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalInspectImageGetRequest;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class InspectImageGetRequestImpl implements InspectImageGetRequest {
  private final String imageId;

  InspectImageGetRequestImpl(String imageId) {
    this.imageId = imageId;
  }

  public String imageId() {
    return this.imageId;
  }

  public InspectImageGetRequest withImageId(String value) {
    return InspectImageGetRequest.from(this).imageId(value).build();
  }

  public InspectImageGetRequest changed(InspectImageGetRequest.Changer changer) {
    return changer.configure(InspectImageGetRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    InspectImageGetRequestImpl that = (InspectImageGetRequestImpl) o;
        return Objects.equals(this.imageId, that.imageId);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.imageId});
  }

  @Override
  public String toString() {
    return "InspectImageGetRequest{" +
        "imageId=" + Objects.toString(this.imageId) +
        '}';
  }

  public OptionalInspectImageGetRequest opt() {
    return OptionalInspectImageGetRequest.of(this);
  }
}
