package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalCreateContainerPostRequest;
import io.flexio.docker.api.types.ContainerCreationData;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class CreateContainerPostRequestImpl implements CreateContainerPostRequest {
  private final String name;

  private final ContainerCreationData payload;

  CreateContainerPostRequestImpl(String name, ContainerCreationData payload) {
    this.name = name;
    this.payload = payload;
  }

  public String name() {
    return this.name;
  }

  public ContainerCreationData payload() {
    return this.payload;
  }

  public CreateContainerPostRequest withName(String value) {
    return CreateContainerPostRequest.from(this).name(value).build();
  }

  public CreateContainerPostRequest withPayload(ContainerCreationData value) {
    return CreateContainerPostRequest.from(this).payload(value).build();
  }

  public CreateContainerPostRequest changed(CreateContainerPostRequest.Changer changer) {
    return changer.configure(CreateContainerPostRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreateContainerPostRequestImpl that = (CreateContainerPostRequestImpl) o;
        return Objects.equals(this.name, that.name) && 
        Objects.equals(this.payload, that.payload);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.name, this.payload});
  }

  @Override
  public String toString() {
    return "CreateContainerPostRequest{" +
        "name=" + Objects.toString(this.name) +
        ", " + "payload=" + Objects.toString(this.payload) +
        '}';
  }

  public OptionalCreateContainerPostRequest opt() {
    return OptionalCreateContainerPostRequest.of(this);
  }
}
