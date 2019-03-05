package io.flexio.docker.api.types.optional;

import io.flexio.docker.api.types.ContainerInList;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalContainerInList {
  private final Optional<ContainerInList> optional;

  private final Optional<String> id;

  private final OptionalValueList<String, Optional<String>> names;

  private final Optional<String> image;

  private final Optional<String> state;

  private final Optional<String> status;

  private OptionalContainerInList(ContainerInList value) {
    this.optional = Optional.ofNullable(value);
    this.id = Optional.ofNullable(value != null ? value.id() : null);
    this.names = new OptionalValueList<>(value != null ? value.names() : null, e -> Optional.ofNullable(e));
    this.image = Optional.ofNullable(value != null ? value.image() : null);
    this.state = Optional.ofNullable(value != null ? value.state() : null);
    this.status = Optional.ofNullable(value != null ? value.status() : null);
  }

  public static OptionalContainerInList of(ContainerInList value) {
    return new OptionalContainerInList(value);
  }

  public Optional<String> id() {
    return this.id;
  }

  public OptionalValueList<String, Optional<String>> names() {
    return this.names;
  }

  public Optional<String> image() {
    return this.image;
  }

  public Optional<String> state() {
    return this.state;
  }

  public Optional<String> status() {
    return this.status;
  }

  public ContainerInList get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ContainerInList> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ContainerInList> filter(Predicate<ContainerInList> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ContainerInList, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ContainerInList, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ContainerInList orElse(ContainerInList value) {
    return this.optional.orElse(value);
  }

  public ContainerInList orElseGet(Supplier<ContainerInList> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ContainerInList orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
