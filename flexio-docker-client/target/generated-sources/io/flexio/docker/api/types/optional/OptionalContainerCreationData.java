package io.flexio.docker.api.types.optional;

import io.flexio.docker.api.types.ContainerCreationData;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalContainerCreationData {
  private final Optional<ContainerCreationData> optional;

  private final Optional<String> image;

  private final OptionalValueList<String, Optional<String>> cmd;

  private OptionalContainerCreationData(ContainerCreationData value) {
    this.optional = Optional.ofNullable(value);
    this.image = Optional.ofNullable(value != null ? value.image() : null);
    this.cmd = new OptionalValueList<>(value != null ? value.cmd() : null, e -> Optional.ofNullable(e));
  }

  public static OptionalContainerCreationData of(ContainerCreationData value) {
    return new OptionalContainerCreationData(value);
  }

  public Optional<String> image() {
    return this.image;
  }

  public OptionalValueList<String, Optional<String>> cmd() {
    return this.cmd;
  }

  public ContainerCreationData get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<ContainerCreationData> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<ContainerCreationData> filter(Predicate<ContainerCreationData> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<ContainerCreationData, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<ContainerCreationData, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public ContainerCreationData orElse(ContainerCreationData value) {
    return this.optional.orElse(value);
  }

  public ContainerCreationData orElseGet(Supplier<ContainerCreationData> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> ContainerCreationData orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
