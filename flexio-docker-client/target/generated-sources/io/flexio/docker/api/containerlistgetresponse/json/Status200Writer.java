package io.flexio.docker.api.containerlistgetresponse.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.containerlistgetresponse.Status200;
import io.flexio.docker.api.types.ContainerInList;
import io.flexio.docker.api.types.json.ContainerInListWriter;
import java.io.IOException;

public class Status200Writer {
  public void write(JsonGenerator generator, Status200 value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("payload");
    if(value.payload() != null) {
      generator.writeStartArray();
      for (ContainerInList element : value.payload()) {
        if(element != null) {
          new ContainerInListWriter().write(generator, element);
        } else {
          generator.writeNull();
        }
      }
      generator.writeEndArray();
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
