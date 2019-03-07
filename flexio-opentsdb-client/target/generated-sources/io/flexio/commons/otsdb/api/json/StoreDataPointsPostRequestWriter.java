package io.flexio.commons.otsdb.api.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.commons.otsdb.api.StoreDataPointsPostRequest;
import io.flexio.commons.otsdb.api.types.DataPoint;
import io.flexio.commons.otsdb.api.types.json.DataPointWriter;
import java.io.IOException;

public class StoreDataPointsPostRequestWriter {
  public void write(JsonGenerator generator, StoreDataPointsPostRequest value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("summary");
    if(value.summary() != null) {
      generator.writeString(value.summary());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("details");
    if(value.details() != null) {
      generator.writeString(value.details());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("sync");
    if(value.sync() != null) {
      generator.writeBoolean(value.sync());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("sync_timeout");
    if(value.sync_timeout() != null) {
      generator.writeNumber(value.sync_timeout());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("authorization");
    if(value.authorization() != null) {
      generator.writeString(value.authorization());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("payload");
    if(value.payload() != null) {
      generator.writeStartArray();
      for (DataPoint element : value.payload()) {
        if(element != null) {
          new DataPointWriter().write(generator, element);
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

  public void writeArray(JsonGenerator generator, StoreDataPointsPostRequest[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(StoreDataPointsPostRequest value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
