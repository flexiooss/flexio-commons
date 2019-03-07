package io.flexio.docker.descriptors;

import io.flexio.docker.api.types.Container;
import io.flexio.docker.descriptors.optional.OptionalContainerDeletionLog;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ContainerDeletionLogImpl implements ContainerDeletionLog {
  private final Container container;

  private final Boolean success;

  private final ContainerDeletionLog.Action action;

  private final String message;

  ContainerDeletionLogImpl(Container container, Boolean success, ContainerDeletionLog.Action action, String message) {
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

  public ContainerDeletionLog.Action action() {
    return this.action;
  }

  public String message() {
    return this.message;
  }

  public ContainerDeletionLog withContainer(Container value) {
    return ContainerDeletionLog.from(this).container(value).build();
  }

  public ContainerDeletionLog withSuccess(Boolean value) {
    return ContainerDeletionLog.from(this).success(value).build();
  }

  public ContainerDeletionLog withAction(ContainerDeletionLog.Action value) {
    return ContainerDeletionLog.from(this).action(value).build();
  }

  public ContainerDeletionLog withMessage(String value) {
    return ContainerDeletionLog.from(this).message(value).build();
  }

  public ContainerDeletionLog changed(ContainerDeletionLog.Changer changer) {
    return changer.configure(ContainerDeletionLog.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainerDeletionLogImpl that = (ContainerDeletionLogImpl) o;
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
    return "ContainerDeletionLog{" +
        "container=" + Objects.toString(this.container) +
        ", " + "success=" + Objects.toString(this.success) +
        ", " + "action=" + Objects.toString(this.action) +
        ", " + "message=" + Objects.toString(this.message) +
        '}';
  }

  public OptionalContainerDeletionLog opt() {
    return OptionalContainerDeletionLog.of(this);
  }
}
