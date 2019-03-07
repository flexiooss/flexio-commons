package io.flexio.commons.graylog.api.client;

import io.flexio.commons.graylog.api.AbsoluteGetRequest;
import io.flexio.commons.graylog.api.AbsoluteGetResponse;
import io.flexio.commons.graylog.api.RelativeGetRequest;
import io.flexio.commons.graylog.api.RelativeGetResponse;
import java.io.IOException;
import java.lang.String;
import java.util.function.Consumer;

public interface GraylogAPIClient {
  String REQUESTER_CLASSNAME = "io.flexio.commons.graylog.api.client.GraylogAPIRequesterClient";

  String HANDLERS_CLASSNAME = "io.flexio.commons.graylog.api.client.GraylogAPIHandlersClient";

  String API_NAME = "graylog-api";

  Api api();

  interface Api {
    Search search();

    interface Search {
      Universal universal();

      interface Universal {
        Relative relative();

        Absolute absolute();

        interface Relative {
          RelativeGetResponse get(RelativeGetRequest request) throws IOException;

          RelativeGetResponse get(Consumer<RelativeGetRequest.Builder> request) throws IOException;
        }

        interface Absolute {
          AbsoluteGetResponse get(AbsoluteGetRequest request) throws IOException;

          AbsoluteGetResponse get(Consumer<AbsoluteGetRequest.Builder> request) throws IOException;
        }
      }
    }
  }
}
