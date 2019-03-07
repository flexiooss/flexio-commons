package io.flexio.docker.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import io.flexio.docker.api.InspectGetRequest;
import io.flexio.docker.api.InspectGetResponse;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import io.flexio.docker.api.inspectgetresponse.Status200;
import io.flexio.docker.api.inspectgetresponse.Status404;
import io.flexio.docker.api.inspectgetresponse.Status500;
import io.flexio.docker.api.types.json.ContainerReader;
import io.flexio.docker.api.types.json.ErrorReader;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import java.util.function.Consumer;
import org.codingmatters.rest.api.client.Requester;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.ResponseDelegate;
import org.codingmatters.rest.api.client.UrlProvider;

public class InspectClient implements DockerEngineAPIClient.Containers.Container.Inspect {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  public InspectClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
  }

  public InspectClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public InspectGetResponse get(InspectGetRequest request) throws IOException {
    Requester requester = this.requesterFactory.create();
    String path = "/containers/{container-id}/json";
    if(request.containerId() != null) {
      String containerIdRaw = request.containerId();
      String containerId = containerIdRaw;
      path = path.replaceFirst("\\{container-id\\}", containerId);
    }
    requester.path(path);
    ResponseDelegate response = null;
    try {
      response = requester.get();
      InspectGetResponse.Builder resp = InspectGetResponse.builder();
      if(response.code() == 200) {
        Status200.Builder responseBuilder = Status200.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ContainerReader().read(parser));
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

  public InspectGetResponse get(Consumer<InspectGetRequest.Builder> request) throws IOException {
    InspectGetRequest.Builder builder = InspectGetRequest.builder();
    if(request != null) {
      request.accept(builder);
    }
    return this.get(builder.build());
  }
}
