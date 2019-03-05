package io.flexio.commons.otsdb.api.client;

import io.flexio.commons.otsdb.api.OpenTSDBAPIHandlers;
import io.flexio.commons.otsdb.api.StoreDataPointsPostRequest;
import io.flexio.commons.otsdb.api.StoreDataPointsPostResponse;
import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.String;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class OpenTSDBAPIHandlersClient implements OpenTSDBAPIClient {
  private final OpenTSDBAPIHandlers handlers;

  private final ExecutorService executor;

  public OpenTSDBAPIHandlersClient(OpenTSDBAPIHandlers handlers, ExecutorService executor) {
    this.handlers = handlers;
    this.executor = executor;
  }

  private <T> T call(Callable<T> callable, String action) throws IOException {
    try {
      return this.executor.submit(callable).get();
    } catch(InterruptedException | ExecutionException e) {
      throw new IOException("error invoking " + action, e);
    }
  }

  public OpenTSDBAPIClient.V1Api v1Api() {
    return new V1ApiImpl();
  }

  private class V1ApiImpl implements OpenTSDBAPIClient.V1Api {
    public OpenTSDBAPIClient.V1Api.StoreDataPoints storeDataPoints() {
      return new StoreDataPointsImpl();
    }

    private class StoreDataPointsImpl implements OpenTSDBAPIClient.V1Api.StoreDataPoints {
      public StoreDataPointsPostResponse post(StoreDataPointsPostRequest request) throws IOException {
        return call(() -> handlers.storeDataPointsPostHandler().apply(request), "Store Data Points post");
      }

      public StoreDataPointsPostResponse post(Consumer<StoreDataPointsPostRequest.Builder> request) throws IOException {
        return call(() -> {
            	StoreDataPointsPostRequest.Builder builder = StoreDataPointsPostRequest.builder();
            	request.accept(builder);
            	return handlers.storeDataPointsPostHandler().apply(builder.build());
            }, "Store Data Points post");
      }
    }
  }
}
