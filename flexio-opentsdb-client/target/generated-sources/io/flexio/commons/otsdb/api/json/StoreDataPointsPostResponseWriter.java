package io.flexio.commons.otsdb.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.commons.otsdb.api.StoreDataPointsPostResponse;
import io.flexio.commons.otsdb.api.storedatapointspostresponse.json.Status204Writer;
import io.flexio.commons.otsdb.api.storedatapointspostresponse.json.Status400Writer;
import java.io.IOException;

public class StoreDataPointsPostResponseWriter {
  public void write(JsonGenerator generator, StoreDataPointsPostResponse value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("status204");
    if(value.status204() != null) {
      new Status204Writer().write(generator, value.status204());
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

  public void writeArray(JsonGenerator generator, StoreDataPointsPostResponse[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(StoreDataPointsPostResponse value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
