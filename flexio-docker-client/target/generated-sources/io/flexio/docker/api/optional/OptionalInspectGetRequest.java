package io.flexio.docker.api.optional;

import io.flexio.docker.api.InspectGetRequest;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalInspectGetRequest {
  private final Optional<InspectGetRequest> optional;

  private final Optional<String> containerId;

  private OptionalInspectGetRequest(InspectGetRequest value) {
    this.optional = Optional.ofNullable(value);
    this.containerId = Optional.ofNullable(value != null ? value.containerId() : null);
  }

  public static OptionalInspectGetRequest of(InspectGetRequest value) {
    return new OptionalInspectGetRequest(value);
  }

  public Optional<String> containerId() {
    return this.containerId;
  }

  public InspectGetRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<InspectGetRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<InspectGetRequest> filter(Predicate<InspectGetRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<InspectGetRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<InspectGetRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public InspectGetRequest orElse(InspectGetRequest value) {
    return this.optional.orElse(value);
  }

  public InspectGetRequest orElseGet(Supplier<InspectGetRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> InspectGetRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
