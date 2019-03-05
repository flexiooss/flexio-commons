package io.flexio.commons.graylog.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.commons.graylog.api.RelativeGetResponse;
import io.flexio.commons.graylog.api.relativegetresponse.json.Status200Writer;
import io.flexio.commons.graylog.api.relativegetresponse.json.Status400Writer;
import java.io.IOException;

public class RelativeGetResponseWriter {
  public void write(JsonGenerator generator, RelativeGetResponse value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("status200");
    if(value.status200() != null) {
      new Status200Writer().write(generator, value.status200());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("status400");
    if(value.status400() != null) {
      new Status400Writer().write(generator, value.status400());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, RelativeGetResponse[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(RelativeGetResponse value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
