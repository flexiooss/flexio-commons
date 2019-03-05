package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalCreateImagePostRequest;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class CreateImagePostRequestImpl implements CreateImagePostRequest {
  private final String fromImage;

  private final String repo;

  private final String tag;

  CreateImagePostRequestImpl(String fromImage, String repo, String tag) {
    this.fromImage = fromImage;
    this.repo = repo;
    this.tag = tag;
  }

  public String fromImage() {
    return this.fromImage;
  }

  public String repo() {
    return this.repo;
  }

  public String tag() {
    return this.tag;
  }

  public CreateImagePostRequest withFromImage(String value) {
    return CreateImagePostRequest.from(this).fromImage(value).build();
  }

  public CreateImagePostRequest withRepo(String value) {
    return CreateImagePostRequest.from(this).repo(value).build();
  }

  public CreateImagePostRequest withTag(String value) {
    return CreateImagePostRequest.from(this).tag(value).build();
  }

  public CreateImagePostRequest changed(CreateImagePostRequest.Changer changer) {
    return changer.configure(CreateImagePostRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreateImagePostRequestImpl that = (CreateImagePostRequestImpl) o;
        return Objects.equals(this.fromImage, that.fromImage) && 
        Objects.equals(this.repo, that.repo) && 
        Objects.equals(this.tag, that.tag);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.fromImage, this.repo, this.tag});
  }

  @Override
  public String toString() {
    return "CreateImagePostRequest{" +
        "fromImage=" + Objects.toString(this.fromImage) +
        ", " + "repo=" + Objects.toString(this.repo) +
        ", " + "tag=" + Objects.toString(this.tag) +
        '}';
  }

  public OptionalCreateImagePostRequest opt() {
    return OptionalCreateImagePostRequest.of(this);
  }
}
