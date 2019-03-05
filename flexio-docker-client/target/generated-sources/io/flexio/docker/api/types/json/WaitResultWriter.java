package io.flexio.docker.api.types.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.types.WaitResult;
import java.io.IOException;

public class WaitResultWriter {
  public void write(JsonGenerator generator, WaitResult value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("StatusCode");
    if(value.statusCode() != null) {
      generator.writeNumber(value.statusCode());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, WaitResult[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(WaitResult value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
