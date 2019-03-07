package io.flexio.docker.api.types.optional;

import io.flexio.docker.api.types.Image;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalImage {
  private final Optional<Image> optional;

  private final Optional<String> id;

  private final OptionalValueList<String, Optional<String>> repoTags;

  private OptionalImage(Image value) {
    this.optional = Optional.ofNullable(value);
    this.id = Optional.ofNullable(value != null ? value.id() : null);
    this.repoTags = new OptionalValueList<>(value != null ? value.repoTags() : null, e -> Optional.ofNullable(e));
  }

  public static OptionalImage of(Image value) {
    return new OptionalImage(value);
  }

  public Optional<String> id() {
    return this.id;
  }

  public OptionalValueList<String, Optional<String>> repoTags() {
    return this.repoTags;
  }

  public Image get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<Image> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<Image> filter(Predicate<Image> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<Image, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<Image, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public Image orElse(Image value) {
    return this.optional.orElse(value);
  }

  public Image orElseGet(Supplier<Image> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> Image orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
