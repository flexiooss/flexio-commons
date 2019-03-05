package io.flexio.commons.otsdb.api.types.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.commons.otsdb.api.types.DataPointError;
import java.io.IOException;

public class DataPointErrorWriter {
  public void write(JsonGenerator generator, DataPointError value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("datapoint");
    if(value.datapoint() != null) {
      new DataPointWriter().write(generator, value.datapoint());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("error");
    if(value.error() != null) {
      generator.writeString(value.error());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, DataPointError[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(DataPointError value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
