package io.flexio.docker.api.types.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.types.ContainerCreationData;
import java.io.IOException;
import java.lang.String;

public class ContainerCreationDataWriter {
  public void write(JsonGenerator generator, ContainerCreationData value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("Image");
    if(value.image() != null) {
      generator.writeString(value.image());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("Cmd");
    if(value.cmd() != null) {
      generator.writeStartArray();
      for (String element : value.cmd()) {
        if(element != null) {
          generator.writeString(element);
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

  public void writeArray(JsonGenerator generator, ContainerCreationData[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(ContainerCreationData value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
