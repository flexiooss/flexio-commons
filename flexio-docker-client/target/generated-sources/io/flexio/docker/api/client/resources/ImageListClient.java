package io.flexio.docker.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import io.flexio.docker.api.ImageListGetRequest;
import io.flexio.docker.api.ImageListGetResponse;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import io.flexio.docker.api.imagelistgetresponse.Status200;
import io.flexio.docker.api.imagelistgetresponse.Status500;
import io.flexio.docker.api.types.json.ErrorReader;
import io.flexio.docker.api.types.json.ImageReader;
import java.io.IOException;
import java.lang.Boolean;
import java.lang.Exception;
import java.lang.String;
import java.util.function.Consumer;
import org.codingmatters.rest.api.client.Requester;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.ResponseDelegate;
import org.codingmatters.rest.api.client.UrlProvider;

public class ImageListClient implements DockerEngineAPIClient.Images.ImageList {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  public ImageListClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
  }

  public ImageListClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public ImageListGetResponse get(ImageListGetRequest request) throws IOException {
    Requester requester = this.requesterFactory.create();
    if(request.all() != null) {
      Boolean allRaw = request.all();
      String all = allRaw != null ? allRaw.toString() : null;
      requester.parameter("all", all);
    }
    if(request.digests() != null) {
      Boolean digestsRaw = request.digests();
      String digests = digestsRaw != null ? digestsRaw.toString() : null;
      requester.parameter("digests", digests);
    }
    String path = "/images/json";
    requester.path(path);
    ResponseDelegate response = null;
    try {
      response = requester.get();
      ImageListGetResponse.Builder resp = ImageListGetResponse.builder();
      if(response.code() == 200) {
        Status200.Builder responseBuilder = Status200.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ImageReader().readArray(parser));
        }
        resp.status200(responseBuilder.build());
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

  public ImageListGetResponse get(Consumer<ImageListGetRequest.Builder> request) throws IOException {
    ImageListGetRequest.Builder builder = ImageListGetRequest.builder();
    if(request != null) {
      request.accept(builder);
    }
    return this.get(builder.build());
  }
}
