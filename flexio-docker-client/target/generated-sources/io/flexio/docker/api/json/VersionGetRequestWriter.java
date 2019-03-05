package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.VersionGetRequest;
import java.io.IOException;

public class VersionGetRequestWriter {
  public void write(JsonGenerator generator, VersionGetRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, VersionGetRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(VersionGetRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
