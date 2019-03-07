package io.flexio.commons.otsdb.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.commons.otsdb.api.client.OpenTSDBAPIClient;
import java.lang.String;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.UrlProvider;

public class V1ApiClient implements OpenTSDBAPIClient.V1Api {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  private final StoreDataPointsClient storeDataPointsDelegate;

  public V1ApiClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
    this.storeDataPointsDelegate = new StoreDataPointsClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
  }

  public V1ApiClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public OpenTSDBAPIClient.V1Api.StoreDataPoints storeDataPoints() {
    return this.storeDataPointsDelegate;
  }
}
