package io.flexio.docker.api.createcontainerpostresponse.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.createcontainerpostresponse.Status409;
import io.flexio.docker.api.types.json.ErrorWriter;
import java.io.IOException;

public class Status409Writer {
  public void write(JsonGenerator generator, Status409 value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("payload");
    if(value.payload() != null) {
      new ErrorWriter().write(generator, value.payload());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, Status409[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(Status409 value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
