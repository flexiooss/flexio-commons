package io.flexio.commons.graylog.api.relativegetresponse;

import io.flexio.commons.graylog.api.relativegetresponse.optional.OptionalStatus400;
import java.util.function.Consumer;
import org.codingmatters.value.objects.values.ObjectValue;

public interface Status400 {
  static Builder builder() {
    return new Status400.Builder();
  }

  static Status400.Builder from(Status400 value) {
    if(value != null) {
      return new Status400.Builder()
          .payload(value.payload())
          ;
    }
    else {
      return null;
    }
  }

  ObjectValue payload();

  Status400 withPayload(ObjectValue value);

  int hashCode();

  Status400 changed(Status400.Changer changer);

  OptionalStatus400 opt();

  class Builder {
    private ObjectValue payload;

    public Status400 build() {
      return new Status400Impl(this.payload);
    }

    public Status400.Builder payload(ObjectValue payload) {
      this.payload = payload;
      return this;
    }

    public Status400.Builder payload(Consumer<ObjectValue.Builder> payload) {
      ObjectValue.Builder builder = ObjectValue.builder();
      payload.accept(builder);
      return this.payload(builder.build());
    }
  }

  interface Changer {
    Status400.Builder configure(Status400.Builder builder);
  }
}
