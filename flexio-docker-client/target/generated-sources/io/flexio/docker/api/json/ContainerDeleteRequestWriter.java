package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.ContainerDeleteRequest;
import java.io.IOException;

public class ContainerDeleteRequestWriter {
  public void write(JsonGenerator generator, ContainerDeleteRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("v");
    if(value.v() != null) {
      generator.writeBoolean(value.v());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("force");
    if(value.force() != null) {
      generator.writeBoolean(value.force());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("link");
    if(value.link() != null) {
      generator.writeBoolean(value.link());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("containerId");
    if(value.containerId() != null) {
      generator.writeString(value.containerId());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, ContainerDeleteRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(ContainerDeleteRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
