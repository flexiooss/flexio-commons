package io.flexio.commons.graylog.api.relativegetresponse;

import io.flexio.commons.graylog.api.relativegetresponse.optional.OptionalStatus200;
import io.flexio.commons.graylog.api.types.SearchResponse;
import java.util.function.Consumer;

public interface Status200 {
  static Builder builder() {
    return new Status200.Builder();
  }

  static Status200.Builder from(Status200 value) {
    if(value != null) {
      return new Status200.Builder()
          .payload(value.payload())
          ;
    }
    else {
      return null;
    }
  }

  SearchResponse payload();

  Status200 withPayload(SearchResponse value);

  int hashCode();

  Status200 changed(Status200.Changer changer);

  OptionalStatus200 opt();

  class Builder {
    private SearchResponse payload;

    public Status200 build() {
      return new Status200Impl(this.payload);
    }

    public Status200.Builder payload(SearchResponse payload) {
      this.payload = payload;
      return this;
    }

    public Status200.Builder payload(Consumer<SearchResponse.Builder> payload) {
      SearchResponse.Builder builder = SearchResponse.builder();
      payload.accept(builder);
      return this.payload(builder.build());
    }
  }

  interface Changer {
    Status200.Builder configure(Status200.Builder builder);
  }
}
