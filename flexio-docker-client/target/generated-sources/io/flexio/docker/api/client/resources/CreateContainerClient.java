package io.flexio.docker.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import io.flexio.docker.api.CreateContainerPostRequest;
import io.flexio.docker.api.CreateContainerPostResponse;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import io.flexio.docker.api.createcontainerpostresponse.Status201;
import io.flexio.docker.api.createcontainerpostresponse.Status400;
import io.flexio.docker.api.createcontainerpostresponse.Status404;
import io.flexio.docker.api.createcontainerpostresponse.Status409;
import io.flexio.docker.api.createcontainerpostresponse.Status500;
import io.flexio.docker.api.types.json.ContainerCreationDataWriter;
import io.flexio.docker.api.types.json.ContainerCreationResultReader;
import io.flexio.docker.api.types.json.ErrorReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import java.util.function.Consumer;
import org.codingmatters.rest.api.client.Requester;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.ResponseDelegate;
import org.codingmatters.rest.api.client.UrlProvider;

public class CreateContainerClient implements DockerEngineAPIClient.Containers.CreateContainer {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  public CreateContainerClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
  }

  public CreateContainerClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public CreateContainerPostResponse post(CreateContainerPostRequest request) throws IOException {
    Requester requester = this.requesterFactory.create();
    if(request.name() != null) {
      String nameRaw = request.name();
      String name = nameRaw;
      requester.parameter("name", name);
    }
    String path = "/containers/create";
    requester.path(path);
    ResponseDelegate response = null;
    try {
      byte[] requestBody = new byte[0];
      if(request.payload() != null) {
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
          try(JsonGenerator generator = this.jsonFactory.createGenerator(out)) {
            new ContainerCreationDataWriter().write(generator, request.payload());
          }
          requestBody = out.toByteArray();
        }
      }
      String contentType = "application/json";
      response = requester.post(contentType, requestBody);
      CreateContainerPostResponse.Builder resp = CreateContainerPostResponse.builder();
      if(response.code() == 201) {
        Status201.Builder responseBuilder = Status201.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ContainerCreationResultReader().read(parser));
        }
        resp.status201(responseBuilder.build());
      }
      if(response.code() == 400) {
        Status400.Builder responseBuilder = Status400.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ErrorReader().read(parser));
        }
        resp.status400(responseBuilder.build());
      }
      if(response.code() == 404) {
        Status404.Builder responseBuilder = Status404.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ErrorReader().read(parser));
        }
        resp.status404(responseBuilder.build());
      }
      if(response.code() == 409) {
        Status409.Builder responseBuilder = Status409.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ErrorReader().read(parser));
        }
        resp.status409(responseBuilder.build());
      }
      if(response.code() == 500) {
        Status500.Builder responseBuilder = Status500.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ErrorReader().read(parser));
        }
        resp.status500(responseBuilder.build());
      }
      return resp.build();
    } finally {
      try {
        if(response != null) {
          response.close();
        }
      } catch(Exception e) {
        throw new IOException("error closing response", e);
      }
    }
  }

  public CreateContainerPostResponse post(Consumer<CreateContainerPostRequest.Builder> request) throws IOException {
    CreateContainerPostRequest.Builder builder = CreateContainerPostRequest.builder();
    if(request != null) {
      request.accept(builder);
    }
    return this.post(builder.build());
  }
}
