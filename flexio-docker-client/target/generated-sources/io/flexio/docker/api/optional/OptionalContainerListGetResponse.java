package io.flexio.docker.api.optional;

import io.flexio.docker.api.ContainerListGetResponse;
import io.flexio.docker.api.containerlistgetresponse.optional.OptionalStatus200;
import io.flexio.docker.api.containerlistgetresponse.optional.OptionalStatus400;
import io.flexio.docker.api.containerlistgetresponse.optional.OptionalStatus500;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalContainerListGetResponse {
  private final Optional<ContainerListGetResponse> optional;

  private OptionalStatus200 status200 = this.status200;

  private OptionalStatus400 status400 = this.status400;

  private OptionalStatus500 status500 = this.status500;

  private OptionalContainerListGetResponse(ContainerListGetResponse value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalContainerListGetResponse of(ContainerListGetResponse value) {
    return new OptionalContainerListGetResponse(value);
  }

  public synchronized OptionalStatus200 status200() {
    if(this.status200 == null) {
      this.status200 = OptionalStatus200.of(this.optional.isPresent() ? this.optional.get().status200() : null);
    }
    return this.status200;
  }

  public synchronized OptionalStatus400 status400() {
    if(this.status400 == null) {
      this.status400 = OptionalStatus400.of(this.optional.isPresent() ? this.optional.get().status400() : null);
    }
    return this.status400;
  }

  public synchronized OptionalStatus500 status500() {
    if(this.status500 == null) {
      this.status500 = OptionalStatus500.of(this.optional.isPresent() ? this.optional.get().status500() : null);
    }
    return this.status500;
  }

  public ContainerListGetResponse get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ContainerListGetResponse> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ContainerListGetResponse> filter(Predicate<ContainerListGetResponse> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ContainerListGetResponse, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ContainerListGetResponse, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ContainerListGetResponse orElse(ContainerListGetResponse value) {
    return this.optional.orElse(value);
  }

  public ContainerListGetResponse orElseGet(Supplier<ContainerListGetResponse> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ContainerListGetResponse orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
