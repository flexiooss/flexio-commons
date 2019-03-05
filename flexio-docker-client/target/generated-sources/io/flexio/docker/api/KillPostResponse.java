package io.flexio.docker.api;

import io.flexio.docker.api.killpostresponse.Status204;
import io.flexio.docker.api.killpostresponse.Status309;
import io.flexio.docker.api.killpostresponse.Status404;
import io.flexio.docker.api.killpostresponse.Status500;
import io.flexio.docker.api.optional.OptionalKillPostResponse;
import java.util.function.Consumer;

public interface KillPostResponse {
  static Builder builder() {
    return new KillPostResponse.Builder();
  }

  static KillPostResponse.Builder from(KillPostResponse value) {
    if(value != null) {
      return new KillPostResponse.Builder()
          .status204(value.status204())
          .status309(value.status309())
          .status404(value.status404())
          .status500(value.status500())
          ;
    }
    else {
      return null;
    }
  }

  Status204 status204();

  Status309 status309();

  Status404 status404();

  Status500 status500();

  KillPostResponse withStatus204(Status204 value);

  KillPostResponse withStatus309(Status309 value);

  KillPostResponse withStatus404(Status404 value);

  KillPostResponse withStatus500(Status500 value);

  int hashCode();

  KillPostResponse changed(KillPostResponse.Changer changer);

  OptionalKillPostResponse opt();

  class Builder {
    private Status204 status204;

    private Status309 status309;

    private Status404 status404;

    private Status500 status500;

    public KillPostResponse build() {
      return new KillPostResponseImpl(this.status204, this.status309, this.status404, this.status500);
    }

    public KillPostResponse.Builder status204(Status204 status204) {
      this.status204 = status204;
      return this;
    }

    public KillPostResponse.Builder status204(Consumer<Status204.Builder> status204) {
      Status204.Builder builder = Status204.builder();
      status204.accept(builder);
      return this.status204(builder.build());
    }

    public KillPostResponse.Builder status309(Status309 status309) {
      this.status309 = status309;
      return this;
    }

    public KillPostResponse.Builder status309(Consumer<Status309.Builder> status309) {
      Status309.Builder builder = Status309.builder();
      status309.accept(builder);
      return this.status309(builder.build());
    }

    public KillPostResponse.Builder status404(Status404 status404) {
      this.status404 = status404;
      return this;
    }

    public KillPostResponse.Builder status404(Consumer<Status404.Builder> status404) {
      Status404.Builder builder = Status404.builder();
      status404.accept(builder);
      return this.status404(builder.build());
    }

    public KillPostResponse.Builder status500(Status500 status500) {
      this.status500 = status500;
      return this;
    }

    public KillPostResponse.Builder status500(Consumer<Status500.Builder> status500) {
      Status500.Builder builder = Status500.builder();
      status500.accept(builder);
      return this.status500(builder.build());
    }
  }

  interface Changer {
    KillPostResponse.Builder configure(KillPostResponse.Builder builder);
  }
}
