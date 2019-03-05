package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalStartPostResponse;
import io.flexio.docker.api.startpostresponse.Status204;
import io.flexio.docker.api.startpostresponse.Status309;
import io.flexio.docker.api.startpostresponse.Status404;
import io.flexio.docker.api.startpostresponse.Status500;
import java.util.function.Consumer;

public interface StartPostResponse {
  static Builder builder() {
    return new StartPostResponse.Builder();
  }

  static StartPostResponse.Builder from(StartPostResponse value) {
    if(value != null) {
      return new StartPostResponse.Builder()
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

  StartPostResponse withStatus204(Status204 value);

  StartPostResponse withStatus309(Status309 value);

  StartPostResponse withStatus404(Status404 value);

  StartPostResponse withStatus500(Status500 value);

  int hashCode();

  StartPostResponse changed(StartPostResponse.Changer changer);

  OptionalStartPostResponse opt();

  class Builder {
    private Status204 status204;

    private Status309 status309;

    private Status404 status404;

    private Status500 status500;

    public StartPostResponse build() {
      return new StartPostResponseImpl(this.status204, this.status309, this.status404, this.status500);
    }

    public StartPostResponse.Builder status204(Status204 status204) {
      this.status204 = status204;
      return this;
    }

    public StartPostResponse.Builder status204(Consumer<Status204.Builder> status204) {
      Status204.Builder builder = Status204.builder();
      status204.accept(builder);
      return this.status204(builder.build());
    }

    public StartPostResponse.Builder status309(Status309 status309) {
      this.status309 = status309;
      return this;
    }

    public StartPostResponse.Builder status309(Consumer<Status309.Builder> status309) {
      Status309.Builder builder = Status309.builder();
      status309.accept(builder);
      return this.status309(builder.build());
    }

    public StartPostResponse.Builder status404(Status404 status404) {
      this.status404 = status404;
      return this;
    }

    public StartPostResponse.Builder status404(Consumer<Status404.Builder> status404) {
      Status404.Builder builder = Status404.builder();
      status404.accept(builder);
      return this.status404(builder.build());
    }

    public StartPostResponse.Builder status500(Status500 status500) {
      this.status500 = status500;
      return this;
    }

    public StartPostResponse.Builder status500(Consumer<Status500.Builder> status500) {
      Status500.Builder builder = Status500.builder();
      status500.accept(builder);
      return this.status500(builder.build());
    }
  }

  interface Changer {
    StartPostResponse.Builder configure(StartPostResponse.Builder builder);
  }
}
