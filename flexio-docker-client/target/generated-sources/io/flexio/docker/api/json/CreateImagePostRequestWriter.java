package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.CreateImagePostRequest;
import java.io.IOException;

public class CreateImagePostRequestWriter {
  public void write(JsonGenerator generator, CreateImagePostRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("fromImage");
    if(value.fromImage() != null) {
      generator.writeString(value.fromImage());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("repo");
    if(value.repo() != null) {
      generator.writeString(value.repo());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("tag");
    if(value.tag() != null) {
      generator.writeString(value.tag());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, CreateImagePostRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(CreateImagePostRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
