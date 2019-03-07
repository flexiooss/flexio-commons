package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalCreateImagePostRequest;
import java.lang.String;

public interface CreateImagePostRequest {
  static Builder builder() {
    return new CreateImagePostRequest.Builder();
  }

  static CreateImagePostRequest.Builder from(CreateImagePostRequest value) {
    if(value != null) {
      return new CreateImagePostRequest.Builder()
          .fromImage(value.fromImage())
          .repo(value.repo())
          .tag(value.tag())
          ;
    }
    else {
      return null;
    }
  }

  String fromImage();

  String repo();

  String tag();

  CreateImagePostRequest withFromImage(String value);

  CreateImagePostRequest withRepo(String value);

  CreateImagePostRequest withTag(String value);

  int hashCode();

  CreateImagePostRequest changed(CreateImagePostRequest.Changer changer);

  OptionalCreateImagePostRequest opt();

  class Builder {
    private String fromImage;

    private String repo;

    private String tag;

    public CreateImagePostRequest build() {
      return new CreateImagePostRequestImpl(this.fromImage, this.repo, this.tag);
    }

    public CreateImagePostRequest.Builder fromImage(String fromImage) {
      this.fromImage = fromImage;
      return this;
    }

    public CreateImagePostRequest.Builder repo(String repo) {
      this.repo = repo;
      return this;
    }

    public CreateImagePostRequest.Builder tag(String tag) {
      this.tag = tag;
      return this;
    }
  }

  interface Changer {
    CreateImagePostRequest.Builder configure(CreateImagePostRequest.Builder builder);
  }
}
