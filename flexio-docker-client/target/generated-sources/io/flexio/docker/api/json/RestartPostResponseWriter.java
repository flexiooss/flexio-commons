package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.RestartPostResponse;
import io.flexio.docker.api.restartpostresponse.json.Status204Writer;
import io.flexio.docker.api.restartpostresponse.json.Status309Writer;
import io.flexio.docker.api.restartpostresponse.json.Status404Writer;
import io.flexio.docker.api.restartpostresponse.json.Status500Writer;
import java.io.IOException;

public class RestartPostResponseWriter {
  public void write(JsonGenerator generator, RestartPostResponse value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("status204");
    if(value.status204() != null) {
      new Status204Writer().write(generator, value.status204());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("status309");
    if(value.status309() != null) {
      new Status309Writer().write(generator, value.status309());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("status404");
    if(value.status404() != null) {
      new Status404Writer().write(generator, value.status404());
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

  public void writeArray(JsonGenerator generator, RestartPostResponse[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(RestartPostResponse value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
