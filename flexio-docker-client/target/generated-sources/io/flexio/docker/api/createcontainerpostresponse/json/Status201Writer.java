package io.flexio.docker.api.createcontainerpostresponse.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.createcontainerpostresponse.Status201;
import io.flexio.docker.api.types.json.ContainerCreationResultWriter;
import java.io.IOException;

public class Status201Writer {
  public void write(JsonGenerator generator, Status201 value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("payload");
    if(value.payload() != null) {
      new ContainerCreationResultWriter().write(generator, value.payload());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, Status201[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(Status201 value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
