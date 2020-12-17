package io.flexio.io.mongo.repository.property.query;

import io.flexio.docker.DockerResource;
import io.flexio.io.mongo.repository.MongoCollectionRepository;
import io.flexio.io.mongo.repository.domain.MongoValueWithObject;
import io.flexio.io.mongo.repository.domain.mongo.MongoValueWithObjectMongoMapper;
import io.flexio.io.mongo.repository.property.query.PropertyQuerier;
import io.flexio.services.tests.mongo.MongoResource;
import io.flexio.services.tests.mongo.MongoTest;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.servives.domain.entities.Entity;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PropertyQuerierTest {
    public static final String DB = "test-db";
    public static final String COLLECTION = "test-collection";
    @ClassRule
    static public DockerResource docker = MongoTest.docker()
            .started().finallyStarted();

    @Rule
    public MongoResource mongo = MongoTest.mongo(() -> docker)
            .testDB(DB)
            .importCollectionContent("test-collection-with-object.json", DB, COLLECTION);

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
    }

    @Test
    public void showcollection() throws Exception {
        System.out.println("######################################");
        System.out.println("######################################");
        System.out.println("######################################");
        System.out.println("######################################");
        for (Entity<MongoValueWithObject> entity :this.repository.all(0, 1000)){
            System.out.println(entity);
        }
        System.out.println("######################################");
        System.out.println("######################################");
        System.out.println("######################################");
        System.out.println("######################################");
    }

    @Test
    public void givenFilteringOnToplevelStringProperty_whenEqualsString__thenFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("a == 'yip'").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::a).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder("yip")
        );
    }

    @Test
    public void givenFilteringOnToplevelStringProperty_whenStartswithString__thenFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("a starts with 'yi'").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::a).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder("yip")
        );
    }

    @Test
    public void givenFilteringOnToplevelStringProperty_whenEndswithString__thenFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("a ends with 'ip'").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::a).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder("yip")
        );
    }

    @Test
    public void givenFilteringOnToplevelStringProperty_whenContainsString__thenFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("a contains 'i'").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::a).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder("yip")
        );
    }

    @Test
    public void givenFilteringOnToplevelIntegerProperty_whenEqualsInteger__thenFiltered() throws Exception {
        List<Integer> actual = this.repository.search(PropertyQuery.builder().filter("b == 2").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::b).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder(2)
        );
    }

    @Test
    public void givenFilteringOnToplevelIntegerProperty_whenEqualsString__thenNoMatch() throws Exception {
        List<Integer> actual = this.repository.search(PropertyQuery.builder().filter("b == '2'").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::b).collect(Collectors.toList());
        assertThat(actual, is(empty()));
    }

    @Test
    public void givenFilteringOnToplevelIntegerProperty_whenLowerThanInteger__thenNoMatch() throws Exception {
        List<Integer> actual = this.repository.search(PropertyQuery.builder().filter("b < 4").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::b).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder(1, 2, 3)
        );
    }


    @Test
    public void givenFilteringOnDeepStringProperty_whenLowerThanString__thenFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("c.d < '003'").build(), 0, 1000).valueList()
                .stream().map(o -> o.opt().c().d().orElseGet(() -> null)).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder("001", "002")
        );
    }

    @Test
    public void givenFilteringOnDeepStringProperty__whenEqualsRHSProperty__thenFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("c.e == a").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::a).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder("yip")
        );
    }

    @Test
    public void givenFilteringOnTopLevelStringProperty__whenEqualsRHSProperty__thenFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("a == c.e").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::a).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder("yip")
        );
    }

    @Test
    public void givenFilteringOnTopLevelProperty__whenIsNull__thenFiltered() throws Exception {
        List<Integer> actual = this.repository.search(PropertyQuery.builder().filter("a == null").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::b).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder(6)
        );
    }

    @Test
    public void givenFilteringOnTopLevelProperty__whenIsNotNull__thenFiltered() throws Exception {
        List<Integer> actual = this.repository.search(PropertyQuery.builder().filter("a != null").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::b).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder(1, 2, 3, 4, 5)
        );
    }

    @Test
    public void givenFilteringOnTopLevelStringProperty__whenAnd__thenFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("a == 'yip' && p.q.r == '002'").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::a).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder("yip")
        );
    }

    @Test
    public void givenFilteringOnTopLevelStringProperty__whenOr__thenFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("a == 'yip' || p.q.r == '003'").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::a).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder("yip", "yup")
        );
    }

    @Test
    public void givenFilteringOnTopLevelStringProperty__whenNot__thenFiltered() throws Exception {
        List<String> actual = this.repository.search(PropertyQuery.builder().filter("! a != 'yip'").build(), 0, 1000).valueList()
                .stream().map(MongoValueWithObject::a).collect(Collectors.toList());
        assertThat(actual,
                containsInAnyOrder("yip")
        );
    }

    @Test
    public void givenSortingOnTopLevelIntegerProperty__whenDesc__theResultsAreOrdered() throws Exception {
        assertThat(
                this.repository.search(PropertyQuery.builder().sort("b desc").build(), 0, 1000).valueList()
                        .stream().map(MongoValueWithObject::b).collect(Collectors.toList()),
                contains(6, 5, 4, 3, 2, 1)
        );
    }

}
