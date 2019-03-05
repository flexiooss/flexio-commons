package io.flexio.docker.api.types.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.types.Error;
import java.io.IOException;

public class ErrorWriter {
  public void write(JsonGenerator generator, Error value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("message");
    if(value.message() != null) {
      generator.writeString(value.message());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, Error[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(Error value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
