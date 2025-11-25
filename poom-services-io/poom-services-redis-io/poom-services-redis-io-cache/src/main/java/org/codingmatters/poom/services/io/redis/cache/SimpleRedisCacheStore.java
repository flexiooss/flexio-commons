package org.codingmatters.poom.services.io.redis.cache;

import org.codingmatters.poom.caches.management.stores.CacheStore;
import org.codingmatters.poom.services.logging.CategorizedLogger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimpleRedisCacheStore<K, V> implements CacheStore<K, V> {
    static private final CategorizedLogger log = CategorizedLogger.getLogger(SimpleRedisCacheStore.class);

    private final JedisPool pool;
    private final String keyPrefix;

    private final FunctionThrowingIOException<K, String> keyToString;
    private final FunctionThrowingIOException<String, K> stringToKey;
    private final FunctionThrowingIOException<V, String> valueToString;
    private final FunctionThrowingIOException<String, V> stringToValue;

    public SimpleRedisCacheStore(JedisPool pool, String keyPrefix, FunctionThrowingIOException<K, String> keyToString, FunctionThrowingIOException<String, K> stringToKey, FunctionThrowingIOException<V, String> valueToString, FunctionThrowingIOException<String, V> stringToValue) {
        this.pool = pool;
        this.keyPrefix = keyPrefix;
        this.keyToString = keyToString;
        this.stringToKey = stringToKey;
        this.valueToString = valueToString;
        this.stringToValue = stringToValue;
    }

    @Override
    public Optional<V> get(K k) {
        try (Jedis client = pool.getResource()) {
            String redisValue = client.get(this.redisKey(k));
            if(redisValue == null || redisValue.equals("nil")) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(this.stringToValue.apply(redisValue));
            }
        } catch (JedisException | IOException e) {
            log.error("error reaching redis cache", e);
            return null;
        }
    }

    @Override
    public void store(K k, V v) {
        try (Jedis client = pool.getResource()) {
            client.set(this.redisKey(k), this.valueToString.apply(v));
        } catch (JedisException | IOException e) {
            log.error("error storing to redis cache", e);
        }
    }

    @Override
    public boolean has(K k) {
        try (Jedis client = pool.getResource()) {
            return !client.keys(this.redisKey(k)).isEmpty();
        } catch (JedisException e) {
            log.warn("error looking up key in redis cache", e);
            return false;
        }
    }

    @Override
    public void remove(K k) {
        try (Jedis client = pool.getResource()) {
            client.del(this.redisKey(k));
        } catch (JedisException e) {
            log.error("error removing key from redis cache", e);
        }
    }

    @Override
    public void clear() {
        try (Jedis client = pool.getResource()) {
            String[] keys = client.keys(this.keyPrefix + ":*").toArray(new String[0]);
            if (keys.length > 0) {
                client.del(keys);
            }
        } catch (JedisException e) {
            log.error("error clearing redis cache", e);
        }
    }

    @Override
    public Set<K> keys() {
        try (Jedis client = pool.getResource()) {
            Set<String> redisKeys = client.keys(this.keyPrefix + ":*");
            Set<K> result = new HashSet<>();
            for (String rk : redisKeys) {
                try {
                    result.add(this.stringToKey.apply(rk.substring(this.keyPrefix.length() + 1)));
                } catch (IOException e) {
                    log.error("error reading key {}, ignoring", rk);
                }
            }
            return result;
        } catch (JedisException e) {
            log.error("error listing key from redis cache", e);
            return Collections.emptySet();
        }
    }

    private String redisKey(K k) {
        try {
            return String.format("%s:%s", this.keyPrefix, this.keyToString.apply(k));
        } catch (IOException e) {
            log.error("error computing key {}, return null", k);
            return null;
        }
    }
}
