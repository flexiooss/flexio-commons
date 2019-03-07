package io.flexio.commons.graylog.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.commons.graylog.api.client.GraylogAPIClient;
import java.lang.String;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.UrlProvider;

public class UniversalClient implements GraylogAPIClient.Api.Search.Universal {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  private final RelativeClient relativeDelegate;

  private final AbsoluteClient absoluteDelegate;

  public UniversalClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
    this.relativeDelegate = new RelativeClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
    this.absoluteDelegate = new AbsoluteClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
  }

  public UniversalClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public GraylogAPIClient.Api.Search.Universal.Relative relative() {
    return this.relativeDelegate;
  }

  public GraylogAPIClient.Api.Search.Universal.Absolute absolute() {
    return this.absoluteDelegate;
  }
}
