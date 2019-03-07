package io.flexio.docker.api;

import io.flexio.docker.api.createcontainerpostresponse.Status201;
import io.flexio.docker.api.createcontainerpostresponse.Status400;
import io.flexio.docker.api.createcontainerpostresponse.Status404;
import io.flexio.docker.api.createcontainerpostresponse.Status409;
import io.flexio.docker.api.createcontainerpostresponse.Status500;
import io.flexio.docker.api.optional.OptionalCreateContainerPostResponse;
import java.util.function.Consumer;

public interface CreateContainerPostResponse {
  static Builder builder() {
    return new CreateContainerPostResponse.Builder();
  }

  static CreateContainerPostResponse.Builder from(CreateContainerPostResponse value) {
    if(value != null) {
      return new CreateContainerPostResponse.Builder()
          .status201(value.status201())
          .status400(value.status400())
          .status404(value.status404())
          .status409(value.status409())
          .status500(value.status500())
          ;
    }
    else {
      return null;
    }
  }

  Status201 status201();

  Status400 status400();

  Status404 status404();

  Status409 status409();

  Status500 status500();

  CreateContainerPostResponse withStatus201(Status201 value);

  CreateContainerPostResponse withStatus400(Status400 value);

  CreateContainerPostResponse withStatus404(Status404 value);

  CreateContainerPostResponse withStatus409(Status409 value);

  CreateContainerPostResponse withStatus500(Status500 value);

  int hashCode();

  CreateContainerPostResponse changed(CreateContainerPostResponse.Changer changer);

  OptionalCreateContainerPostResponse opt();

  class Builder {
    private Status201 status201;

    private Status400 status400;

    private Status404 status404;

    private Status409 status409;

    private Status500 status500;

    public CreateContainerPostResponse build() {
      return new CreateContainerPostResponseImpl(this.status201, this.status400, this.status404, this.status409, this.status500);
    }

    public CreateContainerPostResponse.Builder status201(Status201 status201) {
      this.status201 = status201;
      return this;
    }

    public CreateContainerPostResponse.Builder status201(Consumer<Status201.Builder> status201) {
      Status201.Builder builder = Status201.builder();
      status201.accept(builder);
      return this.status201(builder.build());
    }

    public CreateContainerPostResponse.Builder status400(Status400 status400) {
      this.status400 = status400;
      return this;
    }

    public CreateContainerPostResponse.Builder status400(Consumer<Status400.Builder> status400) {
      Status400.Builder builder = Status400.builder();
      status400.accept(builder);
      return this.status400(builder.build());
    }

    public CreateContainerPostResponse.Builder status404(Status404 status404) {
      this.status404 = status404;
      return this;
    }

    public CreateContainerPostResponse.Builder status404(Consumer<Status404.Builder> status404) {
      Status404.Builder builder = Status404.builder();
      status404.accept(builder);
      return this.status404(builder.build());
    }

    public CreateContainerPostResponse.Builder status409(Status409 status409) {
      this.status409 = status409;
      return this;
    }

    public CreateContainerPostResponse.Builder status409(Consumer<Status409.Builder> status409) {
      Status409.Builder builder = Status409.builder();
      status409.accept(builder);
      return this.status409(builder.build());
    }

    public CreateContainerPostResponse.Builder status500(Status500 status500) {
      this.status500 = status500;
      return this;
    }

    public CreateContainerPostResponse.Builder status500(Consumer<Status500.Builder> status500) {
      Status500.Builder builder = Status500.builder();
      status500.accept(builder);
      return this.status500(builder.build());
    }
  }

  interface Changer {
    CreateContainerPostResponse.Builder configure(CreateContainerPostResponse.Builder builder);
  }
}
