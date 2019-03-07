package io.flexio.docker.api.optional;

import io.flexio.docker.api.VersionGetRequest;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalVersionGetRequest {
  private final Optional<VersionGetRequest> optional;

  private OptionalVersionGetRequest(VersionGetRequest value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalVersionGetRequest of(VersionGetRequest value) {
    return new OptionalVersionGetRequest(value);
  }

  public VersionGetRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<VersionGetRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<VersionGetRequest> filter(Predicate<VersionGetRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<VersionGetRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<VersionGetRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public VersionGetRequest orElse(VersionGetRequest value) {
    return this.optional.orElse(value);
  }

  public VersionGetRequest orElseGet(Supplier<VersionGetRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> VersionGetRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
