package io.flexio.docker.api.optional;

import io.flexio.docker.api.InspectImageGetRequest;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalInspectImageGetRequest {
  private final Optional<InspectImageGetRequest> optional;

  private final Optional<String> imageId;

  private OptionalInspectImageGetRequest(InspectImageGetRequest value) {
    this.optional = Optional.ofNullable(value);
    this.imageId = Optional.ofNullable(value != null ? value.imageId() : null);
  }

  public static OptionalInspectImageGetRequest of(InspectImageGetRequest value) {
    return new OptionalInspectImageGetRequest(value);
  }

  public Optional<String> imageId() {
    return this.imageId;
  }

  public InspectImageGetRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<InspectImageGetRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<InspectImageGetRequest> filter(Predicate<InspectImageGetRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<InspectImageGetRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<InspectImageGetRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public InspectImageGetRequest orElse(InspectImageGetRequest value) {
    return this.optional.orElse(value);
  }

  public InspectImageGetRequest orElseGet(Supplier<InspectImageGetRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> InspectImageGetRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
