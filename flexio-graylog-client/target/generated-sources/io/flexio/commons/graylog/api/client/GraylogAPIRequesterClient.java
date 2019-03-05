package io.flexio.commons.graylog.api.client;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.commons.graylog.api.client.resources.ApiClient;
import java.lang.String;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.UrlProvider;

public class GraylogAPIRequesterClient implements GraylogAPIClient {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  private final ApiClient apiDelegate;

  public GraylogAPIRequesterClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
    this.apiDelegate = new ApiClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
  }

  public GraylogAPIRequesterClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public GraylogAPIClient.Api api() {
    return this.apiDelegate;
  }
}
