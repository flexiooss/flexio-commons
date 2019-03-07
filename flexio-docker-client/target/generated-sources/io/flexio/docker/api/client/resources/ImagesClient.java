package io.flexio.docker.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import java.lang.String;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.UrlProvider;

public class ImagesClient implements DockerEngineAPIClient.Images {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  private final ImageListClient imageListDelegate;

  private final CreateImageClient createImageDelegate;

  private final InspectImageClient inspectImageDelegate;

  public ImagesClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
    this.imageListDelegate = new ImageListClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
    this.createImageDelegate = new CreateImageClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
    this.inspectImageDelegate = new InspectImageClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
  }

  public ImagesClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public DockerEngineAPIClient.Images.ImageList imageList() {
    return this.imageListDelegate;
  }

  public DockerEngineAPIClient.Images.CreateImage createImage() {
    return this.createImageDelegate;
  }

  public DockerEngineAPIClient.Images.InspectImage inspectImage() {
    return this.inspectImageDelegate;
  }
}
