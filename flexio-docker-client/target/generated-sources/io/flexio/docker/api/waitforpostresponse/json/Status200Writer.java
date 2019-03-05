package io.flexio.docker.api.waitforpostresponse.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.types.json.WaitResultWriter;
import io.flexio.docker.api.waitforpostresponse.Status200;
import java.io.IOException;

public class Status200Writer {
  public void write(JsonGenerator generator, Status200 value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("payload");
    if(value.payload() != null) {
      new WaitResultWriter().write(generator, value.payload());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, Status200[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(Status200 value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
