package io.flexio.docker.api.optional;

import io.flexio.docker.api.VersionGetResponse;
import io.flexio.docker.api.versiongetresponse.optional.OptionalStatus200;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalVersionGetResponse {
  private final Optional<VersionGetResponse> optional;

  private OptionalStatus200 status200 = this.status200;

  private OptionalVersionGetResponse(VersionGetResponse value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalVersionGetResponse of(VersionGetResponse value) {
    return new OptionalVersionGetResponse(value);
  }

  public synchronized OptionalStatus200 status200() {
    if(this.status200 == null) {
      this.status200 = OptionalStatus200.of(this.optional.isPresent() ? this.optional.get().status200() : null);
    }
    return this.status200;
  }

  public VersionGetResponse get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<VersionGetResponse> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<VersionGetResponse> filter(Predicate<VersionGetResponse> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<VersionGetResponse, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<VersionGetResponse, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public VersionGetResponse orElse(VersionGetResponse value) {
    return this.optional.orElse(value);
  }

  public VersionGetResponse orElseGet(Supplier<VersionGetResponse> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> VersionGetResponse orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
