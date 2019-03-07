package io.flexio.docker.api.types.json;

import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.docker.api.types.Version;
import java.io.IOException;

public class VersionWriter {
  public void write(JsonGenerator generator, Version value) throws IOException {
    generator.writeStartObject();
    generator.writeFieldName("Version");
    if(value.version() != null) {
      generator.writeString(value.version());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("ApiVersion");
    if(value.apiVersion() != null) {
      generator.writeString(value.apiVersion());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("MinAPIVersion");
    if(value.minAPIVersion() != null) {
      generator.writeString(value.minAPIVersion());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("GitCommit");
    if(value.gitCommit() != null) {
      generator.writeString(value.gitCommit());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("GoVersion");
    if(value.goVersion() != null) {
      generator.writeString(value.goVersion());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("Os");
    if(value.os() != null) {
      generator.writeString(value.os());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("Arch");
    if(value.arch() != null) {
      generator.writeString(value.arch());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("KernelVersion");
    if(value.kernelVersion() != null) {
      generator.writeString(value.kernelVersion());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("Experimental");
    if(value.experimental() != null) {
      generator.writeBoolean(value.experimental());
    } else {
      generator.writeNull();
    }
    generator.writeFieldName("BuildTime");
    if(value.buildTime() != null) {
      generator.writeString(value.buildTime());
    } else {
      generator.writeNull();
    }
    generator.writeEndObject();
  }

  public void writeArray(JsonGenerator generator, Version[] values) throws IOException {
    if(values == null) {
      generator.writeNull();
    } else {
      generator.writeStartArray();
      for(Version value : values) {
        this.write(generator, value);
      }
      generator.writeEndArray();
    }
  }
}
