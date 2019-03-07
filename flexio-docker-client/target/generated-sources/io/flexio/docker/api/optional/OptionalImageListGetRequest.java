package io.flexio.docker.api.optional;

import io.flexio.docker.api.ImageListGetRequest;
import java.lang.Boolean;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalImageListGetRequest {
  private final Optional<ImageListGetRequest> optional;

  private final Optional<Boolean> all;

  private final Optional<Boolean> digests;

  private OptionalImageListGetRequest(ImageListGetRequest value) {
    this.optional = Optional.ofNullable(value);
    this.all = Optional.ofNullable(value != null ? value.all() : null);
    this.digests = Optional.ofNullable(value != null ? value.digests() : null);
  }

  public static OptionalImageListGetRequest of(ImageListGetRequest value) {
    return new OptionalImageListGetRequest(value);
  }

  public Optional<Boolean> all() {
    return this.all;
  }

  public Optional<Boolean> digests() {
    return this.digests;
  }

  public ImageListGetRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ImageListGetRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ImageListGetRequest> filter(Predicate<ImageListGetRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ImageListGetRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ImageListGetRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ImageListGetRequest orElse(ImageListGetRequest value) {
    return this.optional.orElse(value);
  }

  public ImageListGetRequest orElseGet(Supplier<ImageListGetRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ImageListGetRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
