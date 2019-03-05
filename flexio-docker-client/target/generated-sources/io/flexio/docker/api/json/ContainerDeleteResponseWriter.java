package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.ContainerDeleteResponse;
import io.flexio.docker.api.containerdeleteresponse.json.Status204Writer;
import io.flexio.docker.api.containerdeleteresponse.json.Status400Writer;
import java.io.IOException;

public class ContainerDeleteResponseWriter {
  public void write(JsonGenerator generator, ContainerDeleteResponse value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("status204");
    if(value.status204() != null) {
      new Status204Writer().write(generator, value.status204());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("status400");
    if(value.status400() != null) {
      new Status400Writer().write(generator, value.status400());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, ContainerDeleteResponse[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(ContainerDeleteResponse value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
