package io.flexio.commons.graylog.api.absolutegetresponse.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.commons.graylog.api.absolutegetresponse.Status400;
import java.io.IOException;
import org.codingmatters.value.objects.values.json.ObjectValueWriter;

public class Status400Writer {
  public void write(JsonGenerator generator, Status400 value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("payload");
    if(value.payload() != null) {
      new ObjectValueWriter().write(generator, value.payload());
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
