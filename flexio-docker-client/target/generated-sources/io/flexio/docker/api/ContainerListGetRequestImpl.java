package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalContainerListGetRequest;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ContainerListGetRequestImpl implements ContainerListGetRequest {
  private final Boolean all;

  private final Long limit;

  private final Long size;

  private final String filters;

  ContainerListGetRequestImpl(Boolean all, Long limit, Long size, String filters) {
    this.all = all;
    this.limit = limit;
    this.size = size;
    this.filters = filters;
  }

  public Boolean all() {
    return this.all;
  }

  public Long limit() {
    return this.limit;
  }

  public Long size() {
    return this.size;
  }

  public String filters() {
    return this.filters;
  }

  public ContainerListGetRequest withAll(Boolean value) {
    return ContainerListGetRequest.from(this).all(value).build();
  }

  public ContainerListGetRequest withLimit(Long value) {
    return ContainerListGetRequest.from(this).limit(value).build();
  }

  public ContainerListGetRequest withSize(Long value) {
    return ContainerListGetRequest.from(this).size(value).build();
  }

  public ContainerListGetRequest withFilters(String value) {
    return ContainerListGetRequest.from(this).filters(value).build();
  }

  public ContainerListGetRequest changed(ContainerListGetRequest.Changer changer) {
    return changer.configure(ContainerListGetRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainerListGetRequestImpl that = (ContainerListGetRequestImpl) o;
        return Objects.equals(this.all, that.all) && 
        Objects.equals(this.limit, that.limit) && 
        Objects.equals(this.size, that.size) && 
        Objects.equals(this.filters, that.filters);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.all, this.limit, this.size, this.filters});
  }

  @Override
  public String toString() {
    return "ContainerListGetRequest{" +
        "all=" + Objects.toString(this.all) +
        ", " + "limit=" + Objects.toString(this.limit) +
        ", " + "size=" + Objects.toString(this.size) +
        ", " + "filters=" + Objects.toString(this.filters) +
        '}';
  }

  public OptionalContainerListGetRequest opt() {
    return OptionalContainerListGetRequest.of(this);
  }
}
