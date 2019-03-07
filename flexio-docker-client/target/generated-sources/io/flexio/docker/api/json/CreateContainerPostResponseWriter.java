package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.CreateContainerPostResponse;
import io.flexio.docker.api.createcontainerpostresponse.json.Status201Writer;
import io.flexio.docker.api.createcontainerpostresponse.json.Status400Writer;
import io.flexio.docker.api.createcontainerpostresponse.json.Status404Writer;
import io.flexio.docker.api.createcontainerpostresponse.json.Status409Writer;
import io.flexio.docker.api.createcontainerpostresponse.json.Status500Writer;
import java.io.IOException;

public class CreateContainerPostResponseWriter {
  public void write(JsonGenerator generator, CreateContainerPostResponse value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("status201");
    if(value.status201() != null) {
      new Status201Writer().write(generator, value.status201());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("status400");
    if(value.status400() != null) {
      new Status400Writer().write(generator, value.status400());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("status404");
    if(value.status404() != null) {
      new Status404Writer().write(generator, value.status404());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("status409");
    if(value.status409() != null) {
      new Status409Writer().write(generator, value.status409());
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

  public void writeArray(JsonGenerator generator, CreateContainerPostResponse[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(CreateContainerPostResponse value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
