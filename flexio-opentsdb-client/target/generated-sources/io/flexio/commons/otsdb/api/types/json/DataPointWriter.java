package io.flexio.commons.otsdb.api.types.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.commons.otsdb.api.types.DataPoint;
import java.io.IOException;
import org.codingmatters.value.objects.values.json.ObjectValueWriter;

public class DataPointWriter {
  public void write(JsonGenerator generator, DataPoint value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("metric");
    if(value.metric() != null) {
      generator.writeString(value.metric());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("timestamp");
    if(value.timestamp() != null) {
      generator.writeNumber(value.timestamp());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("value");
    if(value.value() != null) {
      generator.writeNumber(value.value());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("tags");
    if(value.tags() != null) {
      new ObjectValueWriter().write(generator, value.tags());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, DataPoint[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(DataPoint value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
