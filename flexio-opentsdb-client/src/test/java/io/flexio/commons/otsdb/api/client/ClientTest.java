package io.flexio.commons.otsdb.api.client;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import io.flexio.commons.otsdb.api.StoreDataPointsPostResponse;
import io.flexio.commons.otsdb.api.ValueList;
import io.flexio.commons.otsdb.client.OpenTSDBAPIRequesterClient;
import io.flexio.commons.otsdb.api.types.DataPoint;
import io.flexio.commons.otsdb.api.types.json.DataPointWriter;
import okhttp3.Credentials;
import org.codingmatters.rest.api.client.UrlProvider;
import org.codingmatters.rest.api.client.okhttp.OkHttpClientWrapper;
import org.codingmatters.rest.api.client.okhttp.OkHttpRequesterFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.security.cert.CertificateException;
import java.util.Random;

@Ignore
public class ClientTest {

    private JsonFactory jsonFactory;
    private OkHttpRequesterFactory reqFactory;
    private UrlProvider urlProvider = () -> "https://metrics:12@devmetrics.flexio.io";
    private OpenTSDBAPIRequesterClient client;


    @Before
    public void setUp() throws Exception {
        this.jsonFactory = new JsonFactory();
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        this.reqFactory = new OkHttpRequesterFactory(OkHttpClientWrapper.build(b -> b
                .hostnameVerifier((s, sslSession) -> true)
                .sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0])
        ), this.urlProvider);
        this.client = new OpenTSDBAPIRequesterClient(reqFactory, jsonFactory, this.urlProvider);
    }

    @Test
    public void realClientTest() throws Exception {
        Long now = System.currentTimeMillis();

        DataPoint.Builder dataPoint = DataPoint.builder()
                .metric("test.counter")
                ;
        Random rand = new Random();
        for (int i = 0; i < 2000; i++) {
            now = now - i * 60 * 1000;
            ValueList<DataPoint> points = new ValueList.Builder<DataPoint>()
                    .with(
                            dataPoint.timestamp(now).value(rand.nextDouble() * 100).tags(Tags.tags().tag("source", "unit-test-1").value().build()).build(),
                            dataPoint.timestamp(now).value(rand.nextDouble() * 100).tags(p -> p.property("source", v -> v.stringValue("unit-test-1"))).build(),
                            dataPoint.timestamp(now).value(rand.nextDouble() * 100).tags(p -> p.property("source", v -> v.stringValue("unit-test-2"))).build(),
                            dataPoint.timestamp(now).value(rand.nextDouble() * 100).tags(p -> p.property("source", v -> v.stringValue("unit-test-3"))).build()
                    )
                    .build();

            try(ByteArrayOutputStream out = new ByteArrayOutputStream() ; JsonGenerator generator = this.jsonFactory.createGenerator(out)) {
                new DataPointWriter().writeArray(generator, points.toArray(new DataPoint[0]));
                generator.close();
                System.out.println(out.toString());
            }

            StoreDataPointsPostResponse response = this.client.v1Api().storeDataPoints().post(req -> req
                    .authorization(
                            Credentials.basic("metrics", "12"))
                    .payload(points)
            );


            System.out.println(response);
        }

    }
}
