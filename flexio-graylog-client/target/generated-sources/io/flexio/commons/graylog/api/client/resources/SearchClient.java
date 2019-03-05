package io.flexio.commons.graylog.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.commons.graylog.api.client.GraylogAPIClient;
import java.lang.String;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.UrlProvider;

public class SearchClient implements GraylogAPIClient.Api.Search {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  private final UniversalClient universalDelegate;

  public SearchClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
    this.universalDelegate = new UniversalClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
  }

  public SearchClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public GraylogAPIClient.Api.Search.Universal universal() {
    return this.universalDelegate;
  }
}
