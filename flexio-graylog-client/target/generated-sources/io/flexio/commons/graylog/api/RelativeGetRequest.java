package io.flexio.commons.graylog.api;

import io.flexio.commons.graylog.api.optional.OptionalRelativeGetRequest;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;

public interface RelativeGetRequest {
  static Builder builder() {
    return new RelativeGetRequest.Builder();
  }

  static RelativeGetRequest.Builder from(RelativeGetRequest value) {
    if(value != null) {
      return new RelativeGetRequest.Builder()
          .query(value.query())
          .range(value.range())
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

  String range();

  Long limit();

  Long offset();

  String filter();

  ValueList<String> fields();

  String sort();

  Boolean decorate();

  String accept();

  String authorization();

  RelativeGetRequest withQuery(String value);

  RelativeGetRequest withRange(String value);

  RelativeGetRequest withLimit(Long value);

  RelativeGetRequest withOffset(Long value);

  RelativeGetRequest withFilter(String value);

  RelativeGetRequest withFields(ValueList<String> value);

  RelativeGetRequest withSort(String value);

  RelativeGetRequest withDecorate(Boolean value);

  RelativeGetRequest withAccept(String value);

  RelativeGetRequest withAuthorization(String value);

  int hashCode();

  RelativeGetRequest changed(RelativeGetRequest.Changer changer);

  OptionalRelativeGetRequest opt();

  class Builder {
    private String query;

    private String range;

    private Long limit;

    private Long offset;

    private String filter;

    private ValueList<String> fields;

    private String sort;

    private Boolean decorate;

    private String accept;

    private String authorization;

    public RelativeGetRequest build() {
      return new RelativeGetRequestImpl(this.query, this.range, this.limit, this.offset, this.filter, this.fields, this.sort, this.decorate, this.accept, this.authorization);
    }

    public RelativeGetRequest.Builder query(String query) {
      this.query = query;
      return this;
    }

    public RelativeGetRequest.Builder range(String range) {
      this.range = range;
      return this;
    }

    public RelativeGetRequest.Builder limit(Long limit) {
      this.limit = limit;
      return this;
    }

    public RelativeGetRequest.Builder offset(Long offset) {
      this.offset = offset;
      return this;
    }

    public RelativeGetRequest.Builder filter(String filter) {
      this.filter = filter;
      return this;
    }

    public RelativeGetRequest.Builder fields() {
      this.fields = null;
      return this;
    }

    public RelativeGetRequest.Builder fields(String... fields) {
      this.fields = fields != null ? new ValueList.Builder<String>().with(fields).build() : null;
      return this;
    }

    public RelativeGetRequest.Builder fields(ValueList<String> fields) {
      this.fields = fields;
      return this;
    }

    public RelativeGetRequest.Builder fields(Collection<String> fields) {
      this.fields = fields != null ? new ValueList.Builder<String>().with(fields).build() : null;
      return this;
    }

    public RelativeGetRequest.Builder sort(String sort) {
      this.sort = sort;
      return this;
    }

    public RelativeGetRequest.Builder decorate(Boolean decorate) {
      this.decorate = decorate;
      return this;
    }

    public RelativeGetRequest.Builder accept(String accept) {
      this.accept = accept;
      return this;
    }

    public RelativeGetRequest.Builder authorization(String authorization) {
      this.authorization = authorization;
      return this;
    }
  }

  interface Changer {
    RelativeGetRequest.Builder configure(RelativeGetRequest.Builder builder);
  }
}
