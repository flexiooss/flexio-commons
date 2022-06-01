package org.codingmatters.poom.services.io.redis.repository;

import org.codingmatters.poom.services.domain.exceptions.RepositoryException;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.logging.CategorizedLogger;
import org.codingmatters.poom.servives.domain.entities.Entity;
import org.codingmatters.poom.servives.domain.entities.ImmutableEntity;
import org.codingmatters.poom.servives.domain.entities.PagedEntityList;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public abstract class RedisHashRepository<V, Q> implements Repository<V, Q> {
    static private final CategorizedLogger log = CategorizedLogger.getLogger(RedisHashRepository.class);

    private final JedisPool pool;
    private final String hashName;

    protected abstract String marshall(V value) throws IOException;
    protected abstract V unmarshall(String value) throws IOException;

    public RedisHashRepository(JedisPool pool, String hashName) {
        this.pool = pool;
        this.hashName = hashName;
    }

    @Override
    public Entity<V> create(V withValue) throws RepositoryException {
        return this.createWithIdAndVersion(UUID.randomUUID().toString(), BigInteger.ONE, withValue);
    }

    @Override
    public Entity<V> createWithId(String id, V withValue) throws RepositoryException {
        return this.createWithIdAndVersion(id, BigInteger.ONE, withValue);
    }

    @Override
    public Entity<V> createWithIdAndVersion(String id, BigInteger version, V withValue) throws RepositoryException {
        try (Jedis client = pool.getResource()) {
            return this.doStore(client, id, new StorableValue(version, withValue), StorePrecondition.CREATE_ONLY);
        } catch (JedisException e) {
            throw new RepositoryException("error storing to redis repository", e);
        } catch (IOException e) {
            throw new RepositoryException("error marshalling value to redis repository", e);
        }
    }

    @Override
    public Entity<V> retrieve(String id) throws RepositoryException {
        try (Jedis client = pool.getResource()) {
            return this.doRetrieve(client, id);
        } catch (JedisException e) {
            throw new RepositoryException("error reaching redis repository", e);
        }
    }

    @Override
    public Entity<V> update(Entity<V> entity, V v) throws RepositoryException {
        try (Jedis client = pool.getResource()) {
            ImmutableEntity<V> current = this.doRetrieve(client, entity.id());
            try {
                return this.doStore(client, entity.id(), new StorableValue(current.version().add(BigInteger.ONE), v), StorePrecondition.UPDATE_ONLY);
            } catch (IOException e) {
                throw new RepositoryException("error updating redis entity", e);
            }
        }
    }

    @Override
    public void delete(Entity<V> entity) throws RepositoryException {
        try (Jedis client = pool.getResource()) {
            long deleted = client.hdel(this.hashName, entity.id());
            if(deleted == 0L) throw new RepositoryException("cannot delete entity, no such entity in store : " + entity.id());
        } catch (JedisException e) {
            throw new RepositoryException("error removing key from redis repository", e);
        }
    }

    @Override
    public PagedEntityList<V> all(long start, long end) throws RepositoryException {
        try (Jedis client = pool.getResource()) {
            List<String> allKeys = client.hkeys(this.hashName).stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            List<String> keys = allKeys.subList(
                    (int)start,
                    (int) Math.min(end + 1, allKeys.size())
            );
            List<String> results;
            if(! keys.isEmpty()) {
                results = client.hmget(this.hashName, keys.toArray(new String[0]));
            } else {
                results = Collections.emptyList();
            }
            return new PagedEntityList.DefaultPagedEntityList<>(start, start + results.size(), allKeys.size(), this.fromStoredList(keys, results));
        } catch (JedisException e) {
            throw new RepositoryException("error listing entities from redis repository", e);
        } catch (IOException e) {
            throw new RepositoryException("error parsing entities from redis repository", e);
        }
    }

    @Override
    public PagedEntityList<V> search(Q q, long l, long l1) throws RepositoryException {
        throw new UnsupportedOperationException();
    }

    private enum StorePrecondition {
        CREATE_ONLY,
        UPDATE_ONLY
    }

    private ImmutableEntity<V> doStore(Jedis client, String id, StorableValue storableValue, StorePrecondition precondition) throws IOException, RepositoryException {
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

    private ImmutableEntity<V> doRetrieve(Jedis client, String id) throws RepositoryException {
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

    private String storable(StorableValue storableValue) throws IOException {
        return  this.marshall(storableValue.value)+ "||" + storableValue.version.toString();
    }

    private StorableValue fromStored(String stored) throws IOException {
        String[] split = stored.split("\\|\\|");
        return new StorableValue(new BigInteger(split[1]), this.unmarshall(split[0]));
    }

    private List<Entity<V>> fromStoredList(List<String> keys, List<String> storedList) throws IOException {
        List<Entity<V>> result = new ArrayList<>(storedList.size());
        for (int i = 0; i < keys.size(); i++) {
            if(storedList.size() > i) {
                StorableValue storable = this.fromStored(storedList.get(i));
                result.add(new ImmutableEntity<>(keys.get(i), storable.version, storable.value));
            }
        }
        return result;
    }

    class StorableValue {
        public final BigInteger version;
        public final V value;

        public StorableValue(BigInteger version, V value) {
            this.version = version;
            this.value = value;
        }
    }
}
