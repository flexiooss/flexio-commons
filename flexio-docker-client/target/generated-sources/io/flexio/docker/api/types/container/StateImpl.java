package io.flexio.docker.api.types.container;

import io.flexio.docker.api.types.container.optional.OptionalState;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class StateImpl implements State {
  private final State.Status status;

  private final Boolean running;

  private final Boolean paused;

  private final Boolean restarting;

  private final Boolean dead;

  StateImpl(State.Status status, Boolean running, Boolean paused, Boolean restarting, Boolean dead) {
    this.status = status;
    this.running = running;
    this.paused = paused;
    this.restarting = restarting;
    this.dead = dead;
  }

  public State.Status status() {
    return this.status;
  }

  public Boolean running() {
    return this.running;
  }

  public Boolean paused() {
    return this.paused;
  }

  public Boolean restarting() {
    return this.restarting;
  }

  public Boolean dead() {
    return this.dead;
  }

  public State withStatus(State.Status value) {
    return State.from(this).status(value).build();
  }

  public State withRunning(Boolean value) {
    return State.from(this).running(value).build();
  }

  public State withPaused(Boolean value) {
    return State.from(this).paused(value).build();
  }

  public State withRestarting(Boolean value) {
    return State.from(this).restarting(value).build();
  }

  public State withDead(Boolean value) {
    return State.from(this).dead(value).build();
  }

  public State changed(State.Changer changer) {
    return changer.configure(State.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StateImpl that = (StateImpl) o;
        return Objects.equals(this.status, that.status) && 
        Objects.equals(this.running, that.running) && 
        Objects.equals(this.paused, that.paused) && 
        Objects.equals(this.restarting, that.restarting) && 
        Objects.equals(this.dead, that.dead);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.status, this.running, this.paused, this.restarting, this.dead});
  }

  @Override
  public String toString() {
    return "State{" +
        "status=" + Objects.toString(this.status) +
        ", " + "running=" + Objects.toString(this.running) +
        ", " + "paused=" + Objects.toString(this.paused) +
        ", " + "restarting=" + Objects.toString(this.restarting) +
        ", " + "dead=" + Objects.toString(this.dead) +
        '}';
  }

  public OptionalState opt() {
    return OptionalState.of(this);
  }
}
