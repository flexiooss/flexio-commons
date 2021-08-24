package org.codingmatters.poom.services.io.redis.cache;

import io.flexio.docker.DockerResource;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SimpleRedisCacheStoreTest {
    public static final String REDIS_VERSION_ENV = "ut.redis.version";
    @ClassRule
    static public DockerResource docker = DockerResource.client()
            .with("redis-ut", c -> c
                    .image("harbor.ci.flexio.io/ci/" + "redis:" + System.getProperty(REDIS_VERSION_ENV))
            ).started()
            .finallyDeleted()
            ;

    private Jedis jedis;
    private SimpleRedisCacheStore<String, String> store;

    @Before
    public void setUp() throws Exception {
        this.jedis = new Jedis(this.docker.containerInfo("redis-ut").get().networkSettings().iPAddress(), 6379);
        this.jedis.flushAll();

        this.store = new SimpleRedisCacheStore<>(
                new JedisPool(this.docker.containerInfo("redis-ut").get().networkSettings().iPAddress(), 6379),
                "prefix",
                s -> s, s -> s, s -> s, s -> s
        );
    }

    @Test
    public void givenStoreIsEmpty__whenStoringKV__thenValueStoredAtPrefixedKey() throws Exception {
        this.store.store("toto", "titi");

        assertThat(this.jedis.get("prefix:toto"), is("titi"));
    }

    @Test
    public void givenStoreNotEmpty__whenStoringKV_andSameKeyWithPrefixExists__thenValueChanged() throws Exception {
        this.jedis.set("prefix:toto", "tutu");

        this.store.store("toto", "titi");

        assertThat(this.jedis.get("prefix:toto"), is("titi"));
    }

    @Test
    public void givenStoreNotEmpty__whenStoringKV_andUnprefixedSameKeyExists__thenValueInsertedForPrefixedKey_andUnprefixedKVIsUnchanged() throws Exception {
        this.jedis.set("toto", "tutu");

        this.store.store("toto", "titi");

        assertThat(this.jedis.get("toto"), is("tutu"));
        assertThat(this.jedis.get("prefix:toto"), is("titi"));
    }

    @Test
    public void givenStoreNotEmpty__whenStoringKV_andPrefixedDifferentKeyExists__thenValueInsertedForPrefixedKey_andPrefixedKVIsUnchanged() throws Exception {
        this.jedis.set("prefix:tata", "tutu");

        this.store.store("toto", "titi");

        assertThat(this.jedis.get("prefix:tata"), is("tutu"));
        assertThat(this.jedis.get("prefix:toto"), is("titi"));
    }

    @Test
    public void givenStoreEmpty__whenGettingKey__thenEmptyIsReturned() throws Exception {
        assertThat(this.store.get("none"), is(Optional.empty()));
    }

    @Test
    public void givenStoreNotEmpty__whenGettingKey_andPrefixedKeyExists__thenValueReturned() throws Exception {
        this.jedis.set("prefix:toto", "titi");

        assertThat(this.store.get("toto"), is(Optional.of("titi")));
    }

    @Test
    public void givenStoreNotEmpty__whenGettingKey_andUnprefixedKeyExists__thenEmptyReturned() throws Exception {
        this.jedis.set("toto", "titi");

        assertThat(this.store.get("toto"), is(Optional.empty()));
    }

    @Test
    public void givenStoreNotEmpty__whenGettingKey_andAnotherPrefixedKeyExists__thenEmptyReturned() throws Exception {
        this.jedis.set("prefix:tata", "titi");

        assertThat(this.store.get("toto"), is(Optional.empty()));
    }

    @Test
    public void givenStoreEmpty__whenTestingKeyExistence__thenFalse() throws Exception {
        assertThat(this.store.has("toto"), is(false));
    }

    @Test
    public void givenStoreNotEmpty__whenTestingKeyExistence_andPrefixedKeyExists__thenTrue() throws Exception {
        this.jedis.set("prefix:toto", "titi");
        assertThat(this.store.has("toto"), is(true));
    }

    @Test
    public void givenStoreNotEmpty__whenTestingKeyExistence_andUnprefixedKeyExists__thenFalse() throws Exception {
        this.jedis.set("toto", "titi");
        assertThat(this.store.has("toto"), is(false));
    }

    @Test
    public void givenStoreNotEmpty__whenTestingKeyExistence_andAnotherPrefixedKeyExists__thenFalse() throws Exception {
        this.jedis.set("prefix:tata", "titi");
        assertThat(this.store.has("toto"), is(false));
    }

    @Test
    public void givenStoreEmpty__whenRemovingKey__thenNothingAppend() throws Exception {
        this.store.remove("toto");
    }

    @Test
    public void givenStoreNotEmpty__whenRemovingKey_andPrefixedKeyExists__thenKeyRemoved() throws Exception {
        this.jedis.set("prefix:toto", "titi");

        this.store.remove("toto");

        assertThat(this.jedis.get("prefix:toto"), is(nullValue()));
    }

    @Test
    public void givenStoreNotEmpty__whenRemovingKey_andUnprefixedKeyExists__thenUnpreficedKeyNotRemoved() throws Exception {
        this.jedis.set("toto", "titi");

        this.store.remove("toto");

        assertThat(this.jedis.get("toto"), is("titi"));
    }

    @Test
    public void givenStoreNotEmpty__whenRemovingKey_andAnotherPrefioxedKeyExists__thenOtherKeyNotRemoved() throws Exception {
        this.jedis.set("prefix:tata", "titi");

        this.store.remove("toto");

        assertThat(this.jedis.get("prefix:tata"), is("titi"));
    }

    @Test
    public void givenStoreEmpty__whenClearing__thenNothoingAppens() throws Exception {
        this.store.clear();
    }

    @Test
    public void givenStoreNotEmpty__whenClearing_andSomePrefixedKeysExists__thenPrefixedKeysRemoved() throws Exception {
        this.jedis.set("prefix:toto", "titi");
        this.jedis.set("prefix:tutu", "tata");

        this.store.clear();

        assertThat(this.jedis.get("prefix:toto"), is(nullValue()));
        assertThat(this.jedis.get("prefix:tutu"), is(nullValue()));
    }

    @Test
    public void givenStoreNotEmpty__whenClearing_andSomeUnprefixedKeysExists__thenUnprefixedKeysNotRemoved() throws Exception {
        this.jedis.set("toto", "titi");
        this.jedis.set("tutu", "tata");

        this.store.clear();

        assertThat(this.jedis.get("toto"), is("titi"));
        assertThat(this.jedis.get("tutu"), is("tata"));
    }

    @Test
    public void givenStoreNotEmpty__whenClearing_andSomeOtherPrefixedKeysExists__thenOtherPrefixedKeysNotRemoved() throws Exception {
        this.jedis.set("otherprefix:toto", "titi");
        this.jedis.set("otherprefix:tutu", "tata");

        this.store.clear();

        assertThat(this.jedis.get("otherprefix:toto"), is("titi"));
        assertThat(this.jedis.get("otherprefix:tutu"), is("tata"));
    }

    @Test
    public void givenStoreEmpty__whenListingKeys__thenEmptySet() throws Exception {
        assertThat(this.store.keys(), is(empty()));
    }

    @Test
    public void givenStoreNotEmpty__whenListingKeys_andPrefixedKeysExist__thenKeysReturned() throws Exception {
        this.jedis.set("prefix:toto", "titi");
        this.jedis.set("prefix:tutu", "tata");

        assertThat(this.store.keys(), containsInAnyOrder("toto", "tutu"));
    }

    @Test
    public void givenStoreNotEmpty__whenListingKeys_andUnprefixedKeysExist__thenEmptySet() throws Exception {
        this.jedis.set("toto", "titi");
        this.jedis.set("tutu", "tata");

        assertThat(this.store.keys(), is(empty()));
    }

    @Test
    public void givenStoreNotEmpty__whenListingKeys_andOtherPrefixedKeysExist__thenEmptySet() throws Exception {
        this.jedis.set("anotherprefix:toto", "titi");
        this.jedis.set("anotherprefix:tutu", "tata");

        assertThat(this.store.keys(), is(empty()));
    }
}