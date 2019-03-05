package io.flexio.docker.api.types.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.types.ContainerCreationResult;
import java.io.IOException;

public class ContainerCreationResultWriter {
  public void write(JsonGenerator generator, ContainerCreationResult value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("Id");
    if(value.id() != null) {
      generator.writeString(value.id());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("Warning");
    if(value.warning() != null) {
      generator.writeString(value.warning());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, ContainerCreationResult[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(ContainerCreationResult value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
