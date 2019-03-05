package io.flexio.docker.api.killpostresponse.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.killpostresponse.Status404;
import io.flexio.docker.api.types.json.ErrorWriter;
import java.io.IOException;

public class Status404Writer {
  public void write(JsonGenerator generator, Status404 value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("payload");
    if(value.payload() != null) {
      new ErrorWriter().write(generator, value.payload());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, Status404[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(Status404 value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
