package io.flexio.docker.descriptors;

import io.flexio.docker.api.types.Container;
import io.flexio.docker.descriptors.optional.OptionalContainerStartLog;
import java.lang.Boolean;
import java.lang.String;
import java.util.function.Consumer;

public interface ContainerStartLog {
  static Builder builder() {
    return new ContainerStartLog.Builder();
  }

  static ContainerStartLog.Builder from(ContainerStartLog value) {
    if(value != null) {
      return new ContainerStartLog.Builder()
          .container(value.container())
          .success(value.success())
          .action(value.action())
          .message(value.message())
          ;
    }
    else {
      return null;
    }
  }

  Container container();

  Boolean success();

  Action action();

  String message();

  ContainerStartLog withContainer(Container value);

  ContainerStartLog withSuccess(Boolean value);

  ContainerStartLog withAction(Action value);

  ContainerStartLog withMessage(String value);

  int hashCode();

  ContainerStartLog changed(ContainerStartLog.Changer changer);

  OptionalContainerStartLog opt();

  enum Action {
    START,

    NONE
  }

  class Builder {
    private Container container;

    private Boolean success;

    private Action action;

    private String message;

    public ContainerStartLog build() {
      return new ContainerStartLogImpl(this.container, this.success, this.action, this.message);
    }

    public ContainerStartLog.Builder container(Container container) {
      this.container = container;
      return this;
    }

    public ContainerStartLog.Builder container(Consumer<Container.Builder> container) {
      Container.Builder builder = Container.builder();
      container.accept(builder);
      return this.container(builder.build());
    }

    public ContainerStartLog.Builder success(Boolean success) {
      this.success = success;
      return this;
    }

    public ContainerStartLog.Builder action(Action action) {
      this.action = action;
      return this;
    }

    public ContainerStartLog.Builder message(String message) {
      this.message = message;
      return this;
    }
  }

  interface Changer {
    ContainerStartLog.Builder configure(ContainerStartLog.Builder builder);
  }
}
