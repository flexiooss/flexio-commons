package io.flexio.docker.api.imagelistgetresponse.optional;

import io.flexio.docker.api.imagelistgetresponse.Status200;
import io.flexio.docker.api.optional.OptionalValueList;
import io.flexio.docker.api.types.Image;
import io.flexio.docker.api.types.optional.OptionalImage;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalStatus200 {
  private final Optional<Status200> optional;

  private final OptionalValueList<Image, OptionalImage> payload;

  private OptionalStatus200(Status200 value) {
    this.optional = Optional.ofNullable(value);
    this.payload = new OptionalValueList<>(value != null ? value.payload() : null, e -> OptionalImage.of(e));
  }

  public static OptionalStatus200 of(Status200 value) {
    return new OptionalStatus200(value);
  }

  public OptionalValueList<Image, OptionalImage> payload() {
    return this.payload;
  }

  public Status200 get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<Status200> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<Status200> filter(Predicate<Status200> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<Status200, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<Status200, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public Status200 orElse(Status200 value) {
    return this.optional.orElse(value);
  }

  public Status200 orElseGet(Supplier<Status200> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> Status200 orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
