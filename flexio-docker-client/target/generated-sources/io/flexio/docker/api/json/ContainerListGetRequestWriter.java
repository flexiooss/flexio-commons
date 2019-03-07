package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.ContainerListGetRequest;
import java.io.IOException;

public class ContainerListGetRequestWriter {
  public void write(JsonGenerator generator, ContainerListGetRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("all");
    if(value.all() != null) {
      generator.writeBoolean(value.all());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("limit");
    if(value.limit() != null) {
      generator.writeNumber(value.limit());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("size");
    if(value.size() != null) {
      generator.writeNumber(value.size());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("filters");
    if(value.filters() != null) {
      generator.writeString(value.filters());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, ContainerListGetRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(ContainerListGetRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
