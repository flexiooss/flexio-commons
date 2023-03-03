package io.flexio.io.mongo.repository.property.query;

import io.flexio.docker.DockerResource;
import io.flexio.io.mongo.repository.MongoCollectionRepository;
import io.flexio.io.mongo.repository.domain.MongoValueWithObject;
import io.flexio.io.mongo.repository.domain.mongo.MongoValueWithObjectMongoMapper;
import io.flexio.services.tests.mongo.MongoResource;
import io.flexio.services.tests.mongo.MongoTest;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.domain.entities.PagedEntityList;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class PropertyQuerierTemporalTest {
    public static final String DB = "test-db";
    public static final String COLLECTION = "test-collection";
    @ClassRule
    static public DockerResource docker = MongoTest.docker()
            .started().finallyStarted();

    @Rule
    public MongoResource mongo = MongoTest.mongo(() -> docker)
            .testDB(DB);
    private Repository<MongoValueWithObject, PropertyQuery> repository;

    @Before
    public void setUp() throws Exception {
        PropertyQuerier propertyQuerier = new PropertyQuerier();
        this.repository = MongoCollectionRepository.<MongoValueWithObject, PropertyQuery>repository(DB, COLLECTION)
                .withToDocument(value -> new MongoValueWithObjectMongoMapper().toDocument(value))
                .withToValue(document -> new MongoValueWithObjectMongoMapper().toValue(document))
                .withCheckedFilter(propertyQuerier.filterer())
                .withCheckedSort(propertyQuerier.sorter())
                .build(mongo.newClient());

        this.repository.create(MongoValueWithObject.builder().a("2012").dt(LocalDateTime.of(2012, 05, 23, 12, 42)).build());
        this.repository.create(MongoValueWithObject.builder().a("2004").dt(LocalDateTime.of(2004, 05, 23, 12, 42)).build());
        this.repository.create(MongoValueWithObject.builder().a("2042").dt(LocalDateTime.of(2042, 05, 23, 12, 42)).build());
    }

    @Test
    public void givenFilteringOnDatetime__whenEquals__thenFileterdOk() throws Exception {
        PagedEntityList<MongoValueWithObject> results = this.repository.search(PropertyQuery.builder().filter("dt == 2012-05-23T12:42:00.000").build(), 0, 1000);

        assertThat(results.valueList().stream().map(o -> o.a()).collect(Collectors.toList()), containsInAnyOrder("2012"));
    }

    @Test
    public void givenFilteringOnDatetime__whenNotEquals__thenFileterdOk() throws Exception {
        PagedEntityList<MongoValueWithObject> results = this.repository.search(PropertyQuery.builder().filter("dt != 2012-05-23T12:42:00.000").build(), 0, 1000);

        assertThat(results.valueList().stream().map(o -> o.a()).collect(Collectors.toList()), containsInAnyOrder("2004", "2042"));
    }

    @Test
    public void givenFilteringOnDatetime__whenGte__thenFileterdOk() throws Exception {
        PagedEntityList<MongoValueWithObject> results = this.repository.search(PropertyQuery.builder().filter("dt >= 2012-05-23T12:42:00.000").build(), 0, 1000);

        assertThat(results.valueList().stream().map(o -> o.a()).collect(Collectors.toList()), containsInAnyOrder("2012", "2042"));
    }

    @Test
    public void givenFilteringOnDatetime__whenGt__thenFileterdOk() throws Exception {
        PagedEntityList<MongoValueWithObject> results = this.repository.search(PropertyQuery.builder().filter("dt > 2012-05-23T12:42:00.000").build(), 0, 1000);

        assertThat(results.valueList().stream().map(o -> o.a()).collect(Collectors.toList()), containsInAnyOrder("2042"));
    }

    @Test
    public void givenFilteringOnDatetime__whenLt__thenFileterdOk() throws Exception {
        PagedEntityList<MongoValueWithObject> results = this.repository.search(PropertyQuery.builder().filter("dt < 2012-05-23T12:42:00.000").build(), 0, 1000);

        assertThat(results.valueList().stream().map(o -> o.a()).collect(Collectors.toList()), containsInAnyOrder("2004"));
    }

    @Test
    public void givenFilteringOnDatetime__whenLte__thenFileterdOk() throws Exception {
        PagedEntityList<MongoValueWithObject> results = this.repository.search(PropertyQuery.builder().filter("dt <= 2012-05-23T12:42:00.000").build(), 0, 1000);

        assertThat(results.valueList().stream().map(o -> o.a()).collect(Collectors.toList()), containsInAnyOrder("2004", "2012"));
    }
}
