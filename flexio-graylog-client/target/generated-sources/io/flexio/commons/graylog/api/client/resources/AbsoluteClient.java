package io.flexio.commons.graylog.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import io.flexio.commons.graylog.api.AbsoluteGetRequest;
import io.flexio.commons.graylog.api.AbsoluteGetResponse;
import io.flexio.commons.graylog.api.absolutegetresponse.Status200;
import io.flexio.commons.graylog.api.absolutegetresponse.Status400;
import io.flexio.commons.graylog.api.client.GraylogAPIClient;
import io.flexio.commons.graylog.api.types.json.SearchResponseReader;
import java.io.IOException;
import java.lang.Boolean;
import java.lang.Exception;
import java.lang.Long;
import java.lang.String;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.codingmatters.rest.api.client.Requester;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.ResponseDelegate;
import org.codingmatters.rest.api.client.UrlProvider;
import org.codingmatters.value.objects.values.json.ObjectValueReader;

public class AbsoluteClient implements GraylogAPIClient.Api.Search.Universal.Absolute {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  public AbsoluteClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
  }

  public AbsoluteClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public AbsoluteGetResponse get(AbsoluteGetRequest request) throws IOException {
    Requester requester = this.requesterFactory.create();
    if(request.query() != null) {
      String queryRaw = request.query();
      String query = queryRaw;
      requester.parameter("query", query);
    }
    if(request.from() != null) {
      String fromRaw = request.from();
      String from = fromRaw;
      requester.parameter("from", from);
    }
    if(request.to() != null) {
      String toRaw = request.to();
      String to = toRaw;
      requester.parameter("to", to);
    }
    if(request.limit() != null) {
      Long limitRaw = request.limit();
      String limit = limitRaw != null ? limitRaw.toString() : null;
      requester.parameter("limit", limit);
    }
    if(request.offset() != null) {
      Long offsetRaw = request.offset();
      String offset = offsetRaw != null ? offsetRaw.toString() : null;
      requester.parameter("offset", offset);
    }
    if(request.filter() != null) {
      String filterRaw = request.filter();
      String filter = filterRaw;
      requester.parameter("filter", filter);
    }
    if(request.fields() != null) {
      List<String> fields = new LinkedList<>();
      for(String fieldsRawElement : request.fields()) {
        String fieldsElement = fieldsRawElement;
        fields.add(fieldsElement);
      }
      requester.parameter("fields", fields);
    }
    if(request.sort() != null) {
      String sortRaw = request.sort();
      String sort = sortRaw;
      requester.parameter("sort", sort);
    }
    if(request.decorate() != null) {
      Boolean decorateRaw = request.decorate();
      String decorate = decorateRaw != null ? decorateRaw.toString() : null;
      requester.parameter("decorate", decorate);
    }
    if(request.accept() != null) {
      String acceptRaw = request.accept();
      String accept = acceptRaw;
      requester.header("Accept", accept);
    }
    if(request.authorization() != null) {
      String authorizationRaw = request.authorization();
      String authorization = authorizationRaw;
      requester.header("Authorization", authorization);
    }
    String path = "/api/search/universal/absolute";
    requester.path(path);
    ResponseDelegate response = null;
    try {
      response = requester.get();
      AbsoluteGetResponse.Builder resp = AbsoluteGetResponse.builder();
      if(response.code() == 200) {
        Status200.Builder responseBuilder = Status200.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new SearchResponseReader().read(parser));
        }
        resp.status200(responseBuilder.build());
      }
      if(response.code() == 400) {
        Status400.Builder responseBuilder = Status400.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ObjectValueReader().read(parser));
        }
        resp.status400(responseBuilder.build());
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

  public AbsoluteGetResponse get(Consumer<AbsoluteGetRequest.Builder> request) throws IOException {
    AbsoluteGetRequest.Builder builder = AbsoluteGetRequest.builder();
    if(request != null) {
      request.accept(builder);
    }
    return this.get(builder.build());
  }
}