package io.flexio.docker.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import java.lang.String;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.UrlProvider;

public class ContainersClient implements DockerEngineAPIClient.Containers {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  private final ContainerListClient containerListDelegate;

  private final CreateContainerClient createContainerDelegate;

  private final ContainerClient containerDelegate;

  public ContainersClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
    this.containerListDelegate = new ContainerListClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
    this.createContainerDelegate = new CreateContainerClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
    this.containerDelegate = new ContainerClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
  }

  public ContainersClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public DockerEngineAPIClient.Containers.ContainerList containerList() {
    return this.containerListDelegate;
  }

  public DockerEngineAPIClient.Containers.CreateContainer createContainer() {
    return this.createContainerDelegate;
  }

  public DockerEngineAPIClient.Containers.Container container() {
    return this.containerDelegate;
  }
}
