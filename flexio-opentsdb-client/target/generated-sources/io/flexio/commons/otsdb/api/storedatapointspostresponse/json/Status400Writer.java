package io.flexio.commons.otsdb.api.storedatapointspostresponse.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.commons.otsdb.api.storedatapointspostresponse.Status400;
import io.flexio.commons.otsdb.api.types.json.StorageResponseWriter;
import java.io.IOException;

public class Status400Writer {
  public void write(JsonGenerator generator, Status400 value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("payload");
    if(value.payload() != null) {
      new StorageResponseWriter().write(generator, value.payload());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, Status400[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(Status400 value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
