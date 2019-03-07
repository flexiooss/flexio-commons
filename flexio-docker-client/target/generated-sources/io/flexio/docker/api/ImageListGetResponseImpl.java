package io.flexio.docker.api;

import io.flexio.docker.api.imagelistgetresponse.Status200;
import io.flexio.docker.api.imagelistgetresponse.Status500;
import io.flexio.docker.api.optional.OptionalImageListGetResponse;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ImageListGetResponseImpl implements ImageListGetResponse {
  private final Status200 status200;

  private final Status500 status500;

  ImageListGetResponseImpl(Status200 status200, Status500 status500) {
    this.status200 = status200;
    this.status500 = status500;
  }

  public Status200 status200() {
    return this.status200;
  }

  public Status500 status500() {
    return this.status500;
  }

  public ImageListGetResponse withStatus200(Status200 value) {
    return ImageListGetResponse.from(this).status200(value).build();
  }

  public ImageListGetResponse withStatus500(Status500 value) {
    return ImageListGetResponse.from(this).status500(value).build();
  }

  public ImageListGetResponse changed(ImageListGetResponse.Changer changer) {
    return changer.configure(ImageListGetResponse.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ImageListGetResponseImpl that = (ImageListGetResponseImpl) o;
        return Objects.equals(this.status200, that.status200) && 
        Objects.equals(this.status500, that.status500);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.status200, this.status500});
  }

  @Override
  public String toString() {
    return "ImageListGetResponse{" +
        "status200=" + Objects.toString(this.status200) +
        ", " + "status500=" + Objects.toString(this.status500) +
        '}';
  }

  public OptionalImageListGetResponse opt() {
    return OptionalImageListGetResponse.of(this);
  }
}
