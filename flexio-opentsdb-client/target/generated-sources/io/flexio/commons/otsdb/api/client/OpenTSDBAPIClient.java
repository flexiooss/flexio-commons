package io.flexio.commons.otsdb.api.client;

import io.flexio.commons.otsdb.api.StoreDataPointsPostRequest;
import io.flexio.commons.otsdb.api.StoreDataPointsPostResponse;
import java.io.IOException;
import java.lang.String;
import java.util.function.Consumer;

public interface OpenTSDBAPIClient {
  String REQUESTER_CLASSNAME = "io.flexio.commons.otsdb.api.client.OpenTSDBAPIRequesterClient";

  String HANDLERS_CLASSNAME = "io.flexio.commons.otsdb.api.client.OpenTSDBAPIHandlersClient";

  String API_NAME = "open-tsdb-api";

  V1Api v1Api();

  interface V1Api {
    StoreDataPoints storeDataPoints();

    interface StoreDataPoints {
      StoreDataPointsPostResponse post(StoreDataPointsPostRequest request) throws IOException;

      StoreDataPointsPostResponse post(Consumer<StoreDataPointsPostRequest.Builder> request) throws IOException;
    }
  }
}
