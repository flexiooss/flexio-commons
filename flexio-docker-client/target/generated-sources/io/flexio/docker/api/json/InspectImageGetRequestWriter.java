package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.InspectImageGetRequest;
import java.io.IOException;

public class InspectImageGetRequestWriter {
  public void write(JsonGenerator generator, InspectImageGetRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("imageId");
    if(value.imageId() != null) {
      generator.writeString(value.imageId());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, InspectImageGetRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(InspectImageGetRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
