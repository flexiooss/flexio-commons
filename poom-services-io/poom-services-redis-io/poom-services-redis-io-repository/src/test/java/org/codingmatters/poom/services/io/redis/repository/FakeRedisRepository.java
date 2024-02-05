package org.codingmatters.poom.services.io.redis.repository;

import org.codingmatters.poom.services.domain.entities.Entity;
import org.codingmatters.poom.services.domain.entities.ImmutableEntity;
import org.codingmatters.poom.services.domain.entities.PagedEntityList;
import org.codingmatters.poom.services.domain.exceptions.RepositoryException;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class FakeRedisRepository extends RedisAbstractRepository<String, String> {
    public FakeRedisRepository() {
        super(null);
    }

    @Override
    protected String marshall(String value) throws IOException {
        return value;
    }

    @Override
    protected String unmarshall(String value) throws IOException {
        return value;
    }

    @Override
    protected ImmutableEntity<String> doStore(Jedis client, String id, RedisAbstractRepository<String, String>.StorableValue storableValue, StorePrecondition precondition) throws IOException, RepositoryException {
        return null;
    }

    @Override
    protected ImmutableEntity<String> doRetrieve(Jedis client, String id) throws RepositoryException {
        return null;
    }

    @Override
    protected void doDelete(Entity<String> entity, Jedis client) throws RepositoryException {

    }

    @Override
    protected PagedEntityList.DefaultPagedEntityList<String> doGetPage(long start, long end, Jedis client) throws IOException {
        return null;
    }
}
