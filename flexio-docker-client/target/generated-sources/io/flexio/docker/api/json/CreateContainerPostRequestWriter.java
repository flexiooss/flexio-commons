package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.CreateContainerPostRequest;
import io.flexio.docker.api.types.json.ContainerCreationDataWriter;
import java.io.IOException;

public class CreateContainerPostRequestWriter {
  public void write(JsonGenerator generator, CreateContainerPostRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("name");
    if(value.name() != null) {
      generator.writeString(value.name());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("payload");
    if(value.payload() != null) {
      new ContainerCreationDataWriter().write(generator, value.payload());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, CreateContainerPostRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(CreateContainerPostRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
