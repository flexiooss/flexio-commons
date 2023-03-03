package io.flexio.io.mongo.repository.property.query;

import io.flexio.docker.DockerResource;
import io.flexio.io.mongo.repository.MongoCollectionRepository;
import io.flexio.services.tests.mongo.MongoResource;
import io.flexio.services.tests.mongo.MongoTest;
import org.codingmatters.generated.QAValue;
import org.codingmatters.generated.mongo.QAValueMongoMapper;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.property.query.PropertyQueryAcceptanceTest;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.domain.entities.PagedEntityList;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class MongoCollectionRepositoryPropertyQueryAcceptanceTest extends PropertyQueryAcceptanceTest {

    public static final String DB = "test-db";
    public static final String COLLECTION = "test-collection";
    @ClassRule
    static public DockerResource docker = MongoTest.docker()
            .started().finallyStarted();

    @Rule
    public MongoResource mongo = MongoTest.mongo(() -> docker)
            .testDB(DB);

    @Override
    protected Repository<QAValue, PropertyQuery> createRepository() {
        QAValueMongoMapper mapper = new QAValueMongoMapper();
        return MongoCollectionRepository.<QAValue, PropertyQuery>repository(DB, COLLECTION)
                .withToDocument(value -> mapper.toDocument(value))
                .withToValue(document -> mapper.toValue(document))
                .buildWithPropertyQuery(mongo.newClient())
                ;
    }

    @Override
    protected String ts(String full) {
        /*
            MongoDB date storage with java client is based on java.utils.Date with is
            only precise to millis, not nanos.
         */

        // 1985-06-17T12:33.123456789+05:00
        if(full.indexOf('+') != -1) {
            String before = full.substring(0, full.indexOf('+') - 6);
            String after = full.substring(full.indexOf('+'));
            return before + after;
        }
        // "1985-09-18T12:42:33.123456789"
        return full.substring(0, full.length() - 6);
    }

    @Override
    @Test
    public void whenNoFilter_andOrderByTwoProperties__thenAllValuesAreOrdered() throws Exception {
        /*
         * MongoDB sorts NULL before mainly everything :
         * https://docs.mongodb.com/manual/reference/bson-type-comparison-order/#bson-types-comparison-order
         */
        PagedEntityList<QAValue> actual = this.repository().search(PropertyQuery.builder()
                .sort("boolProp, stringProp")
                .build(), 0, 1000);

        assertThat(actual.valueList(), hasSize(100));
        assertThat(actual.valueList().get(0).stringProp(), is("002"));
        assertThat(actual.valueList().get(1).stringProp(), is("005"));
    }

    @Override
    public void givenFilterOnStringProperty__whenStartsWithProperty__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPLEMENTED FEATURE : StartsWithProperty");
    }
    @Override
    public void givenFilterOnStringProperty__whenContainsProperty__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPLEMENTED FEATURE : ContainsProperty");
    }
    @Override
    public void givenFilterOnStringProperty__whenEndsWithProperty__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPLEMENTED FEATURE : EndsWithProperty");
    }
}
