package org.codingmatters.poom.services.io.redis.cache;

import org.codingmatters.poom.caches.management.stores.CacheStore;
import org.codingmatters.poom.services.logging.CategorizedLogger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimpleRedisCacheStore<K, V> implements CacheStore<K, V> {
    static private final CategorizedLogger log = CategorizedLogger.getLogger(SimpleRedisCacheStore.class);

    private final JedisPool pool;
    private final String keyPrefix;

    private final Function<K, String> keyToString;
    private final Function<String, K> stringToKey;
    private final Function<V, String> valueToString;
    private final Function<String, V> stringToValue;

    public SimpleRedisCacheStore(JedisPool pool, String keyPrefix, Function<K, String> keyToString, Function<String, K> stringToKey, Function<V, String> valueToString, Function<String, V> stringToValue) {
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
        } catch (JedisException e) {
            log.error("error reaching redis cache", e);
            return null;
        }
    }

    @Override
    public void store(K k, V v) {
        try (Jedis client = pool.getResource()) {
            client.set(this.redisKey(k), this.valueToString.apply(v));
        } catch (JedisException e) {
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
            return redisKeys.stream()
                    .map(rk -> this.stringToKey.apply(rk.substring(this.keyPrefix.length() + 1)))
                    .collect(Collectors.toSet());
        } catch (JedisException e) {
            log.error("error listing key from redis cache", e);
            return Collections.emptySet();
        }
    }

    private String redisKey(K k) {
        return String.format("%s:%s", this.keyPrefix, this.keyToString.apply(k));
    }
}
