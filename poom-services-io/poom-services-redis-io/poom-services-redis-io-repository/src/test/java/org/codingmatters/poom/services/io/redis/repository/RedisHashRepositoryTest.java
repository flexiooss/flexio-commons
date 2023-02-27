package org.codingmatters.poom.services.io.redis.repository;

import com.fasterxml.jackson.core.JsonFactory;
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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThrows;

public class RedisHashRepositoryTest {

    public static final String REDIS_VERSION_ENV = "ut.redis.version";
    @ClassRule
    static public DockerResource docker = DockerResource.client()
            .with("redis-ut", c -> c
                    .image("harbor.ci.flexio.io/ci/" + "redis:" + System.getProperty(REDIS_VERSION_ENV))
            ).started()
            .finallyDeleted()
            ;

    private final String hashName = UUID.randomUUID().toString();

    private JedisPool jedisPool;
    private Jedis jedis;
    private Repository<String, Void> repository;


    @Before
    public void setUp() throws Exception {
        this.jedis = new Jedis(this.docker.containerInfo("redis-ut").get().networkSettings().iPAddress(), 6379);
        this.jedis.flushAll();

        this.jedisPool = new JedisPool(this.docker.containerInfo("redis-ut").get().networkSettings().iPAddress(), 6379);

        this.repository = new RedisHashRepository<>(this.jedisPool, hashName) {
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
    public void nominalCreate() throws Exception {
        Entity<String> entity = this.repository.create("test-value");

        assertThat(
                this.jedis.hget(hashName, entity.id()),
                is("test-value||1")
        );
    }

    @Test
    public void nominalCreateWithId() throws Exception {
        Entity<String> entity = this.repository.createWithId("33", "test-value");

        assertThat(
                this.jedis.hget(hashName, "33"),
                is("test-value||1")
        );
    }

    @Test
    public void nominalCreateWithIdAndVersion() throws Exception {
        Entity<String> entity = this.repository.createWithIdAndVersion("33", BigInteger.valueOf(75), "test-value");

        assertThat(
                this.jedis.hget(hashName, "33"),
                is("test-value||75")
        );
    }

    @Test
    public void nominalRetrieve() throws Exception {
        this.jedis.hset(hashName, "12", "test-value||42");

        assertThat(this.repository.retrieve("12"), is(new ImmutableEntity<>("12", BigInteger.valueOf(42), "test-value")));
    }

    @Test
    public void nominalUpdate() throws Exception {
        this.jedis.hset(hashName, "12", "test-value||42");

        Entity<String> updated = this.repository.update(new ImmutableEntity<>("12", null, null), "changed");

        assertThat(updated.id(), is("12"));
        assertThat(updated.version(), is(BigInteger.valueOf(43)));
        assertThat(
                this.jedis.hget(hashName, updated.id()),
                is("changed||43")
        );
    }

    @Test
    public void nominalDelete() throws Exception {
        this.jedis.hset(hashName, "12", "test-value||42");

        this.repository.delete(new ImmutableEntity<>("12", null, null));

        assertThat(this.jedis.hget(hashName, "12"), is(nullValue()));
    }

    @Test
    public void searchThrowsUnsupportedOperationException() throws Exception {
        assertThrows(UnsupportedOperationException.class, () -> this.repository.search(null, 0L, 10L));
    }

    @Test
    public void deleteFromThrowsUnsupportedOperationException() throws Exception {
        assertThrows(UnsupportedOperationException.class, () -> this.repository.deleteFrom(null));
    }

    @Test
    public void nominalAll() throws Exception {
        for (int i = 0; i < 100;  i++) {
            this.jedis.hset(
                    hashName,
                    String.format("%03d", i),
                    String.format("test-value-%s||1", i)
            );
        }

        PagedEntityList<String> page = this.repository.all(0, 99);
        assertThat(page.size(), is(100));
        assertThat(page.total(), is(100L));

        int i = 0;
        for (Entity<String> entity : page.stream().sorted(Comparator.comparing(Entity::id)).collect(Collectors.toList())) {
            assertThat("entity " + i + " id", entity.id(), is(String.format("%03d", i)));
            assertThat("entity " + i + " version", entity.version(), is(BigInteger.ONE));
            assertThat("entity " + i + " value", entity.value(), is("test-value-" + i));
            i++;
        }
    }
}
