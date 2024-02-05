package org.codingmatters.poom.services.io.redis.repository;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RedisAbstractRepositoryTest {

    @Test
    public void givenContentWithPipe() throws Exception {
        FakeRedisRepository repo = new FakeRedisRepository();
        RedisAbstractRepository<String, String>.StorableValue value = repo.fromStored("data||data||12");
        assertThat(value.value, is("data||data"));
        assertThat(value.version.toString(), is("12"));
    }
}
