package io.flexio.commons.otsdb.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import io.flexio.commons.otsdb.api.StoreDataPointsPostRequest;
import io.flexio.commons.otsdb.api.StoreDataPointsPostResponse;
import io.flexio.commons.otsdb.api.client.OpenTSDBAPIClient;
import io.flexio.commons.otsdb.api.storedatapointspostresponse.Status204;
import io.flexio.commons.otsdb.api.storedatapointspostresponse.Status400;
import io.flexio.commons.otsdb.api.types.DataPoint;
import io.flexio.commons.otsdb.api.types.json.DataPointWriter;
import io.flexio.commons.otsdb.api.types.json.StorageResponseReader;
import java.io.ByteArrayOutputStream;
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

public class StoreDataPointsClient implements OpenTSDBAPIClient.V1Api.StoreDataPoints {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  public StoreDataPointsClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
  }

  public StoreDataPointsClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public StoreDataPointsPostResponse post(StoreDataPointsPostRequest request) throws IOException {
    Requester requester = this.requesterFactory.create();
    if(request.summary() != null) {
      String summaryRaw = request.summary();
      String summary = summaryRaw;
      requester.parameter("summary", summary);
    }
    if(request.details() != null) {
      String detailsRaw = request.details();
      String details = detailsRaw;
      requester.parameter("details", details);
    }
    if(request.sync() != null) {
      Boolean syncRaw = request.sync();
      String sync = syncRaw != null ? syncRaw.toString() : null;
      requester.parameter("sync", sync);
    }
    if(request.sync_timeout() != null) {
      Long sync_timeoutRaw = request.sync_timeout();
      String sync_timeout = sync_timeoutRaw != null ? sync_timeoutRaw.toString() : null;
      requester.parameter("sync_timeout", sync_timeout);
    }
    if(request.authorization() != null) {
      String authorizationRaw = request.authorization();
      String authorization = authorizationRaw;
      requester.header("Authorization", authorization);
    }
    String path = "/api/v1/put";
    requester.path(path);
    ResponseDelegate response = null;
    try {
      byte[] requestBody = new byte[0];
      if(request.payload() != null) {
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
          try(JsonGenerator generator = this.jsonFactory.createGenerator(out)) {
            new DataPointWriter().writeArray(generator, request.payload().toArray(new DataPoint[request.payload().size()]));
          }
          requestBody = out.toByteArray();
        }
      }
      String contentType = "application/json";
      response = requester.post(contentType, requestBody);
      StoreDataPointsPostResponse.Builder resp = StoreDataPointsPostResponse.builder();
      if(response.code() == 204) {
        Status204.Builder responseBuilder = Status204.builder();
        resp.status204(responseBuilder.build());
      }
      if(response.code() == 400) {
        Status400.Builder responseBuilder = Status400.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new StorageResponseReader().read(parser));
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

  public StoreDataPointsPostResponse post(Consumer<StoreDataPointsPostRequest.Builder> request) throws IOException {
    StoreDataPointsPostRequest.Builder builder = StoreDataPointsPostRequest.builder();
    if(request != null) {
      request.accept(builder);
    }
    return this.post(builder.build());
  }
}
