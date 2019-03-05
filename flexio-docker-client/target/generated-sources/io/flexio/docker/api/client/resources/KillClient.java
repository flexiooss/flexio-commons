package io.flexio.docker.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import io.flexio.docker.api.KillPostRequest;
import io.flexio.docker.api.KillPostResponse;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import io.flexio.docker.api.killpostresponse.Status204;
import io.flexio.docker.api.killpostresponse.Status309;
import io.flexio.docker.api.killpostresponse.Status404;
import io.flexio.docker.api.killpostresponse.Status500;
import io.flexio.docker.api.types.json.ErrorReader;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import java.util.function.Consumer;
import org.codingmatters.rest.api.client.Requester;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.ResponseDelegate;
import org.codingmatters.rest.api.client.UrlProvider;

public class KillClient implements DockerEngineAPIClient.Containers.Container.Kill {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  public KillClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
  }

  public KillClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public KillPostResponse post(KillPostRequest request) throws IOException {
    Requester requester = this.requesterFactory.create();
    if(request.signal() != null) {
      String signalRaw = request.signal();
      String signal = signalRaw;
      requester.parameter("signal", signal);
    }
    String path = "/containers/{container-id}/kill";
    if(request.containerId() != null) {
      String containerIdRaw = request.containerId();
      String containerId = containerIdRaw;
      path = path.replaceFirst("\\{container-id\\}", containerId);
    }
    requester.path(path);
    ResponseDelegate response = null;
    try {
      response = requester.post("application/json", new byte[0]);
      KillPostResponse.Builder resp = KillPostResponse.builder();
      if(response.code() == 204) {
        Status204.Builder responseBuilder = Status204.builder();
        resp.status204(responseBuilder.build());
      }
      if(response.code() == 309) {
        Status309.Builder responseBuilder = Status309.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ErrorReader().read(parser));
        }
        resp.status309(responseBuilder.build());
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

  public KillPostResponse post(Consumer<KillPostRequest.Builder> request) throws IOException {
    KillPostRequest.Builder builder = KillPostRequest.builder();
    if(request != null) {
      request.accept(builder);
    }
    return this.post(builder.build());
  }
}
