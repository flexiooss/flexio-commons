package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.KillPostRequest;
import java.io.IOException;

public class KillPostRequestWriter {
  public void write(JsonGenerator generator, KillPostRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("signal");
    if(value.signal() != null) {
      generator.writeString(value.signal());
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

  public void writeArray(JsonGenerator generator, KillPostRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(KillPostRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
