package io.flexio.commons.graylog.api;

import io.flexio.commons.graylog.api.optional.OptionalAbsoluteGetRequest;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;

public interface AbsoluteGetRequest {
  static Builder builder() {
    return new AbsoluteGetRequest.Builder();
  }

  static AbsoluteGetRequest.Builder from(AbsoluteGetRequest value) {
    if(value != null) {
      return new AbsoluteGetRequest.Builder()
          .query(value.query())
          .from(value.from())
          .to(value.to())
          .limit(value.limit())
          .offset(value.offset())
          .filter(value.filter())
          .fields(value.fields())
          .sort(value.sort())
          .decorate(value.decorate())
          .accept(value.accept())
          .authorization(value.authorization())
          ;
    }
    else {
      return null;
    }
  }

  String query();

  String from();

  String to();

  Long limit();

  Long offset();

  String filter();

  ValueList<String> fields();

  String sort();

  Boolean decorate();

  String accept();

  String authorization();

  AbsoluteGetRequest withQuery(String value);

  AbsoluteGetRequest withFrom(String value);

  AbsoluteGetRequest withTo(String value);

  AbsoluteGetRequest withLimit(Long value);

  AbsoluteGetRequest withOffset(Long value);

  AbsoluteGetRequest withFilter(String value);

  AbsoluteGetRequest withFields(ValueList<String> value);

  AbsoluteGetRequest withSort(String value);

  AbsoluteGetRequest withDecorate(Boolean value);

  AbsoluteGetRequest withAccept(String value);

  AbsoluteGetRequest withAuthorization(String value);

  int hashCode();

  AbsoluteGetRequest changed(AbsoluteGetRequest.Changer changer);

  OptionalAbsoluteGetRequest opt();

  class Builder {
    private String query;

    private String from;

    private String to;

    private Long limit;

    private Long offset;

    private String filter;

    private ValueList<String> fields;

    private String sort;

    private Boolean decorate;

    private String accept;

    private String authorization;

    public AbsoluteGetRequest build() {
      return new AbsoluteGetRequestImpl(this.query, this.from, this.to, this.limit, this.offset, this.filter, this.fields, this.sort, this.decorate, this.accept, this.authorization);
    }

    public AbsoluteGetRequest.Builder query(String query) {
      this.query = query;
      return this;
    }

    public AbsoluteGetRequest.Builder from(String from) {
      this.from = from;
      return this;
    }

    public AbsoluteGetRequest.Builder to(String to) {
      this.to = to;
      return this;
    }

    public AbsoluteGetRequest.Builder limit(Long limit) {
      this.limit = limit;
      return this;
    }

    public AbsoluteGetRequest.Builder offset(Long offset) {
      this.offset = offset;
      return this;
    }

    public AbsoluteGetRequest.Builder filter(String filter) {
      this.filter = filter;
      return this;
    }

    public AbsoluteGetRequest.Builder fields() {
      this.fields = null;
      return this;
    }

    public AbsoluteGetRequest.Builder fields(String... fields) {
      this.fields = fields != null ? new ValueList.Builder<String>().with(fields).build() : null;
      return this;
    }

    public AbsoluteGetRequest.Builder fields(ValueList<String> fields) {
      this.fields = fields;
      return this;
    }

    public AbsoluteGetRequest.Builder fields(Collection<String> fields) {
      this.fields = fields != null ? new ValueList.Builder<String>().with(fields).build() : null;
      return this;
    }

    public AbsoluteGetRequest.Builder sort(String sort) {
      this.sort = sort;
      return this;
    }

    public AbsoluteGetRequest.Builder decorate(Boolean decorate) {
      this.decorate = decorate;
      return this;
    }

    public AbsoluteGetRequest.Builder accept(String accept) {
      this.accept = accept;
      return this;
    }

    public AbsoluteGetRequest.Builder authorization(String authorization) {
      this.authorization = authorization;
      return this;
    }
  }

  interface Changer {
    AbsoluteGetRequest.Builder configure(AbsoluteGetRequest.Builder builder);
  }
}
