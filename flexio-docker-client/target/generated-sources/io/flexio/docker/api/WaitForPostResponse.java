package io.flexio.docker.api;

import io.flexio.docker.api.optional.OptionalWaitForPostResponse;
import io.flexio.docker.api.waitforpostresponse.Status200;
import io.flexio.docker.api.waitforpostresponse.Status404;
import io.flexio.docker.api.waitforpostresponse.Status500;
import java.util.function.Consumer;

public interface WaitForPostResponse {
  static Builder builder() {
    return new WaitForPostResponse.Builder();
  }

  static WaitForPostResponse.Builder from(WaitForPostResponse value) {
    if(value != null) {
      return new WaitForPostResponse.Builder()
          .status200(value.status200())
          .status404(value.status404())
          .status500(value.status500())
          ;
    }
    else {
      return null;
    }
  }

  Status200 status200();

  Status404 status404();

  Status500 status500();

  WaitForPostResponse withStatus200(Status200 value);

  WaitForPostResponse withStatus404(Status404 value);

  WaitForPostResponse withStatus500(Status500 value);

  int hashCode();

  WaitForPostResponse changed(WaitForPostResponse.Changer changer);

  OptionalWaitForPostResponse opt();

  class Builder {
    private Status200 status200;

    private Status404 status404;

    private Status500 status500;

    public WaitForPostResponse build() {
      return new WaitForPostResponseImpl(this.status200, this.status404, this.status500);
    }

    public WaitForPostResponse.Builder status200(Status200 status200) {
      this.status200 = status200;
      return this;
    }

    public WaitForPostResponse.Builder status200(Consumer<Status200.Builder> status200) {
      Status200.Builder builder = Status200.builder();
      status200.accept(builder);
      return this.status200(builder.build());
    }

    public WaitForPostResponse.Builder status404(Status404 status404) {
      this.status404 = status404;
      return this;
    }

    public WaitForPostResponse.Builder status404(Consumer<Status404.Builder> status404) {
      Status404.Builder builder = Status404.builder();
      status404.accept(builder);
      return this.status404(builder.build());
    }

    public WaitForPostResponse.Builder status500(Status500 status500) {
      this.status500 = status500;
      return this;
    }

    public WaitForPostResponse.Builder status500(Consumer<Status500.Builder> status500) {
      Status500.Builder builder = Status500.builder();
      status500.accept(builder);
      return this.status500(builder.build());
    }
  }

  interface Changer {
    WaitForPostResponse.Builder configure(WaitForPostResponse.Builder builder);
  }
}
