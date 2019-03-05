package io.flexio.commons.graylog.api.client;

import io.flexio.commons.graylog.api.AbsoluteGetRequest;
import io.flexio.commons.graylog.api.AbsoluteGetResponse;
import io.flexio.commons.graylog.api.GraylogAPIHandlers;
import io.flexio.commons.graylog.api.RelativeGetRequest;
import io.flexio.commons.graylog.api.RelativeGetResponse;
import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.String;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class GraylogAPIHandlersClient implements GraylogAPIClient {
  private final GraylogAPIHandlers handlers;

  private final ExecutorService executor;

  public GraylogAPIHandlersClient(GraylogAPIHandlers handlers, ExecutorService executor) {
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

  public GraylogAPIClient.Api api() {
    return new ApiImpl();
  }

  private class ApiImpl implements GraylogAPIClient.Api {
    public GraylogAPIClient.Api.Search search() {
      return new SearchImpl();
    }

    private class SearchImpl implements GraylogAPIClient.Api.Search {
      public GraylogAPIClient.Api.Search.Universal universal() {
        return new UniversalImpl();
      }

      private class UniversalImpl implements GraylogAPIClient.Api.Search.Universal {
        public GraylogAPIClient.Api.Search.Universal.Relative relative() {
          return new RelativeImpl();
        }

        public GraylogAPIClient.Api.Search.Universal.Absolute absolute() {
          return new AbsoluteImpl();
        }

        private class RelativeImpl implements GraylogAPIClient.Api.Search.Universal.Relative {
          public RelativeGetResponse get(RelativeGetRequest request) throws IOException {
            return call(() -> handlers.relativeGetHandler().apply(request), "Relative get");
          }

          public RelativeGetResponse get(Consumer<RelativeGetRequest.Builder> request) throws IOException {
            return call(() -> {
                	RelativeGetRequest.Builder builder = RelativeGetRequest.builder();
                	request.accept(builder);
                	return handlers.relativeGetHandler().apply(builder.build());
                }, "Relative get");
          }
        }

        private class AbsoluteImpl implements GraylogAPIClient.Api.Search.Universal.Absolute {
          public AbsoluteGetResponse get(AbsoluteGetRequest request) throws IOException {
            return call(() -> handlers.absoluteGetHandler().apply(request), "Absolute get");
          }

          public AbsoluteGetResponse get(Consumer<AbsoluteGetRequest.Builder> request) throws IOException {
            return call(() -> {
                	AbsoluteGetRequest.Builder builder = AbsoluteGetRequest.builder();
                	request.accept(builder);
                	return handlers.absoluteGetHandler().apply(builder.build());
                }, "Absolute get");
          }
        }
      }
    }
  }
}
