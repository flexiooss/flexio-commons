package io.flexio.docker.api.optional;

import io.flexio.docker.api.InspectImageGetResponse;
import io.flexio.docker.api.inspectimagegetresponse.optional.OptionalStatus200;
import io.flexio.docker.api.inspectimagegetresponse.optional.OptionalStatus404;
import io.flexio.docker.api.inspectimagegetresponse.optional.OptionalStatus500;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalInspectImageGetResponse {
  private final Optional<InspectImageGetResponse> optional;

  private OptionalStatus200 status200 = this.status200;

  private OptionalStatus404 status404 = this.status404;

  private OptionalStatus500 status500 = this.status500;

  private OptionalInspectImageGetResponse(InspectImageGetResponse value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalInspectImageGetResponse of(InspectImageGetResponse value) {
    return new OptionalInspectImageGetResponse(value);
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

  public InspectImageGetResponse get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<InspectImageGetResponse> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<InspectImageGetResponse> filter(Predicate<InspectImageGetResponse> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<InspectImageGetResponse, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<InspectImageGetResponse, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public InspectImageGetResponse orElse(InspectImageGetResponse value) {
    return this.optional.orElse(value);
  }

  public InspectImageGetResponse orElseGet(Supplier<InspectImageGetResponse> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> InspectImageGetResponse orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
