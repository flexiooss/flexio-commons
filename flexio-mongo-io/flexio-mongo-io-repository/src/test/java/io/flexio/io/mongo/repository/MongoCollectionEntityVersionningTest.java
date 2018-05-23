package io.flexio.io.mongo.repository;

import com.mongodb.client.model.Filters;
import io.flexio.docker.DockerResource;
import io.flexio.io.mongo.repository.domain.MongoQuery;
import io.flexio.io.mongo.repository.domain.MongoValue;
import io.flexio.io.mongo.repository.domain.mongo.MongoValueMongoMapper;
import io.flexio.services.tests.mongo.MongoResource;
import io.flexio.services.tests.mongo.MongoTest;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.servives.domain.entities.Entity;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MongoCollectionEntityVersionningTest {

    public static final String DB = "test-db";
    public static final String COLLECTION = "test-collection";
    @ClassRule
    static public DockerResource docker = MongoTest.docker()
            .started().finallyStarted();

    @Rule
    public MongoResource mongo = MongoTest.mongo(() -> docker)
            .testDB(DB)
            .importCollectionContent("test-collection.json", DB, COLLECTION);

    private Repository<MongoValue, MongoQuery> repository;

    @Before
    public void setUp() throws Exception {
        this.repository = MongoCollectionRepository.<MongoValue, MongoQuery>repository(DB, COLLECTION)
                .withToDocument(value -> new MongoValueMongoMapper().toDocument(value))
                .withToValue(document -> new MongoValueMongoMapper().toValue(document))
                .withFilter(query -> query.opt().name().isPresent() ? Filters.eq("name", query.name()) : null)
                .build(mongo.newClient());
    }

    @Test
    public void whenEntityCreated__thenVersionIs1() throws Exception {
        assertThat(
                this.repository.create(MongoValue.builder().name("res").build()).version(),
                is(BigInteger.ONE)
        );
    }

    @Test
    public void givenEntityCreated__whenEntityIsUpdated__thenVersionIsIncremented() throws Exception {
        Entity<MongoValue> entity = this.repository.create(MongoValue.builder().name("1").build());

        for (int i = 2; i < 10; i++) {
            entity = this.repository.update(entity, MongoValue.builder().name("" + i).build());
            assertThat("entity version is " + i, entity.version(), is(BigInteger.valueOf(i)));
        }

    }
}
