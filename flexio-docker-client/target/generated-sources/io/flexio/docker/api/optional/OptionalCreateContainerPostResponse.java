package io.flexio.docker.api.optional;

import io.flexio.docker.api.CreateContainerPostResponse;
import io.flexio.docker.api.createcontainerpostresponse.optional.OptionalStatus201;
import io.flexio.docker.api.createcontainerpostresponse.optional.OptionalStatus400;
import io.flexio.docker.api.createcontainerpostresponse.optional.OptionalStatus404;
import io.flexio.docker.api.createcontainerpostresponse.optional.OptionalStatus409;
import io.flexio.docker.api.createcontainerpostresponse.optional.OptionalStatus500;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalCreateContainerPostResponse {
  private final Optional<CreateContainerPostResponse> optional;

  private OptionalStatus201 status201 = this.status201;

  private OptionalStatus400 status400 = this.status400;

  private OptionalStatus404 status404 = this.status404;

  private OptionalStatus409 status409 = this.status409;

  private OptionalStatus500 status500 = this.status500;

  private OptionalCreateContainerPostResponse(CreateContainerPostResponse value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalCreateContainerPostResponse of(CreateContainerPostResponse value) {
    return new OptionalCreateContainerPostResponse(value);
  }

  public synchronized OptionalStatus201 status201() {
    if(this.status201 == null) {
      this.status201 = OptionalStatus201.of(this.optional.isPresent() ? this.optional.get().status201() : null);
    }
    return this.status201;
  }

  public synchronized OptionalStatus400 status400() {
    if(this.status400 == null) {
      this.status400 = OptionalStatus400.of(this.optional.isPresent() ? this.optional.get().status400() : null);
    }
    return this.status400;
  }

  public synchronized OptionalStatus404 status404() {
    if(this.status404 == null) {
      this.status404 = OptionalStatus404.of(this.optional.isPresent() ? this.optional.get().status404() : null);
    }
    return this.status404;
  }

  public synchronized OptionalStatus409 status409() {
    if(this.status409 == null) {
      this.status409 = OptionalStatus409.of(this.optional.isPresent() ? this.optional.get().status409() : null);
    }
    return this.status409;
  }

  public synchronized OptionalStatus500 status500() {
    if(this.status500 == null) {
      this.status500 = OptionalStatus500.of(this.optional.isPresent() ? this.optional.get().status500() : null);
    }
    return this.status500;
  }

  public CreateContainerPostResponse get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<CreateContainerPostResponse> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<CreateContainerPostResponse> filter(Predicate<CreateContainerPostResponse> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<CreateContainerPostResponse, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<CreateContainerPostResponse, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public CreateContainerPostResponse orElse(CreateContainerPostResponse value) {
    return this.optional.orElse(value);
  }

  public CreateContainerPostResponse orElseGet(Supplier<CreateContainerPostResponse> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> CreateContainerPostResponse orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
