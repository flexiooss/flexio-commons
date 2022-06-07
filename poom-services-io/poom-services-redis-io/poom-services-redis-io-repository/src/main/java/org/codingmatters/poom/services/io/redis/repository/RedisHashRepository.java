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
import java.util.stream.Collectors;

public abstract class RedisHashRepository<V, Q> extends RedisAbstractRepository<V, Q> {
    static private final CategorizedLogger log = CategorizedLogger.getLogger(RedisHashRepository.class);

    private final String hashName;

    protected RedisHashRepository(JedisPool pool, String hashName) {
        super(pool);
        this.hashName = hashName;
    }


    protected ImmutableEntity<V> doStore(Jedis client, String id, StorableValue storableValue, StorePrecondition precondition) throws IOException, RepositoryException {
        switch (precondition) {
            case CREATE_ONLY:
                long count = client.hsetnx(this.hashName, id, this.storable(storableValue));
                if(count != 1) throw new RepositoryException("entity already exists : " + id);
                break;
            case UPDATE_ONLY:
                client.hset(this.hashName, id, this.storable(storableValue));
                break;
        }

        return new ImmutableEntity<>(id, storableValue.version, storableValue.value);
    }

    protected ImmutableEntity<V> doRetrieve(Jedis client, String id) throws RepositoryException {
        String redisValue = client.hget(this.hashName, id);
        if(redisValue == null || redisValue.equals("nil")) {
            return null;
        } else {
            try {
                StorableValue storable = this.fromStored(redisValue);
                return new ImmutableEntity<>(id, storable.version, storable.value);
            } catch (IOException e) {
                throw new RepositoryException("error unmarshalling redis value", e);
            }
        }
    }

    protected void doDelete(Entity<V> entity, Jedis client) throws RepositoryException {
        long deleted = client.hdel(this.hashName, entity.id());
        if(deleted == 0L) throw new RepositoryException("cannot delete entity, no such entity in store : " + entity.id());
    }

    protected PagedEntityList.DefaultPagedEntityList<V> doGetPage(long start, long end, Jedis client) throws IOException {
        List<String> allKeys = client.hkeys(this.hashName).stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        List<String> keys = allKeys.subList(
                (int) start,
                (int) Math.min(end + 1, allKeys.size())
        );
        List<String> results;
        if(! keys.isEmpty()) {
            results = client.hmget(this.hashName, keys.toArray(new String[0]));
        } else {
            results = Collections.emptyList();
        }
        return new PagedEntityList.DefaultPagedEntityList<>(start, start + results.size(), allKeys.size(), this.fromStoredList(keys, results));
    }
}
