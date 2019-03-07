package io.flexio.docker.api.types.container;

import io.flexio.docker.api.types.container.optional.OptionalState;
import java.lang.Boolean;

public interface State {
  static Builder builder() {
    return new State.Builder();
  }

  static State.Builder from(State value) {
    if(value != null) {
      return new State.Builder()
          .status(value.status())
          .running(value.running())
          .paused(value.paused())
          .restarting(value.restarting())
          .dead(value.dead())
          ;
    }
    else {
      return null;
    }
  }

  Status status();

  Boolean running();

  Boolean paused();

  Boolean restarting();

  Boolean dead();

  State withStatus(Status value);

  State withRunning(Boolean value);

  State withPaused(Boolean value);

  State withRestarting(Boolean value);

  State withDead(Boolean value);

  int hashCode();

  State changed(State.Changer changer);

  OptionalState opt();

  enum Status {
    created,

    running,

    paused,

    restarting,

    removing,

    exited,

    dead,

    unexistent
  }

  class Builder {
    private Status status;

    private Boolean running;

    private Boolean paused;

    private Boolean restarting;

    private Boolean dead;

    public State build() {
      return new StateImpl(this.status, this.running, this.paused, this.restarting, this.dead);
    }

    public State.Builder status(Status status) {
      this.status = status;
      return this;
    }

    public State.Builder running(Boolean running) {
      this.running = running;
      return this;
    }

    public State.Builder paused(Boolean paused) {
      this.paused = paused;
      return this;
    }

    public State.Builder restarting(Boolean restarting) {
      this.restarting = restarting;
      return this;
    }

    public State.Builder dead(Boolean dead) {
      this.dead = dead;
      return this;
    }
  }

  interface Changer {
    State.Builder configure(State.Builder builder);
  }
}
