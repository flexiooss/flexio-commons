package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.WaitForPostResponse;
import io.flexio.docker.api.waitforpostresponse.json.Status200Writer;
import io.flexio.docker.api.waitforpostresponse.json.Status404Writer;
import io.flexio.docker.api.waitforpostresponse.json.Status500Writer;
import java.io.IOException;

public class WaitForPostResponseWriter {
  public void write(JsonGenerator generator, WaitForPostResponse value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("status200");
    if(value.status200() != null) {
      new Status200Writer().write(generator, value.status200());
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

  public void writeArray(JsonGenerator generator, WaitForPostResponse[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(WaitForPostResponse value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}