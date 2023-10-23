package io.flexio.io.mongo.repository.property.query;

import io.flexio.docker.DockerResource;
import io.flexio.io.mongo.repository.MongoCollectionRepository;
import io.flexio.io.mongo.repository.domain.MongoValue;
import io.flexio.io.mongo.repository.domain.MongoValueWithObject;
import io.flexio.io.mongo.repository.domain.mongo.MongoValueMongoMapper;
import io.flexio.io.mongo.repository.domain.mongo.MongoValueWithObjectMongoMapper;
import io.flexio.services.tests.mongo.MongoResource;
import io.flexio.services.tests.mongo.MongoTest;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PropertyQuerierObjectIdTest {
    public static final String DB = "test-db";
    public static final String COLLECTION = "test-collection";
    @ClassRule
    static public DockerResource docker = MongoTest.docker()
            .started().finallyStarted();

    @Rule
    public MongoResource mongo = MongoTest.mongo(() -> docker)
            .testDB(DB)
            .importCollectionContent("test-collection.json", DB, COLLECTION);

    private Repository<MongoValue, PropertyQuery> repository;

    @Before
    public void setUp() throws Exception {
        PropertyQuerier propertyQuerier = new PropertyQuerier();
        this.repository = MongoCollectionRepository.<MongoValue, PropertyQuery>repository(DB, COLLECTION)
                .withToDocument(value -> new MongoValueMongoMapper().toDocument(value))
                .withToValue(document -> new MongoValueMongoMapper().toValue(document))
                .buildWithPropertyQuery(mongo.newClient());
    }


    @Test
    public void givenIdEq__whenIdStoredAsString__theFiltered() throws Exception {
        assertThat(
                this.repository.search(PropertyQuery.builder().filter("_id == '-FlexLogs-59fc2c5e86ee7'").build(), 0, 1000).valueList()
                        .stream().map(MongoValue::slug).collect(Collectors.toList()),
                contains("flex-log")
        );
    }

    @Test
    public void givenIdEq__whenIdStoredAsObjectID__theFiltered() throws Exception {
        assertThat(
                this.repository.search(PropertyQuery.builder().filter("_id == '59fc47acef850400c41f8f57'").build(), 0, 1000).valueList()
                        .stream().map(MongoValue::slug).collect(Collectors.toList()),
                contains("tweet")
        );
    }

    @Test
    public void givenIdNeq__whenIdStoredAsString__theFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("_id != '-FlexLogs-59fc2c5e86ee7'").build(), 0, 1000).valueList()
                .stream().map(MongoValue::slug).collect(Collectors.toList());
        assertThat(actual, hasSize(21));
        assertThat(actual, not(contains("flex-log")));
    }

    @Test
    public void givenIdNeq__whenIdStoredAsObjectID__theFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("_id != '59fc47acef850400c41f8f57'").build(), 0, 1000).valueList()
                .stream().map(MongoValue::slug).collect(Collectors.toList());
        assertThat(actual, hasSize(21));
        assertThat(actual, not(contains("tweet")));
    }




    @Test
    public void givenIdIn__whenIdStoredAsString__theFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("_id in ('-FlexLogs-59fc2c5e86ee7')").build(), 0, 1000).valueList()
                .stream().map(MongoValue::slug).collect(Collectors.toList());
        System.out.println(actual);
        assertThat(
                actual,
                contains("flex-log")
        );
    }

    @Test
    public void givenIdIn__whenIdStoredAsObjectID__theFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("_id in ('59fc47acef850400c41f8f57')").build(), 0, 1000).valueList()
                .stream().map(MongoValue::slug).collect(Collectors.toList());
        System.out.println(actual);
        assertThat(
                actual,
                contains("tweet")
        );
    }

    @Test
    public void givenIdIn__whenIdStoredAsStringOrOid__theFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("_id in ('-FlexLogs-59fc2c5e86ee7', '59fc47acef850400c41f8f57')").build(), 0, 1000).valueList()
                .stream().map(MongoValue::slug).collect(Collectors.toList());
        System.out.println(actual);
        assertThat(
                actual,
                contains("flex-log", "tweet")
        );
    }



}
