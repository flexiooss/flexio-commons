package io.flexio.commons.graylog;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.commons.graylog.api.AbsoluteGetRequest;
import io.flexio.commons.graylog.api.AbsoluteGetResponse;
import io.flexio.commons.graylog.api.RelativeGetRequest;
import io.flexio.commons.graylog.api.RelativeGetResponse;
import io.flexio.commons.graylog.api.client.GraylogAPIRequesterClient;
import io.flexio.commons.graylog.api.types.SearchResponse;
import okhttp3.Credentials;
import org.codingmatters.rest.api.client.UrlProvider;
import org.codingmatters.rest.api.client.okhttp.OkHttpClientWrapper;
import org.codingmatters.rest.api.client.okhttp.OkHttpRequesterFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class GraylogClientTest {

    private JsonFactory jsonFactory;

    private UrlProvider urlProvider = () -> "http://localhost:9000";
    private OkHttpRequesterFactory requesterFactory;
    private GraylogAPIRequesterClient graylogAPIRequesterClient;

    private static final String CREDENTIALS = Credentials.basic("admin", "admin");

    @Before
    public void setUp() {

        this.jsonFactory = new JsonFactory();

        this.requesterFactory = new OkHttpRequesterFactory(
                OkHttpClientWrapper.build(),
                this.urlProvider
        );

        this.graylogAPIRequesterClient = new GraylogAPIRequesterClient(
                this.requesterFactory,
                this.jsonFactory,
                this.urlProvider
        );
    }

    @Test
    public void realClientTestRelative() throws IOException {
        RelativeGetRequest getRequest = RelativeGetRequest.builder()
                .authorization(CREDENTIALS)
                .query("category:test")
                .range("500000")
                .decorate(true)
                .accept("application/json")
                .build();

        RelativeGetResponse getResponse = this.graylogAPIRequesterClient.api().search().universal().relative().get(getRequest);

        getResponse.opt().status200().orElseThrow(()->new AssertionError("Status code should be 200"));
        SearchResponse searchResponse = getResponse.status200().payload();
        Assert.assertNotNull(searchResponse.messages());
    }

    @Test
    public void realClientTestAbsolute() throws IOException {
        AbsoluteGetRequest getRequest = AbsoluteGetRequest.builder()
                .authorization(CREDENTIALS)
                .query("category:test")
                .from("2014-12-01 00:00:00")
                .to("2019-12-01 00:00:00")
                .limit(100L)
                .offset(42L)
                .decorate(true)
                .accept("application/json")
                .build();

        AbsoluteGetResponse getResponse = this.graylogAPIRequesterClient.api().search().universal().absolute().get(getRequest);

        getResponse.opt().status200().orElseThrow(()->new AssertionError("Status code should be 200"));
        SearchResponse searchResponse = getResponse.status200().payload();
        Assert.assertNotNull(searchResponse.messages());
    }
}
