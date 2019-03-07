package io.flexio.docker.api.optional;

import io.flexio.docker.api.CreateImagePostRequest;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalCreateImagePostRequest {
  private final Optional<CreateImagePostRequest> optional;

  private final Optional<String> fromImage;

  private final Optional<String> repo;

  private final Optional<String> tag;

  private OptionalCreateImagePostRequest(CreateImagePostRequest value) {
    this.optional = Optional.ofNullable(value);
    this.fromImage = Optional.ofNullable(value != null ? value.fromImage() : null);
    this.repo = Optional.ofNullable(value != null ? value.repo() : null);
    this.tag = Optional.ofNullable(value != null ? value.tag() : null);
  }

  public static OptionalCreateImagePostRequest of(CreateImagePostRequest value) {
    return new OptionalCreateImagePostRequest(value);
  }

  public Optional<String> fromImage() {
    return this.fromImage;
  }

  public Optional<String> repo() {
    return this.repo;
  }

  public Optional<String> tag() {
    return this.tag;
  }

  public CreateImagePostRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<CreateImagePostRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<CreateImagePostRequest> filter(Predicate<CreateImagePostRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<CreateImagePostRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<CreateImagePostRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public CreateImagePostRequest orElse(CreateImagePostRequest value) {
    return this.optional.orElse(value);
  }

  public CreateImagePostRequest orElseGet(Supplier<CreateImagePostRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> CreateImagePostRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
