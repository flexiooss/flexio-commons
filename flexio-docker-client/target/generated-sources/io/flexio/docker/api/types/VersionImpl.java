package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalVersion;
import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class VersionImpl implements Version {
  private final String version;

  private final String apiVersion;

  private final String minAPIVersion;

  private final String gitCommit;

  private final String goVersion;

  private final String os;

  private final String arch;

  private final String kernelVersion;

  private final Boolean experimental;

  private final String buildTime;

  VersionImpl(String version, String apiVersion, String minAPIVersion, String gitCommit, String goVersion, String os, String arch, String kernelVersion, Boolean experimental, String buildTime) {
    this.version = version;
    this.apiVersion = apiVersion;
    this.minAPIVersion = minAPIVersion;
    this.gitCommit = gitCommit;
    this.goVersion = goVersion;
    this.os = os;
    this.arch = arch;
    this.kernelVersion = kernelVersion;
    this.experimental = experimental;
    this.buildTime = buildTime;
  }

  public String version() {
    return this.version;
  }

  public String apiVersion() {
    return this.apiVersion;
  }

  public String minAPIVersion() {
    return this.minAPIVersion;
  }

  public String gitCommit() {
    return this.gitCommit;
  }

  public String goVersion() {
    return this.goVersion;
  }

  public String os() {
    return this.os;
  }

  public String arch() {
    return this.arch;
  }

  public String kernelVersion() {
    return this.kernelVersion;
  }

  public Boolean experimental() {
    return this.experimental;
  }

  public String buildTime() {
    return this.buildTime;
  }

  public Version withVersion(String value) {
    return Version.from(this).version(value).build();
  }

  public Version withApiVersion(String value) {
    return Version.from(this).apiVersion(value).build();
  }

  public Version withMinAPIVersion(String value) {
    return Version.from(this).minAPIVersion(value).build();
  }

  public Version withGitCommit(String value) {
    return Version.from(this).gitCommit(value).build();
  }

  public Version withGoVersion(String value) {
    return Version.from(this).goVersion(value).build();
  }

  public Version withOs(String value) {
    return Version.from(this).os(value).build();
  }

  public Version withArch(String value) {
    return Version.from(this).arch(value).build();
  }

  public Version withKernelVersion(String value) {
    return Version.from(this).kernelVersion(value).build();
  }

  public Version withExperimental(Boolean value) {
    return Version.from(this).experimental(value).build();
  }

  public Version withBuildTime(String value) {
    return Version.from(this).buildTime(value).build();
  }

  public Version changed(Version.Changer changer) {
    return changer.configure(Version.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    VersionImpl that = (VersionImpl) o;
        return Objects.equals(this.version, that.version) && 
        Objects.equals(this.apiVersion, that.apiVersion) && 
        Objects.equals(this.minAPIVersion, that.minAPIVersion) && 
        Objects.equals(this.gitCommit, that.gitCommit) && 
        Objects.equals(this.goVersion, that.goVersion) && 
        Objects.equals(this.os, that.os) && 
        Objects.equals(this.arch, that.arch) && 
        Objects.equals(this.kernelVersion, that.kernelVersion) && 
        Objects.equals(this.experimental, that.experimental) && 
        Objects.equals(this.buildTime, that.buildTime);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.version, this.apiVersion, this.minAPIVersion, this.gitCommit, this.goVersion, this.os, this.arch, this.kernelVersion, this.experimental, this.buildTime});
  }

  @Override
  public String toString() {
    return "Version{" +
        "version=" + Objects.toString(this.version) +
        ", " + "apiVersion=" + Objects.toString(this.apiVersion) +
        ", " + "minAPIVersion=" + Objects.toString(this.minAPIVersion) +
        ", " + "gitCommit=" + Objects.toString(this.gitCommit) +
        ", " + "goVersion=" + Objects.toString(this.goVersion) +
        ", " + "os=" + Objects.toString(this.os) +
        ", " + "arch=" + Objects.toString(this.arch) +
        ", " + "kernelVersion=" + Objects.toString(this.kernelVersion) +
        ", " + "experimental=" + Objects.toString(this.experimental) +
        ", " + "buildTime=" + Objects.toString(this.buildTime) +
        '}';
  }

  public OptionalVersion opt() {
    return OptionalVersion.of(this);
  }
}
