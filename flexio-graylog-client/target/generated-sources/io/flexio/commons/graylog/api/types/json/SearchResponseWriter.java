package io.flexio.commons.graylog.api.types.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.commons.graylog.api.types.SearchResponse;
import java.io.IOException;
import java.lang.String;
import org.codingmatters.value.objects.values.ObjectValue;
import org.codingmatters.value.objects.values.json.ObjectValueWriter;

public class SearchResponseWriter {
  public void write(JsonGenerator generator, SearchResponse value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("from");
    if(value.from() != null) {
      generator.writeString(value.from());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("to");
    if(value.to() != null) {
      generator.writeString(value.to());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("messages");
    if(value.messages() != null) {
      generator.writeStartArray();
      for (ObjectValue element : value.messages()) {
        if(element != null) {
          new ObjectValueWriter().write(generator, element);
        } else {
          generator.writeNull();
        }
      }
      generator.writeEndArray();
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("fields");
    if(value.fields() != null) {
      generator.writeStartArray();
      for (String element : value.fields()) {
        if(element != null) {
          generator.writeString(element);
        } else {
          generator.writeNull();
        }
      }
      generator.writeEndArray();
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("time");
    if(value.time() != null) {
      generator.writeNumber(value.time());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("built_query");
    if(value.built_query() != null) {
      generator.writeString(value.built_query());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("decoration_stats");
    if(value.decoration_stats() != null) {
      new ObjectValueWriter().write(generator, value.decoration_stats());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("total_results");
    if(value.total_results() != null) {
      generator.writeNumber(value.total_results());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("used_indices");
    if(value.used_indices() != null) {
      generator.writeStartArray();
      for (ObjectValue element : value.used_indices()) {
        if(element != null) {
          new ObjectValueWriter().write(generator, element);
        } else {
          generator.writeNull();
        }
      }
      generator.writeEndArray();
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("query");
    if(value.query() != null) {
      generator.writeString(value.query());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, SearchResponse[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(SearchResponse value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
