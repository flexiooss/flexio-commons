package io.flexio.commons.graylog.api;

import io.flexio.commons.graylog.api.optional.OptionalAbsoluteGetRequest;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class AbsoluteGetRequestImpl implements AbsoluteGetRequest {
  private final String query;

  private final String from;

  private final String to;

  private final Long limit;

  private final Long offset;

  private final String filter;

  private final ValueList<String> fields;

  private final String sort;

  private final Boolean decorate;

  private final String accept;

  private final String authorization;

  AbsoluteGetRequestImpl(String query, String from, String to, Long limit, Long offset, String filter, ValueList<String> fields, String sort, Boolean decorate, String accept, String authorization) {
    this.query = query;
    this.from = from;
    this.to = to;
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

  public String from() {
    return this.from;
  }

  public String to() {
    return this.to;
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

  public AbsoluteGetRequest withQuery(String value) {
    return AbsoluteGetRequest.from(this).query(value).build();
  }

  public AbsoluteGetRequest withFrom(String value) {
    return AbsoluteGetRequest.from(this).from(value).build();
  }

  public AbsoluteGetRequest withTo(String value) {
    return AbsoluteGetRequest.from(this).to(value).build();
  }

  public AbsoluteGetRequest withLimit(Long value) {
    return AbsoluteGetRequest.from(this).limit(value).build();
  }

  public AbsoluteGetRequest withOffset(Long value) {
    return AbsoluteGetRequest.from(this).offset(value).build();
  }

  public AbsoluteGetRequest withFilter(String value) {
    return AbsoluteGetRequest.from(this).filter(value).build();
  }

  public AbsoluteGetRequest withFields(ValueList<String> value) {
    return AbsoluteGetRequest.from(this).fields(value).build();
  }

  public AbsoluteGetRequest withSort(String value) {
    return AbsoluteGetRequest.from(this).sort(value).build();
  }

  public AbsoluteGetRequest withDecorate(Boolean value) {
    return AbsoluteGetRequest.from(this).decorate(value).build();
  }

  public AbsoluteGetRequest withAccept(String value) {
    return AbsoluteGetRequest.from(this).accept(value).build();
  }

  public AbsoluteGetRequest withAuthorization(String value) {
    return AbsoluteGetRequest.from(this).authorization(value).build();
  }

  public AbsoluteGetRequest changed(AbsoluteGetRequest.Changer changer) {
    return changer.configure(AbsoluteGetRequest.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AbsoluteGetRequestImpl that = (AbsoluteGetRequestImpl) o;
        return Objects.equals(this.query, that.query) && 
        Objects.equals(this.from, that.from) && 
        Objects.equals(this.to, that.to) && 
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
    return Arrays.deepHashCode(new Object[]{this.query, this.from, this.to, this.limit, this.offset, this.filter, this.fields, this.sort, this.decorate, this.accept, this.authorization});
  }

  @Override
  public String toString() {
    return "AbsoluteGetRequest{" +
        "query=" + Objects.toString(this.query) +
        ", " + "from=" + Objects.toString(this.from) +
        ", " + "to=" + Objects.toString(this.to) +
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

  public OptionalAbsoluteGetRequest opt() {
    return OptionalAbsoluteGetRequest.of(this);
  }
}
