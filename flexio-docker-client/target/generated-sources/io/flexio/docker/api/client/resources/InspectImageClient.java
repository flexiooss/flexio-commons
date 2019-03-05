package io.flexio.docker.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import io.flexio.docker.api.InspectImageGetRequest;
import io.flexio.docker.api.InspectImageGetResponse;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import io.flexio.docker.api.inspectimagegetresponse.Status200;
import io.flexio.docker.api.inspectimagegetresponse.Status404;
import io.flexio.docker.api.inspectimagegetresponse.Status500;
import io.flexio.docker.api.types.json.ErrorReader;
import io.flexio.docker.api.types.json.ImageReader;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import java.util.function.Consumer;
import org.codingmatters.rest.api.client.Requester;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.ResponseDelegate;
import org.codingmatters.rest.api.client.UrlProvider;

public class InspectImageClient implements DockerEngineAPIClient.Images.InspectImage {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  public InspectImageClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
  }

  public InspectImageClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public InspectImageGetResponse get(InspectImageGetRequest request) throws IOException {
    Requester requester = this.requesterFactory.create();
    String path = "/images/{image-id}/json";
    if(request.imageId() != null) {
      String imageIdRaw = request.imageId();
      String imageId = imageIdRaw;
      path = path.replaceFirst("\\{image-id\\}", imageId);
    }
    requester.path(path);
    ResponseDelegate response = null;
    try {
      response = requester.get();
      InspectImageGetResponse.Builder resp = InspectImageGetResponse.builder();
      if(response.code() == 200) {
        Status200.Builder responseBuilder = Status200.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ImageReader().read(parser));
        }
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

  public InspectImageGetResponse get(Consumer<InspectImageGetRequest.Builder> request) throws IOException {
    InspectImageGetRequest.Builder builder = InspectImageGetRequest.builder();
    if(request != null) {
      request.accept(builder);
    }
    return this.get(builder.build());
  }
}
