package io.flexio.docker.descriptors;

import io.flexio.docker.api.types.Container;
import io.flexio.docker.descriptors.optional.OptionalContainerCreationLog;
import java.lang.Boolean;
import java.lang.String;
import java.util.function.Consumer;

public interface ContainerCreationLog {
  static Builder builder() {
    return new ContainerCreationLog.Builder();
  }

  static ContainerCreationLog.Builder from(ContainerCreationLog value) {
    if(value != null) {
      return new ContainerCreationLog.Builder()
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

  ContainerCreationLog withContainer(Container value);

  ContainerCreationLog withSuccess(Boolean value);

  ContainerCreationLog withAction(Action value);

  ContainerCreationLog withMessage(String value);

  int hashCode();

  ContainerCreationLog changed(ContainerCreationLog.Changer changer);

  OptionalContainerCreationLog opt();

  enum Action {
    CREATION,

    NONE,

    UPDATE
  }

  class Builder {
    private Container container;

    private Boolean success;

    private Action action;

    private String message;

    public ContainerCreationLog build() {
      return new ContainerCreationLogImpl(this.container, this.success, this.action, this.message);
    }

    public ContainerCreationLog.Builder container(Container container) {
      this.container = container;
      return this;
    }

    public ContainerCreationLog.Builder container(Consumer<Container.Builder> container) {
      Container.Builder builder = Container.builder();
      container.accept(builder);
      return this.container(builder.build());
    }

    public ContainerCreationLog.Builder success(Boolean success) {
      this.success = success;
      return this;
    }

    public ContainerCreationLog.Builder action(Action action) {
      this.action = action;
      return this;
    }

    public ContainerCreationLog.Builder message(String message) {
      this.message = message;
      return this;
    }
  }

  interface Changer {
    ContainerCreationLog.Builder configure(ContainerCreationLog.Builder builder);
  }
}
