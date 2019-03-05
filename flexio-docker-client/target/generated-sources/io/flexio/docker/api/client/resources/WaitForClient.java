package io.flexio.docker.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import io.flexio.docker.api.WaitForPostRequest;
import io.flexio.docker.api.WaitForPostResponse;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import io.flexio.docker.api.types.json.ErrorReader;
import io.flexio.docker.api.types.json.WaitResultReader;
import io.flexio.docker.api.waitforpostresponse.Status200;
import io.flexio.docker.api.waitforpostresponse.Status404;
import io.flexio.docker.api.waitforpostresponse.Status500;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import java.util.function.Consumer;
import org.codingmatters.rest.api.client.Requester;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.ResponseDelegate;
import org.codingmatters.rest.api.client.UrlProvider;

public class WaitForClient implements DockerEngineAPIClient.Containers.Container.WaitFor {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  public WaitForClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
  }

  public WaitForClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public WaitForPostResponse post(WaitForPostRequest request) throws IOException {
    Requester requester = this.requesterFactory.create();
    if(request.condition() != null) {
      String conditionRaw = request.condition();
      String condition = conditionRaw;
      requester.parameter("condition", condition);
    }
    String path = "/containers/{container-id}/wait";
    if(request.containerId() != null) {
      String containerIdRaw = request.containerId();
      String containerId = containerIdRaw;
      path = path.replaceFirst("\\{container-id\\}", containerId);
    }
    requester.path(path);
    ResponseDelegate response = null;
    try {
      response = requester.post("application/json", new byte[0]);
      WaitForPostResponse.Builder resp = WaitForPostResponse.builder();
      if(response.code() == 200) {
        Status200.Builder responseBuilder = Status200.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new WaitResultReader().read(parser));
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

  public WaitForPostResponse post(Consumer<WaitForPostRequest.Builder> request) throws IOException {
    WaitForPostRequest.Builder builder = WaitForPostRequest.builder();
    if(request != null) {
      request.accept(builder);
    }
    return this.post(builder.build());
  }
}
