package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.WaitForPostRequest;
import java.io.IOException;

public class WaitForPostRequestWriter {
  public void write(JsonGenerator generator, WaitForPostRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("condition");
    if(value.condition() != null) {
      generator.writeString(value.condition());
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

  public void writeArray(JsonGenerator generator, WaitForPostRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(WaitForPostRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
