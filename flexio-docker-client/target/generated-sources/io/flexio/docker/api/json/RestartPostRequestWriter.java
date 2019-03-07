package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.RestartPostRequest;
import java.io.IOException;

public class RestartPostRequestWriter {
  public void write(JsonGenerator generator, RestartPostRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("t");
    if(value.t() != null) {
      generator.writeNumber(value.t());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("containerId");
    if(value.containerId() != null) {
      generator.writeString(value.containerId());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, RestartPostRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(RestartPostRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
