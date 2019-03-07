package io.flexio.docker.descriptors;

import io.flexio.docker.api.types.Container;
import io.flexio.docker.descriptors.optional.OptionalContainerCreationLog;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ContainerCreationLogImpl implements ContainerCreationLog {
  private final Container container;

  private final Boolean success;

  private final ContainerCreationLog.Action action;

  private final String message;

  ContainerCreationLogImpl(Container container, Boolean success, ContainerCreationLog.Action action, String message) {
    this.container = container;
    this.success = success;
    this.action = action;
    this.message = message;
  }

  public Container container() {
    return this.container;
  }

  public Boolean success() {
    return this.success;
  }

  public ContainerCreationLog.Action action() {
    return this.action;
  }

  public String message() {
    return this.message;
  }

  public ContainerCreationLog withContainer(Container value) {
    return ContainerCreationLog.from(this).container(value).build();
  }

  public ContainerCreationLog withSuccess(Boolean value) {
    return ContainerCreationLog.from(this).success(value).build();
  }

  public ContainerCreationLog withAction(ContainerCreationLog.Action value) {
    return ContainerCreationLog.from(this).action(value).build();
  }

  public ContainerCreationLog withMessage(String value) {
    return ContainerCreationLog.from(this).message(value).build();
  }

  public ContainerCreationLog changed(ContainerCreationLog.Changer changer) {
    return changer.configure(ContainerCreationLog.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainerCreationLogImpl that = (ContainerCreationLogImpl) o;
        return Objects.equals(this.container, that.container) && 
        Objects.equals(this.success, that.success) && 
        Objects.equals(this.action, that.action) && 
        Objects.equals(this.message, that.message);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.container, this.success, this.action, this.message});
  }

  @Override
  public String toString() {
    return "ContainerCreationLog{" +
        "container=" + Objects.toString(this.container) +
        ", " + "success=" + Objects.toString(this.success) +
        ", " + "action=" + Objects.toString(this.action) +
        ", " + "message=" + Objects.toString(this.message) +
        '}';
  }

  public OptionalContainerCreationLog opt() {
    return OptionalContainerCreationLog.of(this);
  }
}
