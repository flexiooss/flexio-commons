package io.flexio.commons.graylog.api;

import io.flexio.commons.graylog.api.optional.OptionalRelativeGetRequest;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class RelativeGetRequestImpl implements RelativeGetRequest {
  private final String query;

  private final String range;

  private final Long limit;

  private final Long offset;

  private final String filter;

  private final ValueList<String> fields;

  private final String sort;

  private final Boolean decorate;

  private final String accept;

  private final String authorization;

  RelativeGetRequestImpl(String query, String range, Long limit, Long offset, String filter, ValueList<String> fields, String sort, Boolean decorate, String accept, String authorization) {
    this.query = query;
    this.range = range;
    this.limit = limit;
    this.offset = offset;
    this.filter = filter;
    this.fields = fields;
    this.sort = sort;
    this.decorate = decorate;
    this.accept = accept;
    this.authorization = authorization;
  }

  public String query() {
    return this.query;
  }

  public String range() {
    return this.range;
  }

  public Long limit() {
    return this.limit;
  }

  public Long offset() {
    return this.offset;
  }

  public String filter() {
    return this.filter;
  }

  public ValueList<String> fields() {
    return this.fields;
  }

  public String sort() {
    return this.sort;
  }

  public Boolean decorate() {
    return this.decorate;
  }

  public String accept() {
    return this.accept;
  }

  public String authorization() {
    return this.authorization;
  }

  public RelativeGetRequest withQuery(String value) {
    return RelativeGetRequest.from(this).query(value).build();
  }

  public RelativeGetRequest withRange(String value) {
    return RelativeGetRequest.from(this).range(value).build();
  }

  public RelativeGetRequest withLimit(Long value) {
    return RelativeGetRequest.from(this).limit(value).build();
  }

  public RelativeGetRequest withOffset(Long value) {
    return RelativeGetRequest.from(this).offset(value).build();
  }

  public RelativeGetRequest withFilter(String value) {
    return RelativeGetRequest.from(this).filter(value).build();
  }

  public RelativeGetRequest withFields(ValueList<String> value) {
    return RelativeGetRequest.from(this).fields(value).build();
  }

  public RelativeGetRequest withSort(String value) {
    return RelativeGetRequest.from(this).sort(value).build();
  }

  public RelativeGetRequest withDecorate(Boolean value) {
    return RelativeGetRequest.from(this).decorate(value).build();
  }

  public RelativeGetRequest withAccept(String value) {
    return RelativeGetRequest.from(this).accept(value).build();
  }

  public RelativeGetRequest withAuthorization(String value) {
    return RelativeGetRequest.from(this).authorization(value).build();
  }

  public RelativeGetRequest changed(RelativeGetRequest.Changer changer) {
    return changer.configure(RelativeGetRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RelativeGetRequestImpl that = (RelativeGetRequestImpl) o;
        return Objects.equals(this.query, that.query) && 
        Objects.equals(this.range, that.range) && 
        Objects.equals(this.limit, that.limit) && 
        Objects.equals(this.offset, that.offset) && 
        Objects.equals(this.filter, that.filter) && 
        Objects.equals(this.fields, that.fields) && 
        Objects.equals(this.sort, that.sort) && 
        Objects.equals(this.decorate, that.decorate) && 
        Objects.equals(this.accept, that.accept) && 
        Objects.equals(this.authorization, that.authorization);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.query, this.range, this.limit, this.offset, this.filter, this.fields, this.sort, this.decorate, this.accept, this.authorization});
  }

  @Override
  public String toString() {
    return "RelativeGetRequest{" +
        "query=" + Objects.toString(this.query) +
        ", " + "range=" + Objects.toString(this.range) +
        ", " + "limit=" + Objects.toString(this.limit) +
        ", " + "offset=" + Objects.toString(this.offset) +
        ", " + "filter=" + Objects.toString(this.filter) +
        ", " + "fields=" + Objects.toString(this.fields) +
        ", " + "sort=" + Objects.toString(this.sort) +
        ", " + "decorate=" + Objects.toString(this.decorate) +
        ", " + "accept=" + Objects.toString(this.accept) +
        ", " + "authorization=" + Objects.toString(this.authorization) +
        '}';
  }

  public OptionalRelativeGetRequest opt() {
    return OptionalRelativeGetRequest.of(this);
  }
}
