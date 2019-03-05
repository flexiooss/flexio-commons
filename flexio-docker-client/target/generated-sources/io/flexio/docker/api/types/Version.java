package io.flexio.docker.api.types;

import io.flexio.docker.api.types.optional.OptionalVersion;
import java.lang.Boolean;
import java.lang.String;

public interface Version {
  static Builder builder() {
    return new Version.Builder();
  }

  static Version.Builder from(Version value) {
    if(value != null) {
      return new Version.Builder()
          .version(value.version())
          .apiVersion(value.apiVersion())
          .minAPIVersion(value.minAPIVersion())
          .gitCommit(value.gitCommit())
          .goVersion(value.goVersion())
          .os(value.os())
          .arch(value.arch())
          .kernelVersion(value.kernelVersion())
          .experimental(value.experimental())
          .buildTime(value.buildTime())
          ;
    }
    else {
      return null;
    }
  }

  String version();

  String apiVersion();

  String minAPIVersion();

  String gitCommit();

  String goVersion();

  String os();

  String arch();

  String kernelVersion();

  Boolean experimental();

  String buildTime();

  Version withVersion(String value);

  Version withApiVersion(String value);

  Version withMinAPIVersion(String value);

  Version withGitCommit(String value);

  Version withGoVersion(String value);

  Version withOs(String value);

  Version withArch(String value);

  Version withKernelVersion(String value);

  Version withExperimental(Boolean value);

  Version withBuildTime(String value);

  int hashCode();

  Version changed(Version.Changer changer);

  OptionalVersion opt();

  class Builder {
    private String version;

    private String apiVersion;

    private String minAPIVersion;

    private String gitCommit;

    private String goVersion;

    private String os;

    private String arch;

    private String kernelVersion;

    private Boolean experimental;

    private String buildTime;

    public Version build() {
      return new VersionImpl(this.version, this.apiVersion, this.minAPIVersion, this.gitCommit, this.goVersion, this.os, this.arch, this.kernelVersion, this.experimental, this.buildTime);
    }

    public Version.Builder version(String version) {
      this.version = version;
      return this;
    }

    public Version.Builder apiVersion(String apiVersion) {
      this.apiVersion = apiVersion;
      return this;
    }

    public Version.Builder minAPIVersion(String minAPIVersion) {
      this.minAPIVersion = minAPIVersion;
      return this;
    }

    public Version.Builder gitCommit(String gitCommit) {
      this.gitCommit = gitCommit;
      return this;
    }

    public Version.Builder goVersion(String goVersion) {
      this.goVersion = goVersion;
      return this;
    }

    public Version.Builder os(String os) {
      this.os = os;
      return this;
    }

    public Version.Builder arch(String arch) {
      this.arch = arch;
      return this;
    }

    public Version.Builder kernelVersion(String kernelVersion) {
      this.kernelVersion = kernelVersion;
      return this;
    }

    public Version.Builder experimental(Boolean experimental) {
      this.experimental = experimental;
      return this;
    }

    public Version.Builder buildTime(String buildTime) {
      this.buildTime = buildTime;
      return this;
    }
  }

  interface Changer {
    Version.Builder configure(Version.Builder builder);
  }
}
