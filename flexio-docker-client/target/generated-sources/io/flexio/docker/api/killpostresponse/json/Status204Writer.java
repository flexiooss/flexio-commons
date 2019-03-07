package io.flexio.docker.api.killpostresponse.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.killpostresponse.Status204;
import java.io.IOException;

public class Status204Writer {
  public void write(JsonGenerator generator, Status204 value) throws IOException {
    generator.writeStartObject();
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, Status204[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(Status204 value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
