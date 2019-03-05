package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalImage;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ImageImpl implements Image {
  private final String id;

  private final ValueList<String> repoTags;

  ImageImpl(String id, ValueList<String> repoTags) {
    this.id = id;
    this.repoTags = repoTags;
  }

  public String id() {
    return this.id;
  }

  public ValueList<String> repoTags() {
    return this.repoTags;
  }

  public Image withId(String value) {
    return Image.from(this).id(value).build();
  }

  public Image withRepoTags(ValueList<String> value) {
    return Image.from(this).repoTags(value).build();
  }

  public Image changed(Image.Changer changer) {
    return changer.configure(Image.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ImageImpl that = (ImageImpl) o;
        return Objects.equals(this.id, that.id) && 
        Objects.equals(this.repoTags, that.repoTags);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.id, this.repoTags});
  }

  @Override
  public String toString() {
    return "Image{" +
        "id=" + Objects.toString(this.id) +
        ", " + "repoTags=" + Objects.toString(this.repoTags) +
        '}';
  }

  public OptionalImage opt() {
    return OptionalImage.of(this);
  }
}
