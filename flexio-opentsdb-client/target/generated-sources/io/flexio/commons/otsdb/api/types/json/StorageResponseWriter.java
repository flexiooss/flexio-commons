package io.flexio.commons.otsdb.api.types.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.commons.otsdb.api.types.DataPointError;
import io.flexio.commons.otsdb.api.types.StorageResponse;
import java.io.IOException;

public class StorageResponseWriter {
  public void write(JsonGenerator generator, StorageResponse value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("success");
    if(value.success() != null) {
      generator.writeNumber(value.success());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("failed");
    if(value.failed() != null) {
      generator.writeNumber(value.failed());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("errors");
    if(value.errors() != null) {
      generator.writeStartArray();
      for (DataPointError element : value.errors()) {
        if(element != null) {
          new DataPointErrorWriter().write(generator, element);
        } else {
          generator.writeNull();
        }
      }
      generator.writeEndArray();
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, StorageResponse[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(StorageResponse value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
