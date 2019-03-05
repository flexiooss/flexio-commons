package io.flexio.docker.api.types.container.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.types.container.State;
import java.io.IOException;

public class StateWriter {
  public void write(JsonGenerator generator, State value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("Status");
    if(value.status() != null) {
      generator.writeString(value.status().name());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("Running");
    if(value.running() != null) {
      generator.writeBoolean(value.running());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("Paused");
    if(value.paused() != null) {
      generator.writeBoolean(value.paused());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("Restarting");
    if(value.restarting() != null) {
      generator.writeBoolean(value.restarting());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("Dead");
    if(value.dead() != null) {
      generator.writeBoolean(value.dead());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, State[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(State value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
