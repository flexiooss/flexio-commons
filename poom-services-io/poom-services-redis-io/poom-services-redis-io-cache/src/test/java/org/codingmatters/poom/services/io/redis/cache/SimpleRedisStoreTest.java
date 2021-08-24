package org.codingmatters.poom.services.io.redis.cache;

import io.flexio.docker.DockerResource;
import io.flexio.docker.api.types.ContainerCreationData;
import org.codingmatters.poom.services.support.Env;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SimpleRedisStoreTest {
    public static final String REDIS_VERSION_ENV = "ut.redis.version";
    @Rule
    public DockerResource docker = DockerResource.client()
            .with("redis-ut", c -> c
                    .image("harbor.ci.flexio.io/ci/redis:" + System.getProperty(REDIS_VERSION_ENV))
                    .cmd("redis-server", "-p", "7777")
            ).started().finallyStopped()
            ;

    @Test
    public void given__when__then() throws Exception {
        Jedis jedis = new Jedis(this.docker.containerInfo("redis-ut").get().networkSettings().iPAddress(), 7777);
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
    }
}