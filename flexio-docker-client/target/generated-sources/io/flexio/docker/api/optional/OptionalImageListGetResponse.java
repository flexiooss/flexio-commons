package io.flexio.docker.api.optional;

import io.flexio.docker.api.ImageListGetResponse;
import io.flexio.docker.api.imagelistgetresponse.optional.OptionalStatus200;
import io.flexio.docker.api.imagelistgetresponse.optional.OptionalStatus500;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalImageListGetResponse {
  private final Optional<ImageListGetResponse> optional;

  private OptionalStatus200 status200 = this.status200;

  private OptionalStatus500 status500 = this.status500;

  private OptionalImageListGetResponse(ImageListGetResponse value) {
    this.optional = Optional.ofNullable(value);
  }

  public static OptionalImageListGetResponse of(ImageListGetResponse value) {
    return new OptionalImageListGetResponse(value);
  }

  public synchronized OptionalStatus200 status200() {
    if(this.status200 == null) {
      this.status200 = OptionalStatus200.of(this.optional.isPresent() ? this.optional.get().status200() : null);
    }
    return this.status200;
  }

  public synchronized OptionalStatus500 status500() {
    if(this.status500 == null) {
      this.status500 = OptionalStatus500.of(this.optional.isPresent() ? this.optional.get().status500() : null);
    }
    return this.status500;
  }

  public ImageListGetResponse get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ImageListGetResponse> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ImageListGetResponse> filter(Predicate<ImageListGetResponse> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ImageListGetResponse, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ImageListGetResponse, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ImageListGetResponse orElse(ImageListGetResponse value) {
    return this.optional.orElse(value);
  }

  public ImageListGetResponse orElseGet(Supplier<ImageListGetResponse> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ImageListGetResponse orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
