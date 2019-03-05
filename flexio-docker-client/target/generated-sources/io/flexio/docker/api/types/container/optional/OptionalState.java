package io.flexio.docker.api.types.container.optional;

import io.flexio.docker.api.types.container.State;
import java.lang.Boolean;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalState {
  private final Optional<State> optional;

  private final Optional<State.Status> status;

  private final Optional<Boolean> running;

  private final Optional<Boolean> paused;

  private final Optional<Boolean> restarting;

  private final Optional<Boolean> dead;

  private OptionalState(State value) {
    this.optional = Optional.ofNullable(value);
    this.status = Optional.ofNullable(value != null ? value.status() : null);
    this.running = Optional.ofNullable(value != null ? value.running() : null);
    this.paused = Optional.ofNullable(value != null ? value.paused() : null);
    this.restarting = Optional.ofNullable(value != null ? value.restarting() : null);
    this.dead = Optional.ofNullable(value != null ? value.dead() : null);
  }

  public static OptionalState of(State value) {
    return new OptionalState(value);
  }

  public Optional<State.Status> status() {
    return this.status;
  }

  public Optional<Boolean> running() {
    return this.running;
  }

  public Optional<Boolean> paused() {
    return this.paused;
  }

  public Optional<Boolean> restarting() {
    return this.restarting;
  }

  public Optional<Boolean> dead() {
    return this.dead;
  }

  public State get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<State> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<State> filter(Predicate<State> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<State, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<State, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public State orElse(State value) {
    return this.optional.orElse(value);
  }

  public State orElseGet(Supplier<State> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> State orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
