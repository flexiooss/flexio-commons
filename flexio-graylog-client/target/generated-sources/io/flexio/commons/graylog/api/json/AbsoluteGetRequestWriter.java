package io.flexio.commons.graylog.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.commons.graylog.api.AbsoluteGetRequest;
import java.io.IOException;
import java.lang.String;

public class AbsoluteGetRequestWriter {
  public void write(JsonGenerator generator, AbsoluteGetRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("query");
    if(value.query() != null) {
      generator.writeString(value.query());
    } else {
      generator.writeNull();
    }
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
    generator.writeFieldName("limit");
    if(value.limit() != null) {
      generator.writeNumber(value.limit());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("offset");
    if(value.offset() != null) {
      generator.writeNumber(value.offset());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("filter");
    if(value.filter() != null) {
      generator.writeString(value.filter());
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
    generator.writeFieldName("sort");
    if(value.sort() != null) {
      generator.writeString(value.sort());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("decorate");
    if(value.decorate() != null) {
      generator.writeBoolean(value.decorate());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("accept");
    if(value.accept() != null) {
      generator.writeString(value.accept());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("authorization");
    if(value.authorization() != null) {
      generator.writeString(value.authorization());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, AbsoluteGetRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(AbsoluteGetRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
