package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalImage;
import java.lang.String;
import java.util.Collection;

public interface Image {
  static Builder builder() {
    return new Image.Builder();
  }

  static Image.Builder from(Image value) {
    if(value != null) {
      return new Image.Builder()
          .id(value.id())
          .repoTags(value.repoTags())
          ;
    }
    else {
      return null;
    }
  }

  String id();

  ValueList<String> repoTags();

  Image withId(String value);

  Image withRepoTags(ValueList<String> value);

  int hashCode();

  Image changed(Image.Changer changer);

  OptionalImage opt();

  class Builder {
    private String id;

    private ValueList<String> repoTags;

    public Image build() {
      return new ImageImpl(this.id, this.repoTags);
    }

    public Image.Builder id(String id) {
      this.id = id;
      return this;
    }

    public Image.Builder repoTags() {
      this.repoTags = null;
      return this;
    }

    public Image.Builder repoTags(String... repoTags) {
      this.repoTags = repoTags != null ? new ValueList.Builder<String>().with(repoTags).build() : null;
      return this;
    }

    public Image.Builder repoTags(ValueList<String> repoTags) {
      this.repoTags = repoTags;
      return this;
    }

    public Image.Builder repoTags(Collection<String> repoTags) {
      this.repoTags = repoTags != null ? new ValueList.Builder<String>().with(repoTags).build() : null;
      return this;
    }
  }

  interface Changer {
    Image.Builder configure(Image.Builder builder);
  }
}
