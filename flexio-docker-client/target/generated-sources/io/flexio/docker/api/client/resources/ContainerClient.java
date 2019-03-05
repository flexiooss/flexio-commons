package io.flexio.docker.api.client.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import io.flexio.docker.api.ContainerDeleteRequest;
import io.flexio.docker.api.ContainerDeleteResponse;
import io.flexio.docker.api.client.DockerEngineAPIClient;
import io.flexio.docker.api.containerdeleteresponse.Status204;
import io.flexio.docker.api.containerdeleteresponse.Status400;
import io.flexio.docker.api.types.json.ErrorReader;
import java.io.IOException;
import java.lang.Boolean;
import java.lang.Exception;
import java.lang.String;
import java.util.function.Consumer;
import org.codingmatters.rest.api.client.Requester;
import org.codingmatters.rest.api.client.RequesterFactory;
import org.codingmatters.rest.api.client.ResponseDelegate;
import org.codingmatters.rest.api.client.UrlProvider;

public class ContainerClient implements DockerEngineAPIClient.Containers.Container {
  private final RequesterFactory requesterFactory;

  private final JsonFactory jsonFactory;

  private final UrlProvider urlProvider;

  private final InspectClient inspectDelegate;

  private final StartClient startDelegate;

  private final StopClient stopDelegate;

  private final RestartClient restartDelegate;

  private final KillClient killDelegate;

  private final WaitForClient waitForDelegate;

  public ContainerClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, UrlProvider urlProvider) {
    this.requesterFactory = requesterFactory;
    this.jsonFactory = jsonFactory;
    this.urlProvider = urlProvider;
    this.inspectDelegate = new InspectClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
    this.startDelegate = new StartClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
    this.stopDelegate = new StopClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
    this.restartDelegate = new RestartClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
    this.killDelegate = new KillClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
    this.waitForDelegate = new WaitForClient(this.requesterFactory, this.jsonFactory, this.urlProvider);
  }

  public ContainerClient(RequesterFactory requesterFactory, JsonFactory jsonFactory, String baseUrl) {
    this(requesterFactory, jsonFactory, () -> baseUrl);
  }

  public ContainerDeleteResponse delete(ContainerDeleteRequest request) throws IOException {
    Requester requester = this.requesterFactory.create();
    if(request.v() != null) {
      Boolean vRaw = request.v();
      String v = vRaw != null ? vRaw.toString() : null;
      requester.parameter("v", v);
    }
    if(request.force() != null) {
      Boolean forceRaw = request.force();
      String force = forceRaw != null ? forceRaw.toString() : null;
      requester.parameter("force", force);
    }
    if(request.link() != null) {
      Boolean linkRaw = request.link();
      String link = linkRaw != null ? linkRaw.toString() : null;
      requester.parameter("link", link);
    }
    String path = "/containers/{container-id}";
    if(request.containerId() != null) {
      String containerIdRaw = request.containerId();
      String containerId = containerIdRaw;
      path = path.replaceFirst("\\{container-id\\}", containerId);
    }
    requester.path(path);
    ResponseDelegate response = null;
    try {
      response = requester.delete();
      ContainerDeleteResponse.Builder resp = ContainerDeleteResponse.builder();
      if(response.code() == 204) {
        Status204.Builder responseBuilder = Status204.builder();
        resp.status204(responseBuilder.build());
      }
      if(response.code() == 400) {
        Status400.Builder responseBuilder = Status400.builder();
        try(JsonParser parser = this.jsonFactory.createParser(response.body())) {
          responseBuilder.payload(new ErrorReader().read(parser));
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

  public ContainerDeleteResponse delete(Consumer<ContainerDeleteRequest.Builder> request) throws IOException {
    ContainerDeleteRequest.Builder builder = ContainerDeleteRequest.builder();
    if(request != null) {
      request.accept(builder);
    }
    return this.delete(builder.build());
  }

  public DockerEngineAPIClient.Containers.Container.Inspect inspect() {
    return this.inspectDelegate;
  }

  public DockerEngineAPIClient.Containers.Container.Start start() {
    return this.startDelegate;
  }

  public DockerEngineAPIClient.Containers.Container.Stop stop() {
    return this.stopDelegate;
  }

  public DockerEngineAPIClient.Containers.Container.Restart restart() {
    return this.restartDelegate;
  }

  public DockerEngineAPIClient.Containers.Container.Kill kill() {
    return this.killDelegate;
  }

  public DockerEngineAPIClient.Containers.Container.WaitFor waitFor() {
    return this.waitForDelegate;
  }
}
