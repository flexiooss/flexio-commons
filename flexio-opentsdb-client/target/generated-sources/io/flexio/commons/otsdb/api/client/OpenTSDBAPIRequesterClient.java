package io.flexio.commons.otsdb.api.client;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.commons.otsdb.api.client.resources.V1ApiClient;
import java.lang.String;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.UrlProvider;

public class OpenTSDBAPIRequesterClient implements OpenTSDBAPIClient {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  private final V1ApiClient v1ApiDelegate;

  public OpenTSDBAPIRequesterClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
    this.v1ApiDelegate = new V1ApiClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
  }

  public OpenTSDBAPIRequesterClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public OpenTSDBAPIClient.V1Api v1Api() {
    return this.v1ApiDelegate;
  }
}
