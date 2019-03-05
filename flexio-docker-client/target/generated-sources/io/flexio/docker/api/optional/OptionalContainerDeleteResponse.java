package io.flexio.docker.api.optional;

import io.flexio.docker.api.ContainerDeleteResponse;
import io.flexio.docker.api.containerdeleteresponse.optional.OptionalStatus204;
import io.flexio.docker.api.containerdeleteresponse.optional.OptionalStatus400;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalContainerDeleteResponse {
  private final Optional<ContainerDeleteResponse> optional;

  private OptionalStatus204 status204 = this.status204;

  private OptionalStatus400 status400 = this.status400;

  private OptionalContainerDeleteResponse(ContainerDeleteResponse value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalContainerDeleteResponse of(ContainerDeleteResponse value) {
    return new OptionalContainerDeleteResponse(value);
  }

  public synchronized OptionalStatus204 status204() {
    if(this.status204 == null) {
      this.status204 = OptionalStatus204.of(this.optional.isPresent() ? this.optional.get().status204() : null);
    }
    return this.status204;
  }

  public synchronized OptionalStatus400 status400() {
    if(this.status400 == null) {
      this.status400 = OptionalStatus400.of(this.optional.isPresent() ? this.optional.get().status400() : null);
    }
    return this.status400;
  }

  public ContainerDeleteResponse get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ContainerDeleteResponse> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ContainerDeleteResponse> filter(Predicate<ContainerDeleteResponse> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ContainerDeleteResponse, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ContainerDeleteResponse, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ContainerDeleteResponse orElse(ContainerDeleteResponse value) {
    return this.optional.orElse(value);
  }

  public ContainerDeleteResponse orElseGet(Supplier<ContainerDeleteResponse> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ContainerDeleteResponse orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
