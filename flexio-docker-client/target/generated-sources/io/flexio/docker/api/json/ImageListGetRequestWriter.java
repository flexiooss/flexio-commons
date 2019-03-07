package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.ImageListGetRequest;
import java.io.IOException;

public class ImageListGetRequestWriter {
  public void write(JsonGenerator generator, ImageListGetRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("all");
    if(value.all() != null) {
      generator.writeBoolean(value.all());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("digests");
    if(value.digests() != null) {
      generator.writeBoolean(value.digests());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, ImageListGetRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(ImageListGetRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
