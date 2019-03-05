package io.flexio.docker.api.imagelistgetresponse;

import io.flexio.docker.api.ValueList;
import io.flexio.docker.api.imagelistgetresponse.optional.OptionalStatus200;
import io.flexio.docker.api.types.Image;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

public interface Status200 {
  static Builder builder() {
    return new Status200.Builder();
  }

  static Status200.Builder from(Status200 value) {
    if(value != null) {
      return new Status200.Builder()
          .payload(value.payload())
          ;
    }
    else {
      return null;
    }
  }

  ValueList<Image> payload();

  Status200 withPayload(ValueList<Image> value);

  int hashCode();

  Status200 changed(Status200.Changer changer);

  OptionalStatus200 opt();

  class Builder {
    private ValueList<Image> payload;

    public Status200 build() {
      return new Status200Impl(this.payload);
    }

    public Status200.Builder payload() {
      this.payload = null;
      return this;
    }

    public Status200.Builder payload(Image... payload) {
      this.payload = payload != null ? new ValueList.Builder<Image>().with(payload).build() : null;
      return this;
    }

    public Status200.Builder payload(ValueList<Image> payload) {
      this.payload = payload;
      return this;
    }

    public Status200.Builder payload(Collection<Image> payload) {
      this.payload = payload != null ? new ValueList.Builder<Image>().with(payload).build() : null;
      return this;
    }

    public Status200.Builder payload(Consumer<Image.Builder>... payloadElements) {
      if(payloadElements != null) {
        LinkedList<Image> elements = new LinkedList<Image>();
        for(Consumer<Image.Builder> payload : payloadElements) {
          Image.Builder builder = Image.builder();
          payload.accept(builder);
          elements.add(builder.build());
        }
        this.payload(elements.toArray(new Image[elements.size()]));
      }
      return this;
    }
  }

  interface Changer {
    Status200.Builder configure(Status200.Builder builder);
  }
}
