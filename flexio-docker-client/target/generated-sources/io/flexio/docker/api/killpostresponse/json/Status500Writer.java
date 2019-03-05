package io.flexio.docker.api.killpostresponse.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.killpostresponse.Status500;
import io.flexio.docker.api.types.json.ErrorWriter;
import java.io.IOException;

public class Status500Writer {
  public void write(JsonGenerator generator, Status500 value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("payload");
    if(value.payload() != null) {
      new ErrorWriter().write(generator, value.payload());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, Status500[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(Status500 value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
