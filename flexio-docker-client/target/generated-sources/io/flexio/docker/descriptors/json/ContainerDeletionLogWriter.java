package io.flexio.docker.descriptors.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.types.json.ContainerWriter;
import io.flexio.docker.descriptors.ContainerDeletionLog;
import java.io.IOException;

public class ContainerDeletionLogWriter {
  public void write(JsonGenerator generator, ContainerDeletionLog value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("container");
    if(value.container() != null) {
      new ContainerWriter().write(generator, value.container());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("success");
    if(value.success() != null) {
      generator.writeBoolean(value.success());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("action");
    if(value.action() != null) {
      generator.writeString(value.action().name());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("message");
    if(value.message() != null) {
      generator.writeString(value.message());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, ContainerDeletionLog[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(ContainerDeletionLog value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
