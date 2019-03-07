package io.flexio.commons.graylog.api.types;

import io.flexio.commons.graylog.api.types.optional.OptionalSearchResponse;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;
import org.codingmatters.value.objects.values.ObjectValue;

class SearchResponseImpl implements SearchResponse {
  private final String from;

  private final String to;

  private final ValueList<ObjectValue> messages;

  private final ValueList<String> fields;

  private final Long time;

  private final String built_query;

  private final ObjectValue decoration_stats;

  private final Long total_results;

  private final ValueList<ObjectValue> used_indices;

  private final String query;

  SearchResponseImpl(String from, String to, ValueList<ObjectValue> messages, ValueList<String> fields, Long time, String built_query, ObjectValue decoration_stats, Long total_results, ValueList<ObjectValue> used_indices, String query) {
    this.from = from;
    this.to = to;
    this.messages = messages;
    this.fields = fields;
    this.time = time;
    this.built_query = built_query;
    this.decoration_stats = decoration_stats;
    this.total_results = total_results;
    this.used_indices = used_indices;
    this.query = query;
  }

  public String from() {
    return this.from;
  }

  public String to() {
    return this.to;
  }

  public ValueList<ObjectValue> messages() {
    return this.messages;
  }

  public ValueList<String> fields() {
    return this.fields;
  }

  public Long time() {
    return this.time;
  }

  public String built_query() {
    return this.built_query;
  }

  public ObjectValue decoration_stats() {
    return this.decoration_stats;
  }

  public Long total_results() {
    return this.total_results;
  }

  public ValueList<ObjectValue> used_indices() {
    return this.used_indices;
  }

  public String query() {
    return this.query;
  }

  public SearchResponse withFrom(String value) {
    return SearchResponse.from(this).from(value).build();
  }

  public SearchResponse withTo(String value) {
    return SearchResponse.from(this).to(value).build();
  }

  public SearchResponse withMessages(ValueList<ObjectValue> value) {
    return SearchResponse.from(this).messages(value).build();
  }

  public SearchResponse withFields(ValueList<String> value) {
    return SearchResponse.from(this).fields(value).build();
  }

  public SearchResponse withTime(Long value) {
    return SearchResponse.from(this).time(value).build();
  }

  public SearchResponse withBuilt_query(String value) {
    return SearchResponse.from(this).built_query(value).build();
  }

  public SearchResponse withDecoration_stats(ObjectValue value) {
    return SearchResponse.from(this).decoration_stats(value).build();
  }

  public SearchResponse withTotal_results(Long value) {
    return SearchResponse.from(this).total_results(value).build();
  }

  public SearchResponse withUsed_indices(ValueList<ObjectValue> value) {
    return SearchResponse.from(this).used_indices(value).build();
  }

  public SearchResponse withQuery(String value) {
    return SearchResponse.from(this).query(value).build();
  }

  public SearchResponse changed(SearchResponse.Changer changer) {
    return changer.configure(SearchResponse.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SearchResponseImpl that = (SearchResponseImpl) o;
        return Objects.equals(this.from, that.from) && 
        Objects.equals(this.to, that.to) && 
        Objects.equals(this.messages, that.messages) && 
        Objects.equals(this.fields, that.fields) && 
        Objects.equals(this.time, that.time) && 
        Objects.equals(this.built_query, that.built_query) && 
        Objects.equals(this.decoration_stats, that.decoration_stats) && 
        Objects.equals(this.total_results, that.total_results) && 
        Objects.equals(this.used_indices, that.used_indices) && 
        Objects.equals(this.query, that.query);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.from, this.to, this.messages, this.fields, this.time, this.built_query, this.decoration_stats, this.total_results, this.used_indices, this.query});
  }

  @Override
  public String toString() {
    return "SearchResponse{" +
        "from=" + Objects.toString(this.from) +
        ", " + "to=" + Objects.toString(this.to) +
        ", " + "messages=" + Objects.toString(this.messages) +
        ", " + "fields=" + Objects.toString(this.fields) +
        ", " + "time=" + Objects.toString(this.time) +
        ", " + "built_query=" + Objects.toString(this.built_query) +
        ", " + "decoration_stats=" + Objects.toString(this.decoration_stats) +
        ", " + "total_results=" + Objects.toString(this.total_results) +
        ", " + "used_indices=" + Objects.toString(this.used_indices) +
        ", " + "query=" + Objects.toString(this.query) +
        '}';
  }

  public OptionalSearchResponse opt() {
    return OptionalSearchResponse.of(this);
  }
}
