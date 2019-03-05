package io.flexio.docker.api.types.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.types.Image;
import java.io.IOException;
import java.lang.String;

public class ImageWriter {
  public void write(JsonGenerator generator, Image value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("Id");
    if(value.id() != null) {
      generator.writeString(value.id());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("RepoTags");
    if(value.repoTags() != null) {
      generator.writeStartArray();
      for (String element : value.repoTags()) {
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

  public void writeArray(JsonGenerator generator, Image[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(Image value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
