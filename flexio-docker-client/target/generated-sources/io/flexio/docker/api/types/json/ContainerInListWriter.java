package io.flexio.docker.api.types.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.types.ContainerInList;
import java.io.IOException;
import java.lang.String;

public class ContainerInListWriter {
  public void write(JsonGenerator generator, ContainerInList value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("Id");
    if(value.id() != null) {
      generator.writeString(value.id());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("Names");
    if(value.names() != null) {
      generator.writeStartArray();
      for (String element : value.names()) {
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
    generator.writeFieldName("Image");
    if(value.image() != null) {
      generator.writeString(value.image());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("State");
    if(value.state() != null) {
      generator.writeString(value.state());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("Status");
    if(value.status() != null) {
      generator.writeString(value.status());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, ContainerInList[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(ContainerInList value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
