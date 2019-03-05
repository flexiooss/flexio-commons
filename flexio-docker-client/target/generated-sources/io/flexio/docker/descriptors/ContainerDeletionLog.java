package io.flexio.docker.descriptors;

import io.flexio.docker.api.types.Container;
import io.flexio.docker.descriptors.optional.OptionalContainerDeletionLog;
import java.lang.Boolean;
import java.lang.String;
import java.util.function.Consumer;

public interface ContainerDeletionLog {
  static Builder builder() {
    return new ContainerDeletionLog.Builder();
  }

  static ContainerDeletionLog.Builder from(ContainerDeletionLog value) {
    if(value != null) {
      return new ContainerDeletionLog.Builder()
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

  ContainerDeletionLog withContainer(Container value);

  ContainerDeletionLog withSuccess(Boolean value);

  ContainerDeletionLog withAction(Action value);

  ContainerDeletionLog withMessage(String value);

  int hashCode();

  ContainerDeletionLog changed(ContainerDeletionLog.Changer changer);

  OptionalContainerDeletionLog opt();

  enum Action {
    DELETE,

    NONE
  }

  class Builder {
    private Container container;

    private Boolean success;

    private Action action;

    private String message;

    public ContainerDeletionLog build() {
      return new ContainerDeletionLogImpl(this.container, this.success, this.action, this.message);
    }

    public ContainerDeletionLog.Builder container(Container container) {
      this.container = container;
      return this;
    }

    public ContainerDeletionLog.Builder container(Consumer<Container.Builder> container) {
      Container.Builder builder = Container.builder();
      container.accept(builder);
      return this.container(builder.build());
    }

    public ContainerDeletionLog.Builder success(Boolean success) {
      this.success = success;
      return this;
    }

    public ContainerDeletionLog.Builder action(Action action) {
      this.action = action;
      return this;
    }

    public ContainerDeletionLog.Builder message(String message) {
      this.message = message;
      return this;
    }
  }

  interface Changer {
    ContainerDeletionLog.Builder configure(ContainerDeletionLog.Builder builder);
  }
}
