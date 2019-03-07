package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.StartPostRequest;
import java.io.IOException;

public class StartPostRequestWriter {
  public void write(JsonGenerator generator, StartPostRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("containerId");
    if(value.containerId() != null) {
      generator.writeString(value.containerId());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, StartPostRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(StartPostRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
