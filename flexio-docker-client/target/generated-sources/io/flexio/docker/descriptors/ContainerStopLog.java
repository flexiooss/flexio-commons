package io.flexio.docker.descriptors;

import io.flexio.docker.api.types.Container;
import io.flexio.docker.descriptors.optional.OptionalContainerStopLog;
import java.lang.Boolean;
import java.lang.String;
import java.util.function.Consumer;

public interface ContainerStopLog {
  static Builder builder() {
    return new ContainerStopLog.Builder();
  }

  static ContainerStopLog.Builder from(ContainerStopLog value) {
    if(value != null) {
      return new ContainerStopLog.Builder()
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

  ContainerStopLog withContainer(Container value);

  ContainerStopLog withSuccess(Boolean value);

  ContainerStopLog withAction(Action value);

  ContainerStopLog withMessage(String value);

  int hashCode();

  ContainerStopLog changed(ContainerStopLog.Changer changer);

  OptionalContainerStopLog opt();

  enum Action {
    STOP,

    NONE
  }

  class Builder {
    private Container container;

    private Boolean success;

    private Action action;

    private String message;

    public ContainerStopLog build() {
      return new ContainerStopLogImpl(this.container, this.success, this.action, this.message);
    }

    public ContainerStopLog.Builder container(Container container) {
      this.container = container;
      return this;
    }

    public ContainerStopLog.Builder container(Consumer<Container.Builder> container) {
      Container.Builder builder = Container.builder();
      container.accept(builder);
      return this.container(builder.build());
    }

    public ContainerStopLog.Builder success(Boolean success) {
      this.success = success;
      return this;
    }

    public ContainerStopLog.Builder action(Action action) {
      this.action = action;
      return this;
    }

    public ContainerStopLog.Builder message(String message) {
      this.message = message;
      return this;
    }
  }

  interface Changer {
    ContainerStopLog.Builder configure(ContainerStopLog.Builder builder);
  }
}
