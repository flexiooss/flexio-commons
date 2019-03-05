package io.flexio.commons.graylog.api.types;

import io.flexio.commons.graylog.api.types.optional.OptionalSearchResponse;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;
import org.codingmatters.value.objects.values.ObjectValue;

public interface SearchResponse {
  static Builder builder() {
    return new SearchResponse.Builder();
  }

  static SearchResponse.Builder from(SearchResponse value) {
    if(value != null) {
      return new SearchResponse.Builder()
          .from(value.from())
          .to(value.to())
          .messages(value.messages())
          .fields(value.fields())
          .time(value.time())
          .built_query(value.built_query())
          .decoration_stats(value.decoration_stats())
          .total_results(value.total_results())
          .used_indices(value.used_indices())
          .query(value.query())
          ;
    }
    else {
      return null;
    }
  }

  String from();

  String to();

  ValueList<ObjectValue> messages();

  ValueList<String> fields();

  Long time();

  String built_query();

  ObjectValue decoration_stats();

  Long total_results();

  ValueList<ObjectValue> used_indices();

  String query();

  SearchResponse withFrom(String value);

  SearchResponse withTo(String value);

  SearchResponse withMessages(ValueList<ObjectValue> value);

  SearchResponse withFields(ValueList<String> value);

  SearchResponse withTime(Long value);

  SearchResponse withBuilt_query(String value);

  SearchResponse withDecoration_stats(ObjectValue value);

  SearchResponse withTotal_results(Long value);

  SearchResponse withUsed_indices(ValueList<ObjectValue> value);

  SearchResponse withQuery(String value);

  int hashCode();

  SearchResponse changed(SearchResponse.Changer changer);

  OptionalSearchResponse opt();

  class Builder {
    private String from;

    private String to;

    private ValueList<ObjectValue> messages;

    private ValueList<String> fields;

    private Long time;

    private String built_query;

    private ObjectValue decoration_stats;

    private Long total_results;

    private ValueList<ObjectValue> used_indices;

    private String query;

    public SearchResponse build() {
      return new SearchResponseImpl(this.from, this.to, this.messages, this.fields, this.time, this.built_query, this.decoration_stats, this.total_results, this.used_indices, this.query);
    }

    public SearchResponse.Builder from(String from) {
      this.from = from;
      return this;
    }

    public SearchResponse.Builder to(String to) {
      this.to = to;
      return this;
    }

    public SearchResponse.Builder messages() {
      this.messages = null;
      return this;
    }

    public SearchResponse.Builder messages(ObjectValue... messages) {
      this.messages = messages != null ? new ValueList.Builder<ObjectValue>().with(messages).build() : null;
      return this;
    }

    public SearchResponse.Builder messages(ValueList<ObjectValue> messages) {
      this.messages = messages;
      return this;
    }

    public SearchResponse.Builder messages(Collection<ObjectValue> messages) {
      this.messages = messages != null ? new ValueList.Builder<ObjectValue>().with(messages).build() : null;
      return this;
    }

    public SearchResponse.Builder messages(Consumer<ObjectValue.Builder>... messagesElements) {
      if(messagesElements != null) {
        LinkedList<ObjectValue> elements = new LinkedList<ObjectValue>();
        for(Consumer<ObjectValue.Builder> messages : messagesElements) {
          ObjectValue.Builder builder = ObjectValue.builder();
          messages.accept(builder);
          elements.add(builder.build());
        }
        this.messages(elements.toArray(new ObjectValue[elements.size()]));
      }
      return this;
    }

    public SearchResponse.Builder fields() {
      this.fields = null;
      return this;
    }

    public SearchResponse.Builder fields(String... fields) {
      this.fields = fields != null ? new ValueList.Builder<String>().with(fields).build() : null;
      return this;
    }

    public SearchResponse.Builder fields(ValueList<String> fields) {
      this.fields = fields;
      return this;
    }

    public SearchResponse.Builder fields(Collection<String> fields) {
      this.fields = fields != null ? new ValueList.Builder<String>().with(fields).build() : null;
      return this;
    }

    public SearchResponse.Builder time(Long time) {
      this.time = time;
      return this;
    }

    public SearchResponse.Builder built_query(String built_query) {
      this.built_query = built_query;
      return this;
    }

    public SearchResponse.Builder decoration_stats(ObjectValue decoration_stats) {
      this.decoration_stats = decoration_stats;
      return this;
    }

    public SearchResponse.Builder decoration_stats(Consumer<ObjectValue.Builder> decoration_stats) {
      ObjectValue.Builder builder = ObjectValue.builder();
      decoration_stats.accept(builder);
      return this.decoration_stats(builder.build());
    }

    public SearchResponse.Builder total_results(Long total_results) {
      this.total_results = total_results;
      return this;
    }

    public SearchResponse.Builder used_indices() {
      this.used_indices = null;
      return this;
    }

    public SearchResponse.Builder used_indices(ObjectValue... used_indices) {
      this.used_indices = used_indices != null ? new ValueList.Builder<ObjectValue>().with(used_indices).build() : null;
      return this;
    }

    public SearchResponse.Builder used_indices(ValueList<ObjectValue> used_indices) {
      this.used_indices = used_indices;
      return this;
    }

    public SearchResponse.Builder used_indices(Collection<ObjectValue> used_indices) {
      this.used_indices = used_indices != null ? new ValueList.Builder<ObjectValue>().with(used_indices).build() : null;
      return this;
    }

    public SearchResponse.Builder used_indices(Consumer<ObjectValue.Builder>... used_indicesElements) {
      if(used_indicesElements != null) {
        LinkedList<ObjectValue> elements = new LinkedList<ObjectValue>();
        for(Consumer<ObjectValue.Builder> used_indices : used_indicesElements) {
          ObjectValue.Builder builder = ObjectValue.builder();
          used_indices.accept(builder);
          elements.add(builder.build());
        }
        this.used_indices(elements.toArray(new ObjectValue[elements.size()]));
      }
      return this;
    }

    public SearchResponse.Builder query(String query) {
      this.query = query;
      return this;
    }
  }

  interface Changer {
    SearchResponse.Builder configure(SearchResponse.Builder builder);
  }
}
