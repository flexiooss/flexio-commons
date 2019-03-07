package io.flexio.docker.api.imagelistgetresponse.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.imagelistgetresponse.Status200;
import io.flexio.docker.api.types.Image;
import io.flexio.docker.api.types.json.ImageWriter;
import java.io.IOException;

public class Status200Writer {
  public void write(JsonGenerator generator, Status200 value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("payload");
    if(value.payload() != null) {
      generator.writeStartArray();
      for (Image element : value.payload()) {
        if(element != null) {
          new ImageWriter().write(generator, element);
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
