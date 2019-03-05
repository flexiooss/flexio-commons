package io.flexio.docker.descriptors;

import io.flexio.docker.api.types.Container;
import io.flexio.docker.descriptors.optional.OptionalContainerStartLog;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class ContainerStartLogImpl implements ContainerStartLog {
  private final Container container;

  private final Boolean success;

  private final ContainerStartLog.Action action;

  private final String message;

  ContainerStartLogImpl(Container container, Boolean success, ContainerStartLog.Action action, String message) {
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

  public ContainerStartLog.Action action() {
    return this.action;
  }

  public String message() {
    return this.message;
  }

  public ContainerStartLog withContainer(Container value) {
    return ContainerStartLog.from(this).container(value).build();
  }

  public ContainerStartLog withSuccess(Boolean value) {
    return ContainerStartLog.from(this).success(value).build();
  }

  public ContainerStartLog withAction(ContainerStartLog.Action value) {
    return ContainerStartLog.from(this).action(value).build();
  }

  public ContainerStartLog withMessage(String value) {
    return ContainerStartLog.from(this).message(value).build();
  }

  public ContainerStartLog changed(ContainerStartLog.Changer changer) {
    return changer.configure(ContainerStartLog.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainerStartLogImpl that = (ContainerStartLogImpl) o;
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
    return "ContainerStartLog{" +
        "container=" + Objects.toString(this.container) +
        ", " + "success=" + Objects.toString(this.success) +
        ", " + "action=" + Objects.toString(this.action) +
        ", " + "message=" + Objects.toString(this.message) +
        '}';
  }

  public OptionalContainerStartLog opt() {
    return OptionalContainerStartLog.of(this);
  }
}
