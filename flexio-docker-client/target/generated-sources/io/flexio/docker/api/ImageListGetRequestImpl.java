package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalImageListGetRequest;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ImageListGetRequestImpl implements ImageListGetRequest {
  private final Boolean all;

  private final Boolean digests;

  ImageListGetRequestImpl(Boolean all, Boolean digests) {
    this.all = all;
    this.digests = digests;
  }

  public Boolean all() {
    return this.all;
  }

  public Boolean digests() {
    return this.digests;
  }

  public ImageListGetRequest withAll(Boolean value) {
    return ImageListGetRequest.from(this).all(value).build();
  }

  public ImageListGetRequest withDigests(Boolean value) {
    return ImageListGetRequest.from(this).digests(value).build();
  }

  public ImageListGetRequest changed(ImageListGetRequest.Changer changer) {
    return changer.configure(ImageListGetRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ImageListGetRequestImpl that = (ImageListGetRequestImpl) o;
        return Objects.equals(this.all, that.all) && 
        Objects.equals(this.digests, that.digests);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.all, this.digests});
  }

  @Override
  public String toString() {
    return "ImageListGetRequest{" +
        "all=" + Objects.toString(this.all) +
        ", " + "digests=" + Objects.toString(this.digests) +
        '}';
  }

  public OptionalImageListGetRequest opt() {
    return OptionalImageListGetRequest.of(this);
  }
}
