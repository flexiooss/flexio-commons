package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.VersionGetResponse;
import io.flexio.docker.api.versiongetresponse.json.Status200Writer;
import java.io.IOException;

public class VersionGetResponseWriter {
  public void write(JsonGenerator generator, VersionGetResponse value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("status200");
    if(value.status200() != null) {
      new Status200Writer().write(generator, value.status200());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, VersionGetResponse[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(VersionGetResponse value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
