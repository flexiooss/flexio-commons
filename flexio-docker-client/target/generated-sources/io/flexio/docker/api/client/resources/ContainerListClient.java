package io.flexio.docker.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import io.flexio.docker.api.ContainerListGetRequest;
import io.flexio.docker.api.ContainerListGetResponse;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import io.flexio.docker.api.containerlistgetresponse.Status200;
import io.flexio.docker.api.containerlistgetresponse.Status400;
import io.flexio.docker.api.containerlistgetresponse.Status500;
import io.flexio.docker.api.types.json.ContainerInListReader;
import io.flexio.docker.api.types.json.ErrorReader;
import java.io.IOException;
import java.lang.Boolean;
import java.lang.Exception;
import java.lang.Long;
import java.lang.String;
import java.util.function.Consumer;
import org.codingmatters.rest.api.client.Requester;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.ResponseDelegate;
import org.codingmatters.rest.api.client.UrlProvider;

public class ContainerListClient implements DockerEngineAPIClient.Containers.ContainerList {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  public ContainerListClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
  }

  public ContainerListClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public ContainerListGetResponse get(ContainerListGetRequest request) throws IOException {
    Requester requester = this.requesterFactory.create();
    if(request.all() != null) {
      Boolean allRaw = request.all();
      String all = allRaw != null ? allRaw.toString() : null;
      requester.parameter("all", all);
    }
    if(request.limit() != null) {
      Long limitRaw = request.limit();
      String limit = limitRaw != null ? limitRaw.toString() : null;
      requester.parameter("limit", limit);
    }
    if(request.size() != null) {
      Long sizeRaw = request.size();
      String size = sizeRaw != null ? sizeRaw.toString() : null;
      requester.parameter("size", size);
    }
    if(request.filters() != null) {
      String filtersRaw = request.filters();
      String filters = filtersRaw;
      requester.parameter("filters", filters);
    }
    String path = "/containers/json";
    requester.path(path);
    ResponseDelegate response = null;
    try {
      response = requester.get();
      ContainerListGetResponse.Builder resp = ContainerListGetResponse.builder();
      if(response.code() == 200) {
        Status200.Builder responseBuilder = Status200.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ContainerInListReader().readArray(parser));
        }
        resp.status200(responseBuilder.build());
      }
      if(response.code() == 400) {
        Status400.Builder responseBuilder = Status400.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ErrorReader().read(parser));
        }
        resp.status400(responseBuilder.build());
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

  public ContainerListGetResponse get(Consumer<ContainerListGetRequest.Builder> request) throws IOException {
    ContainerListGetRequest.Builder builder = ContainerListGetRequest.builder();
    if(request != null) {
      request.accept(builder);
    }
    return this.get(builder.build());
  }
}
