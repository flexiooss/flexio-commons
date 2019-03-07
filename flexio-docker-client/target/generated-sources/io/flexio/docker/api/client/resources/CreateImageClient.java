package io.flexio.docker.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import io.flexio.docker.api.CreateImagePostRequest;
import io.flexio.docker.api.CreateImagePostResponse;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import io.flexio.docker.api.createimagepostresponse.Status200;
import io.flexio.docker.api.createimagepostresponse.Status404;
import io.flexio.docker.api.createimagepostresponse.Status500;
import io.flexio.docker.api.types.json.ErrorReader;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import java.util.function.Consumer;
import org.codingmatters.rest.api.client.Requester;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.ResponseDelegate;
import org.codingmatters.rest.api.client.UrlProvider;

public class CreateImageClient implements DockerEngineAPIClient.Images.CreateImage {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  public CreateImageClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
  }

  public CreateImageClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public CreateImagePostResponse post(CreateImagePostRequest request) throws IOException {
    Requester requester = this.requesterFactory.create();
    if(request.fromImage() != null) {
      String fromImageRaw = request.fromImage();
      String fromImage = fromImageRaw;
      requester.parameter("fromImage", fromImage);
    }
    if(request.repo() != null) {
      String repoRaw = request.repo();
      String repo = repoRaw;
      requester.parameter("repo", repo);
    }
    if(request.tag() != null) {
      String tagRaw = request.tag();
      String tag = tagRaw;
      requester.parameter("tag", tag);
    }
    String path = "/images/create";
    requester.path(path);
    ResponseDelegate response = null;
    try {
      response = requester.post("application/json", new byte[0]);
      CreateImagePostResponse.Builder resp = CreateImagePostResponse.builder();
      if(response.code() == 200) {
        Status200.Builder responseBuilder = Status200.builder();
        resp.status200(responseBuilder.build());
      }
      if(response.code() == 404) {
        Status404.Builder responseBuilder = Status404.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ErrorReader().read(parser));
        }
        resp.status404(responseBuilder.build());
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

  public CreateImagePostResponse post(Consumer<CreateImagePostRequest.Builder> request) throws IOException {
    CreateImagePostRequest.Builder builder = CreateImagePostRequest.builder();
    if(request != null) {
      request.accept(builder);
    }
    return this.post(builder.build());
  }
}
