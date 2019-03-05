package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.ContainerListGetResponse;
import io.flexio.docker.api.containerlistgetresponse.json.Status200Writer;
import io.flexio.docker.api.containerlistgetresponse.json.Status400Writer;
import io.flexio.docker.api.containerlistgetresponse.json.Status500Writer;
import java.io.IOException;

public class ContainerListGetResponseWriter {
  public void write(JsonGenerator generator, ContainerListGetResponse value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("status200");
    if(value.status200() != null) {
      new Status200Writer().write(generator, value.status200());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("status400");
    if(value.status400() != null) {
      new Status400Writer().write(generator, value.status400());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("status500");
    if(value.status500() != null) {
      new Status500Writer().write(generator, value.status500());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, ContainerListGetResponse[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(ContainerListGetResponse value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
