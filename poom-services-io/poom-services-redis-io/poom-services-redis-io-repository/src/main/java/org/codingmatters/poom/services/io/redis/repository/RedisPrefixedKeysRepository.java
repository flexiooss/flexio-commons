package org.codingmatters.poom.services.io.redis.repository;

import org.codingmatters.poom.services.domain.exceptions.RepositoryException;
import org.codingmatters.poom.services.logging.CategorizedLogger;
import org.codingmatters.poom.servives.domain.entities.Entity;
import org.codingmatters.poom.servives.domain.entities.ImmutableEntity;
import org.codingmatters.poom.servives.domain.entities.PagedEntityList;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class RedisPrefixedKeysRepository<V, Q> extends RedisAbstractRepository<V, Q> {
    static private final CategorizedLogger log = CategorizedLogger.getLogger(RedisPrefixedKeysRepository.class);

    private final Optional<Long> ttl;
    private final String keyPrefix;

    public RedisPrefixedKeysRepository(JedisPool pool, String keyPrefix) {
        this(pool, keyPrefix, null);
    }
    public RedisPrefixedKeysRepository(JedisPool pool, String keyPrefix, Long ttl) {
        super(pool);
        this.keyPrefix = keyPrefix;
        if(ttl != null && ttl > 0) {
            this.ttl = Optional.of(ttl);
        } else {
            this.ttl = Optional.empty();
        }
    }

    @Override
    protected ImmutableEntity<V> doStore(Jedis client, String id, RedisAbstractRepository<V, Q>.StorableValue storableValue, StorePrecondition precondition) throws IOException, RepositoryException {
        final String key = this.redisKey(id);
        switch (precondition) {
            case CREATE_ONLY:
                long count = client.setnx(key, this.storable(storableValue));
                if(count != 1) throw new RepositoryException("entity already exists : " + id);
                break;
            case UPDATE_ONLY:
                client.set(key, this.storable(storableValue));
                break;
        }
        this.expire(client, key);
        return new ImmutableEntity<>(id, storableValue.version, storableValue.value);
    }

    @Override
    protected ImmutableEntity<V> doRetrieve(Jedis client, String id) throws RepositoryException {
        String key = this.redisKey(id);
        String redisValue = client.get(key);
        if(redisValue == null || redisValue.equals("nil")) {
            return null;
        } else {
            try {
                StorableValue storable = this.fromStored(redisValue);
                this.expire(client, key);
                return new ImmutableEntity<>(id, storable.version, storable.value);
            } catch (IOException e) {
                throw new RepositoryException("error unmarshalling redis value", e);
            }
        }
    }

    @Override
    protected void doDelete(Entity<V> entity, Jedis client) throws RepositoryException {
        long deleted = client.del(this.redisKey(entity.id()));
        if(deleted == 0L) throw new RepositoryException("cannot delete entity, no such entity in store : " + entity.id());
    }

    @Override
    protected PagedEntityList.DefaultPagedEntityList<V> doGetPage(long start, long end, Jedis client) throws IOException {
        List<String> allKeys = client.keys(this.keyPrefix + "::*").stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        List<String> keys = allKeys.subList(
                (int) start,
                (int) Math.min(end + 1, allKeys.size())
        );
        List<String> results;
        if(! keys.isEmpty()) {
            results = client.mget(keys.toArray(new String[0]));
        } else {
            results = Collections.emptyList();
        }
        return new PagedEntityList.DefaultPagedEntityList<>(start, start + results.size(), allKeys.size(),
                this.fromStoredList(keys.stream().map(s -> s.substring(this.keyPrefix.length() + 2)).collect(Collectors.toList()), results)
        );
    }

    private String redisKey(String id) {
        return this.keyPrefix + "::" + id;
    }

    private void expire(Jedis client, String redisKey) {
        if(this.ttl.isPresent()) {
            client.expire(redisKey, this.ttl.get());
        }
    }
}
