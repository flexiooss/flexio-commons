package io.flexio.docker.api.client;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.docker.api.client.resources.ContainersClient;
import io.flexio.docker.api.client.resources.ImagesClient;
import io.flexio.docker.api.client.resources.VersionClient;
import java.lang.String;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.UrlProvider;

public class DockerEngineAPIRequesterClient implements DockerEngineAPIClient {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  private final VersionClient versionDelegate;

  private final ContainersClient containersDelegate;

  private final ImagesClient imagesDelegate;

  public DockerEngineAPIRequesterClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
    this.versionDelegate = new VersionClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
    this.containersDelegate = new ContainersClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
    this.imagesDelegate = new ImagesClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
  }

  public DockerEngineAPIRequesterClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public DockerEngineAPIClient.Version version() {
    return this.versionDelegate;
  }

  public DockerEngineAPIClient.Containers containers() {
    return this.containersDelegate;
  }

  public DockerEngineAPIClient.Images images() {
    return this.imagesDelegate;
  }
}
