package org.codingmatters.poom.services.io.redis.repository;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import io.flexio.docker.DockerResource;
import org.codingmatters.generated.QAValue;
import org.codingmatters.generated.json.QAValueReader;
import org.codingmatters.generated.json.QAValueWriter;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.domain.repositories.RepositoryBaseAcceptanceTest;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.ClassRule;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

public class RedisPrefixedKeysRepositoryBaseAcceptanceTest extends RepositoryBaseAcceptanceTest {

    public static final String REDIS_VERSION_ENV = "ut.redis.version";
    @ClassRule
    static public DockerResource docker = DockerResource.client()
            .with("redis-ut", c -> c
                    .image("harbor.ci.flexio.io/ci/" + "redis:" + System.getProperty(REDIS_VERSION_ENV))
            ).started()
            .finallyDeleted();

    final String hashName = UUID.randomUUID().toString();

    private JsonFactory jsonFactory = new JsonFactory();

    private JedisPool pool;
    private Jedis jedis;

    @Before
    public void setUp() throws Exception {
        this.jedis = new Jedis(this.docker.containerInfo("redis-ut").get().networkSettings().iPAddress(), 6379);
        this.pool = new JedisPool(this.docker.containerInfo("redis-ut").get().networkSettings().iPAddress(), 6379);
        this.jedis.flushAll();
        super.setUp();
    }

    @Override
    protected Repository<QAValue, Void> createRepository() throws Exception {
        assertThat(this.pool, Matchers.is(Matchers.notNullValue()));

        return new RedisPrefixedKeysRepository<>(this.pool, hashName) {
            @Override
            protected String marshall(QAValue value) throws IOException {
                try (ByteArrayOutputStream out = new ByteArrayOutputStream(); JsonGenerator generator = jsonFactory.createGenerator(out)) {
                    new QAValueWriter().write(generator, value);
                    generator.flush();
                    generator.close();
                    return out.toString();
                }
            }

            @Override
            protected QAValue unmarshall(String value) throws IOException {
                try (JsonParser parser = jsonFactory.createParser(value)) {
                    return new QAValueReader().read(parser);
                }
            }
        };
    }
}