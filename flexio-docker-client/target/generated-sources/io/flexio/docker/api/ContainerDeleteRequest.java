package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalContainerDeleteRequest;
import java.lang.Boolean;
import java.lang.String;

public interface ContainerDeleteRequest {
  static Builder builder() {
    return new ContainerDeleteRequest.Builder();
  }

  static ContainerDeleteRequest.Builder from(ContainerDeleteRequest value) {
    if(value != null) {
      return new ContainerDeleteRequest.Builder()
          .v(value.v())
          .force(value.force())
          .link(value.link())
          .containerId(value.containerId())
          ;
    }
    else {
      return null;
    }
  }

  Boolean v();

  Boolean force();

  Boolean link();

  String containerId();

  ContainerDeleteRequest withV(Boolean value);

  ContainerDeleteRequest withForce(Boolean value);

  ContainerDeleteRequest withLink(Boolean value);

  ContainerDeleteRequest withContainerId(String value);

  int hashCode();

  ContainerDeleteRequest changed(ContainerDeleteRequest.Changer changer);

  OptionalContainerDeleteRequest opt();

  class Builder {
    private Boolean v;

    private Boolean force;

    private Boolean link;

    private String containerId;

    public ContainerDeleteRequest build() {
      return new ContainerDeleteRequestImpl(this.v, this.force, this.link, this.containerId);
    }

    public ContainerDeleteRequest.Builder v(Boolean v) {
      this.v = v;
      return this;
    }

    public ContainerDeleteRequest.Builder force(Boolean force) {
      this.force = force;
      return this;
    }

    public ContainerDeleteRequest.Builder link(Boolean link) {
      this.link = link;
      return this;
    }

    public ContainerDeleteRequest.Builder containerId(String containerId) {
      this.containerId = containerId;
      return this;
    }
  }

  interface Changer {
    ContainerDeleteRequest.Builder configure(ContainerDeleteRequest.Builder builder);
  }
}
