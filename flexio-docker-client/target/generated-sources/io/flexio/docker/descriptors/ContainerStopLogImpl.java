package io.flexio.docker.descriptors;

import io.flexio.docker.api.types.Container;
import io.flexio.docker.descriptors.optional.OptionalContainerStopLog;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ContainerStopLogImpl implements ContainerStopLog {
  private final Container container;

  private final Boolean success;

  private final ContainerStopLog.Action action;

  private final String message;

  ContainerStopLogImpl(Container container, Boolean success, ContainerStopLog.Action action, String message) {
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

  public ContainerStopLog.Action action() {
    return this.action;
  }

  public String message() {
    return this.message;
  }

  public ContainerStopLog withContainer(Container value) {
    return ContainerStopLog.from(this).container(value).build();
  }

  public ContainerStopLog withSuccess(Boolean value) {
    return ContainerStopLog.from(this).success(value).build();
  }

  public ContainerStopLog withAction(ContainerStopLog.Action value) {
    return ContainerStopLog.from(this).action(value).build();
  }

  public ContainerStopLog withMessage(String value) {
    return ContainerStopLog.from(this).message(value).build();
  }

  public ContainerStopLog changed(ContainerStopLog.Changer changer) {
    return changer.configure(ContainerStopLog.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainerStopLogImpl that = (ContainerStopLogImpl) o;
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
    return "ContainerStopLog{" +
        "container=" + Objects.toString(this.container) +
        ", " + "success=" + Objects.toString(this.success) +
        ", " + "action=" + Objects.toString(this.action) +
        ", " + "message=" + Objects.toString(this.message) +
        '}';
  }

  public OptionalContainerStopLog opt() {
    return OptionalContainerStopLog.of(this);
  }
}
