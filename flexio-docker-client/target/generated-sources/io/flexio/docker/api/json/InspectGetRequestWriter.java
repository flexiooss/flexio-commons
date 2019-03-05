package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.InspectGetRequest;
import java.io.IOException;

public class InspectGetRequestWriter {
  public void write(JsonGenerator generator, InspectGetRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("containerId");
    if(value.containerId() != null) {
      generator.writeString(value.containerId());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, InspectGetRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(InspectGetRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
