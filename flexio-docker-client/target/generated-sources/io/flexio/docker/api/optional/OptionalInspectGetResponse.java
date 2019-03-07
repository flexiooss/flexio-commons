package io.flexio.docker.api.optional;

import io.flexio.docker.api.InspectGetResponse;
import io.flexio.docker.api.inspectgetresponse.optional.OptionalStatus200;
import io.flexio.docker.api.inspectgetresponse.optional.OptionalStatus404;
import io.flexio.docker.api.inspectgetresponse.optional.OptionalStatus500;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalInspectGetResponse {
  private final Optional<InspectGetResponse> optional;

  private OptionalStatus200 status200 = this.status200;

  private OptionalStatus404 status404 = this.status404;

  private OptionalStatus500 status500 = this.status500;

  private OptionalInspectGetResponse(InspectGetResponse value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalInspectGetResponse of(InspectGetResponse value) {
    return new OptionalInspectGetResponse(value);
  }

  public synchronized OptionalStatus200 status200() {
    if(this.status200 == null) {
      this.status200 = OptionalStatus200.of(this.optional.isPresent() ? this.optional.get().status200() : null);
    }
    return this.status200;
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

  public InspectGetResponse get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<InspectGetResponse> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<InspectGetResponse> filter(Predicate<InspectGetResponse> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<InspectGetResponse, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<InspectGetResponse, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public InspectGetResponse orElse(InspectGetResponse value) {
    return this.optional.orElse(value);
  }

  public InspectGetResponse orElseGet(Supplier<InspectGetResponse> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> InspectGetResponse orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
