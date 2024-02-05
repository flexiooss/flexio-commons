package org.codingmatters.poom.services.io.redis.repository;

import org.codingmatters.poom.services.domain.entities.Entity;
import org.codingmatters.poom.services.domain.entities.ImmutableEntity;
import org.codingmatters.poom.services.domain.entities.PagedEntityList;
import org.codingmatters.poom.services.domain.exceptions.RepositoryException;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.logging.CategorizedLogger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class RedisAbstractRepository<V, Q> implements Repository<V, Q> {
    static private final CategorizedLogger log = CategorizedLogger.getLogger(RedisAbstractRepository.class);
    private static final String SEPARATOR = "||";


    protected final JedisPool pool;

    protected abstract String marshall(V value) throws IOException;

    protected abstract V unmarshall(String value) throws IOException;

    public RedisAbstractRepository(JedisPool pool) {
        this.pool = pool;
    }


    protected enum StorePrecondition {
        CREATE_ONLY, UPDATE_ONLY
    }

    protected class StorableValue {
        public final BigInteger version;
        public final V value;

        public StorableValue(BigInteger version, V value) {
            this.version = version;
            this.value = value;
        }
    }

    protected abstract ImmutableEntity<V> doStore(Jedis client, String id, StorableValue storableValue, StorePrecondition precondition) throws IOException, RepositoryException;

    protected abstract ImmutableEntity<V> doRetrieve(Jedis client, String id) throws RepositoryException;

    protected abstract void doDelete(Entity<V> entity, Jedis client) throws RepositoryException;

    protected abstract PagedEntityList.DefaultPagedEntityList<V> doGetPage(long start, long end, Jedis client) throws IOException;


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
            this.doDelete(entity, client);
        } catch (JedisException e) {
            throw new RepositoryException("error removing key from redis repository", e);
        }
    }

    @Override
    public PagedEntityList<V> all(long start, long end) throws RepositoryException {
        try (Jedis client = pool.getResource()) {
            return this.doGetPage(start, end, client);
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


    protected String storable(StorableValue storableValue) throws IOException {
        return this.marshall(storableValue.value) + SEPARATOR + storableValue.version.toString();
    }

    protected StorableValue fromStored(String stored) throws IOException {
        int index = stored.lastIndexOf(SEPARATOR);
        String value = stored.substring(0, index);
        String version = stored.substring(index + 2);
        return new StorableValue(new BigInteger(version), this.unmarshall(value));
    }

    protected List<Entity<V>> fromStoredList(List<String> keys, List<String> storedList) throws IOException {
        List<Entity<V>> result = new ArrayList<>(storedList.size());
        for (int i = 0; i < keys.size(); i++) {
            if (storedList.size() > i) {
                StorableValue storable = this.fromStored(storedList.get(i));
                result.add(new ImmutableEntity<>(keys.get(i), storable.version, storable.value));
            }
        }
        return result;
    }
}
