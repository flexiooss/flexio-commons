package io.flexio.docker.api.types.container.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.types.container.NetworkSettings;
import java.io.IOException;

public class NetworkSettingsWriter {
  public void write(JsonGenerator generator, NetworkSettings value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("IPAddress");
    if(value.iPAddress() != null) {
      generator.writeString(value.iPAddress());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, NetworkSettings[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(NetworkSettings value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
