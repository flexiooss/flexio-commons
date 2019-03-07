package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalContainerListGetRequest;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;

public interface ContainerListGetRequest {
  static Builder builder() {
    return new ContainerListGetRequest.Builder();
  }

  static ContainerListGetRequest.Builder from(ContainerListGetRequest value) {
    if(value != null) {
      return new ContainerListGetRequest.Builder()
          .all(value.all())
          .limit(value.limit())
          .size(value.size())
          .filters(value.filters())
          ;
    }
    else {
      return null;
    }
  }

  Boolean all();

  Long limit();

  Long size();

  String filters();

  ContainerListGetRequest withAll(Boolean value);

  ContainerListGetRequest withLimit(Long value);

  ContainerListGetRequest withSize(Long value);

  ContainerListGetRequest withFilters(String value);

  int hashCode();

  ContainerListGetRequest changed(ContainerListGetRequest.Changer changer);

  OptionalContainerListGetRequest opt();

  class Builder {
    private Boolean all;

    private Long limit;

    private Long size;

    private String filters;

    public ContainerListGetRequest build() {
      return new ContainerListGetRequestImpl(this.all, this.limit, this.size, this.filters);
    }

    public ContainerListGetRequest.Builder all(Boolean all) {
      this.all = all;
      return this;
    }

    public ContainerListGetRequest.Builder limit(Long limit) {
      this.limit = limit;
      return this;
    }

    public ContainerListGetRequest.Builder size(Long size) {
      this.size = size;
      return this;
    }

    public ContainerListGetRequest.Builder filters(String filters) {
      this.filters = filters;
      return this;
    }
  }

  interface Changer {
    ContainerListGetRequest.Builder configure(ContainerListGetRequest.Builder builder);
  }
}
