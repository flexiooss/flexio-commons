package io.flexio.docker.api.optional;

import io.flexio.docker.api.KillPostRequest;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalKillPostRequest {
  private final Optional<KillPostRequest> optional;

  private final Optional<String> signal;

  private final Optional<String> containerId;

  private OptionalKillPostRequest(KillPostRequest value) {
    this.optional = Optional.ofNullable(value);
    this.signal = Optional.ofNullable(value != null ? value.signal() : null);
    this.containerId = Optional.ofNullable(value != null ? value.containerId() : null);
  }

  public static OptionalKillPostRequest of(KillPostRequest value) {
    return new OptionalKillPostRequest(value);
  }

  public Optional<String> signal() {
    return this.signal;
  }

  public Optional<String> containerId() {
    return this.containerId;
  }

  public KillPostRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<KillPostRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<KillPostRequest> filter(Predicate<KillPostRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<KillPostRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<KillPostRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public KillPostRequest orElse(KillPostRequest value) {
    return this.optional.orElse(value);
  }

  public KillPostRequest orElseGet(Supplier<KillPostRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> KillPostRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
