package io.flexio.docker.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import io.flexio.docker.api.VersionGetRequest;
import io.flexio.docker.api.VersionGetResponse;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import io.flexio.docker.api.types.json.VersionReader;
import io.flexio.docker.api.versiongetresponse.Status200;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import java.util.function.Consumer;
import org.codingmatters.rest.api.client.Requester;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.ResponseDelegate;
import org.codingmatters.rest.api.client.UrlProvider;

public class VersionClient implements DockerEngineAPIClient.Version {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  public VersionClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
  }

  public VersionClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public VersionGetResponse get(VersionGetRequest request) throws IOException {
    Requester requester = this.requesterFactory.create();
    String path = "/version";
    requester.path(path);
    ResponseDelegate response = null;
    try {
      response = requester.get();
      VersionGetResponse.Builder resp = VersionGetResponse.builder();
      if(response.code() == 200) {
        Status200.Builder responseBuilder = Status200.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new VersionReader().read(parser));
        }
        resp.status200(responseBuilder.build());
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

  public VersionGetResponse get(Consumer<VersionGetRequest.Builder> request) throws IOException {
    VersionGetRequest.Builder builder = VersionGetRequest.builder();
    if(request != null) {
      request.accept(builder);
    }
    return this.get(builder.build());
  }
}
