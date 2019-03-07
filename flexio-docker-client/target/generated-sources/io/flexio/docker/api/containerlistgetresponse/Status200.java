package io.flexio.docker.api.containerlistgetresponse;

import io.flexio.docker.api.ValueList;
import io.flexio.docker.api.containerlistgetresponse.optional.OptionalStatus200;
import io.flexio.docker.api.types.ContainerInList;
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

  ValueList<ContainerInList> payload();

  Status200 withPayload(ValueList<ContainerInList> value);

  int hashCode();

  Status200 changed(Status200.Changer changer);

  OptionalStatus200 opt();

  class Builder {
    private ValueList<ContainerInList> payload;

    public Status200 build() {
      return new Status200Impl(this.payload);
    }

    public Status200.Builder payload() {
      this.payload = null;
      return this;
    }

    public Status200.Builder payload(ContainerInList... payload) {
      this.payload = payload != null ? new ValueList.Builder<ContainerInList>().with(payload).build() : null;
      return this;
    }

    public Status200.Builder payload(ValueList<ContainerInList> payload) {
      this.payload = payload;
      return this;
    }

    public Status200.Builder payload(Collection<ContainerInList> payload) {
      this.payload = payload != null ? new ValueList.Builder<ContainerInList>().with(payload).build() : null;
      return this;
    }

    public Status200.Builder payload(Consumer<ContainerInList.Builder>... payloadElements) {
      if(payloadElements != null) {
        LinkedList<ContainerInList> elements = new LinkedList<ContainerInList>();
        for(Consumer<ContainerInList.Builder> payload : payloadElements) {
          ContainerInList.Builder builder = ContainerInList.builder();
          payload.accept(builder);
          elements.add(builder.build());
        }
        this.payload(elements.toArray(new ContainerInList[elements.size()]));
      }
      return this;
    }
  }

  interface Changer {
    Status200.Builder configure(Status200.Builder builder);
  }
}
