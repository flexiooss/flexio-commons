package org.codingmatters.poom.services.io.redis.repository;

import io.flexio.docker.DockerResource;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.domain.entities.Entity;
import org.codingmatters.poom.services.domain.entities.ImmutableEntity;
import org.codingmatters.poom.services.domain.entities.PagedEntityList;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;

public class RedisPrefixedKeysRepositoryWithTTLTest {

    public static final String REDIS_VERSION_ENV = "ut.redis.version";
    @ClassRule
    static public DockerResource docker = DockerResource.client()
            .with("redis-ut", c -> c
                    .image("harbor.ci.flexio.io/ci/" + "redis:" + System.getProperty(REDIS_VERSION_ENV))
            ).started()
            .finallyDeleted()
            ;

    private final String keyPrefix = UUID.randomUUID().toString();

    private JedisPool jedisPool;
    private Jedis jedis;
    private Repository<String, Void> repository;

    @Before
    public void setUp() throws Exception {
        this.jedis = new Jedis(this.docker.containerInfo("redis-ut").get().networkSettings().iPAddress(), 6379);
        this.jedis.flushAll();

        this.jedisPool = new JedisPool(this.docker.containerInfo("redis-ut").get().networkSettings().iPAddress(), 6379);

        this.repository = new RedisPrefixedKeysRepository<>(this.jedisPool, keyPrefix, 2L) {
            @Override
            protected String marshall(String value) throws IOException {
                return value;
            }

            @Override
            protected String unmarshall(String value) throws IOException {
                return value;
            }
        };
    }

    @Test
    public void nominalCreateWithIdAndVersion() throws Exception {
        Entity<String> entity = this.repository.createWithIdAndVersion("33", BigInteger.valueOf(75), "test-value");

        assertThat(
                this.jedis.ttl(keyPrefix + "::" + "33"),
                is(greaterThan(1L))
        );
    }

    @Test
    public void nominalRetrieve() throws Exception {
        this.jedis.set(keyPrefix + "::" + "12", "test-value||42");
        this.jedis.expire(keyPrefix + "::" + "12", 12);

        assertThat(this.repository.retrieve("12"), is(new ImmutableEntity<>("12", BigInteger.valueOf(42), "test-value")));

        assertThat(
                this.jedis.ttl(keyPrefix + "::" + "12"),
                is(greaterThan(1L))
        );
    }

    @Test
    public void nominalUpdate() throws Exception {
        this.jedis.set(keyPrefix + "::" + "12", "test-value||42");
        this.jedis.expire(keyPrefix + "::" + "12", 12L);

        this.repository.update(new ImmutableEntity<>("12", null, null), "changed");

        assertThat(
                this.jedis.ttl(keyPrefix + "::" + "12"),
                is(greaterThan(1L))
        );
    }



    @Test
    public void ttlExpires() throws Exception {
        Entity<String> entity = this.repository.createWithIdAndVersion("33", BigInteger.valueOf(75), "test-value");

        assertThat(this.repository.retrieve("33"), is(notNullValue()));
        Thread.sleep(2000L);

        assertThat(this.repository.retrieve("33"), is(nullValue()));
    }
}
