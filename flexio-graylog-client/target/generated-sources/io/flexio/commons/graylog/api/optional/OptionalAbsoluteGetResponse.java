package io.flexio.commons.graylog.api.optional;

import io.flexio.commons.graylog.api.AbsoluteGetResponse;
import io.flexio.commons.graylog.api.absolutegetresponse.optional.OptionalStatus200;
import io.flexio.commons.graylog.api.absolutegetresponse.optional.OptionalStatus400;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalAbsoluteGetResponse {
  private final Optional<AbsoluteGetResponse> optional;

  private OptionalStatus200 status200 = this.status200;

  private OptionalStatus400 status400 = this.status400;

  private OptionalAbsoluteGetResponse(AbsoluteGetResponse value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalAbsoluteGetResponse of(AbsoluteGetResponse value) {
    return new OptionalAbsoluteGetResponse(value);
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

  public AbsoluteGetResponse get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<AbsoluteGetResponse> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<AbsoluteGetResponse> filter(Predicate<AbsoluteGetResponse> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<AbsoluteGetResponse, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<AbsoluteGetResponse, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public AbsoluteGetResponse orElse(AbsoluteGetResponse value) {
    return this.optional.orElse(value);
  }

  public AbsoluteGetResponse orElseGet(Supplier<AbsoluteGetResponse> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> AbsoluteGetResponse orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
