package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.StopPostRequest;
import java.io.IOException;

public class StopPostRequestWriter {
  public void write(JsonGenerator generator, StopPostRequest value) throws IOException {
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

  public void writeArray(JsonGenerator generator, StopPostRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(StopPostRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
