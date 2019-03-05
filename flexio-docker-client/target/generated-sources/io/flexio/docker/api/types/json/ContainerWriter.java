package io.flexio.docker.api.types.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.types.Container;
import io.flexio.docker.api.types.container.json.NetworkSettingsWriter;
import io.flexio.docker.api.types.container.json.StateWriter;
import java.io.IOException;
import java.lang.String;

public class ContainerWriter {
  public void write(JsonGenerator generator, Container value) throws IOException {
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
    generator.writeFieldName("state");
    if(value.state() != null) {
      new StateWriter().write(generator, value.state());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("networkSettings");
    if(value.networkSettings() != null) {
      new NetworkSettingsWriter().write(generator, value.networkSettings());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, Container[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(Container value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
