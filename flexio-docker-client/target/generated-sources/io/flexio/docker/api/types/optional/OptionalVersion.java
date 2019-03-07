package io.flexio.docker.api.types.optional;

import io.flexio.docker.api.types.Version;
import java.lang.Boolean;
import java.lang.String;
import java.lang.Throwable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OptionalVersion {
  private final Optional<Version> optional;

  private final Optional<String> version;

  private final Optional<String> apiVersion;

  private final Optional<String> minAPIVersion;

  private final Optional<String> gitCommit;

  private final Optional<String> goVersion;

  private final Optional<String> os;

  private final Optional<String> arch;

  private final Optional<String> kernelVersion;

  private final Optional<Boolean> experimental;

  private final Optional<String> buildTime;

  private OptionalVersion(Version value) {
    this.optional = Optional.ofNullable(value);
    this.version = Optional.ofNullable(value != null ? value.version() : null);
    this.apiVersion = Optional.ofNullable(value != null ? value.apiVersion() : null);
    this.minAPIVersion = Optional.ofNullable(value != null ? value.minAPIVersion() : null);
    this.gitCommit = Optional.ofNullable(value != null ? value.gitCommit() : null);
    this.goVersion = Optional.ofNullable(value != null ? value.goVersion() : null);
    this.os = Optional.ofNullable(value != null ? value.os() : null);
    this.arch = Optional.ofNullable(value != null ? value.arch() : null);
    this.kernelVersion = Optional.ofNullable(value != null ? value.kernelVersion() : null);
    this.experimental = Optional.ofNullable(value != null ? value.experimental() : null);
    this.buildTime = Optional.ofNullable(value != null ? value.buildTime() : null);
  }

  public static OptionalVersion of(Version value) {
    return new OptionalVersion(value);
  }

  public Optional<String> version() {
    return this.version;
  }

  public Optional<String> apiVersion() {
    return this.apiVersion;
  }

  public Optional<String> minAPIVersion() {
    return this.minAPIVersion;
  }

  public Optional<String> gitCommit() {
    return this.gitCommit;
  }

  public Optional<String> goVersion() {
    return this.goVersion;
  }

  public Optional<String> os() {
    return this.os;
  }

  public Optional<String> arch() {
    return this.arch;
  }

  public Optional<String> kernelVersion() {
    return this.kernelVersion;
  }

  public Optional<Boolean> experimental() {
    return this.experimental;
  }

  public Optional<String> buildTime() {
    return this.buildTime;
  }

  public Version get() {
    return this.optional.get();
  }

  public boolean isPresent() {
    return this.optional.isPresent();
  }

  public void ifPresent(Consumer<Version> consumer) {
    this.optional.ifPresent(consumer);
  }

  public Optional<Version> filter(Predicate<Version> predicate) {
    return this.optional.filter(predicate);
  }

  public <U> Optional<U> map(Function<Version, ? extends U> function) {
    return this.optional.map(function);
  }

  public <U> Optional<U> flatMap(Function<Version, Optional<U>> function) {
    return this.optional.flatMap(function);
  }

  public Version orElse(Version value) {
    return this.optional.orElse(value);
  }

  public Version orElseGet(Supplier<Version> supplier) {
    return this.optional.orElseGet(supplier);
  }

  public <X extends Throwable> Version orElseThrow(Supplier<? extends X> supplier) throws X {
    return this.optional.orElseThrow(supplier);
  }
}
