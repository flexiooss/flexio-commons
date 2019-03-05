package io.flexio.docker.api.optional;

import io.flexio.docker.api.StopPostResponse;
import io.flexio.docker.api.stoppostresponse.optional.OptionalStatus204;
import io.flexio.docker.api.stoppostresponse.optional.OptionalStatus309;
import io.flexio.docker.api.stoppostresponse.optional.OptionalStatus404;
import io.flexio.docker.api.stoppostresponse.optional.OptionalStatus500;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalStopPostResponse {
  private final Optional<StopPostResponse> optional;

  private OptionalStatus204 status204 = this.status204;

  private OptionalStatus309 status309 = this.status309;

  private OptionalStatus404 status404 = this.status404;

  private OptionalStatus500 status500 = this.status500;

  private OptionalStopPostResponse(StopPostResponse value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalStopPostResponse of(StopPostResponse value) {
    return new OptionalStopPostResponse(value);
  }

  public synchronized OptionalStatus204 status204() {
    if(this.status204 == null) {
      this.status204 = OptionalStatus204.of(this.optional.isPresent() ? this.optional.get().status204() : null);
    }
    return this.status204;
  }

  public synchronized OptionalStatus309 status309() {
    if(this.status309 == null) {
      this.status309 = OptionalStatus309.of(this.optional.isPresent() ? this.optional.get().status309() : null);
    }
    return this.status309;
  }

  public synchronized OptionalStatus404 status404() {
    if(this.status404 == null) {
      this.status404 = OptionalStatus404.of(this.optional.isPresent() ? this.optional.get().status404() : null);
    }
    return this.status404;
  }

  public synchronized OptionalStatus500 status500() {
    if(this.status500 == null) {
      this.status500 = OptionalStatus500.of(this.optional.isPresent() ? this.optional.get().status500() : null);
    }
    return this.status500;
  }

  public StopPostResponse get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<StopPostResponse> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<StopPostResponse> filter(Predicate<StopPostResponse> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<StopPostResponse, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<StopPostResponse, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public StopPostResponse orElse(StopPostResponse value) {
    return this.optional.orElse(value);
  }

  public StopPostResponse orElseGet(Supplier<StopPostResponse> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> StopPostResponse orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
