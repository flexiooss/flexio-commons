package io.flexio.docker.api.optional;

import io.flexio.docker.api.CreateContainerPostRequest;
import io.flexio.docker.api.types.optional.OptionalContainerCreationData;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalCreateContainerPostRequest {
  private final Optional<CreateContainerPostRequest> optional;

  private final Optional<String> name;

  private OptionalContainerCreationData payload = this.payload;

  private OptionalCreateContainerPostRequest(CreateContainerPostRequest value) {
    this.optional = Optional.ofNullable(value);
    this.name = Optional.ofNullable(value != null ? value.name() : null);
  }

  public static OptionalCreateContainerPostRequest of(CreateContainerPostRequest value) {
    return new OptionalCreateContainerPostRequest(value);
  }

  public Optional<String> name() {
    return this.name;
  }

  public synchronized OptionalContainerCreationData payload() {
    if(this.payload == null) {
      this.payload = OptionalContainerCreationData.of(this.optional.isPresent() ? this.optional.get().payload() : null);
    }
    return this.payload;
  }

  public CreateContainerPostRequest get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<CreateContainerPostRequest> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<CreateContainerPostRequest> filter(Predicate<CreateContainerPostRequest> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<CreateContainerPostRequest, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<CreateContainerPostRequest, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public CreateContainerPostRequest orElse(CreateContainerPostRequest value) {
    return this.optional.orElse(value);
  }

  public CreateContainerPostRequest orElseGet(Supplier<CreateContainerPostRequest> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> CreateContainerPostRequest orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
