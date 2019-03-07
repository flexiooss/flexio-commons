package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalContainerDeleteRequest;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ContainerDeleteRequestImpl implements ContainerDeleteRequest {
  private final Boolean v;

  private final Boolean force;

  private final Boolean link;

  private final String containerId;

  ContainerDeleteRequestImpl(Boolean v, Boolean force, Boolean link, String containerId) {
    this.v = v;
    this.force = force;
    this.link = link;
    this.containerId = containerId;
  }

  public Boolean v() {
    return this.v;
  }

  public Boolean force() {
    return this.force;
  }

  public Boolean link() {
    return this.link;
  }

  public String containerId() {
    return this.containerId;
  }

  public ContainerDeleteRequest withV(Boolean value) {
    return ContainerDeleteRequest.from(this).v(value).build();
  }

  public ContainerDeleteRequest withForce(Boolean value) {
    return ContainerDeleteRequest.from(this).force(value).build();
  }

  public ContainerDeleteRequest withLink(Boolean value) {
    return ContainerDeleteRequest.from(this).link(value).build();
  }

  public ContainerDeleteRequest withContainerId(String value) {
    return ContainerDeleteRequest.from(this).containerId(value).build();
  }

  public ContainerDeleteRequest changed(ContainerDeleteRequest.Changer changer) {
    return changer.configure(ContainerDeleteRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainerDeleteRequestImpl that = (ContainerDeleteRequestImpl) o;
        return Objects.equals(this.v, that.v) && 
        Objects.equals(this.force, that.force) && 
        Objects.equals(this.link, that.link) && 
        Objects.equals(this.containerId, that.containerId);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.v, this.force, this.link, this.containerId});
  }

  @Override
  public String toString() {
    return "ContainerDeleteRequest{" +
        "v=" + Objects.toString(this.v) +
        ", " + "force=" + Objects.toString(this.force) +
        ", " + "link=" + Objects.toString(this.link) +
        ", " + "containerId=" + Objects.toString(this.containerId) +
        '}';
  }

  public OptionalContainerDeleteRequest opt() {
    return OptionalContainerDeleteRequest.of(this);
  }
}
