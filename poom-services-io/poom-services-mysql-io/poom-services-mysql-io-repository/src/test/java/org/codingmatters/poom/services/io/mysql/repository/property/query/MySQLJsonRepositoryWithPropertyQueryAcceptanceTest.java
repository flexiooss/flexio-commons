package org.codingmatters.poom.services.io.mysql.repository.property.query;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.docker.DockerResource;
import org.codingmatters.generated.QAValue;
import org.codingmatters.generated.json.QAValueReader;
import org.codingmatters.generated.json.QAValueWriter;
import org.codingmatters.poom.services.domain.entities.PagedEntityList;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.property.query.PropertyQueryAcceptanceTest;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.io.mysql.repository.MariaDBResource;
import org.codingmatters.poom.services.io.mysql.repository.MySQLJsonRepository;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


public class MySQLJsonRepositoryWithPropertyQueryAcceptanceTest extends PropertyQueryAcceptanceTest {

    @ClassRule(order = 0)
    static public DockerResource docker = DockerResource.client();

    @ClassRule(order = 1)
    static public MariaDBResource mariaDBResource = new MariaDBResource(docker);

    private final JsonFactory jsonFactory = new JsonFactory();

    @Override
    @Before
    public void setUp() throws Exception {
        mariaDBResource.wipe();
        super.setUp();
    }

    @Override
    protected Repository<QAValue, PropertyQuery> createRepository() {
        try {
            return new MySQLJsonRepository<QAValue, PropertyQuery>(
                    this.mariaDBResource.ds(),
                    "repository",
                    this.jsonFactory,
                    (generator, value) -> new QAValueWriter().write(generator, value),
                    parser -> new QAValueReader().read(parser),
                    new PropertyQueryToDocQueryParser()
            );
        } catch (Exception e) {
            throw new AssertionError("error creating repository", e);
        }
    }

    @Override
    @Test
    public void whenNoFilter_andOrderByTwoProperties__thenAllValuesAreOrdered() throws Exception {
        /*
         * Just like MongoDB, MariaDB sorts NULL before mainly everything
         */
        PagedEntityList<QAValue> actual = this.repository().search(PropertyQuery.builder()
                .sort("boolProp, stringProp")
                .build(), 0, 1000);

        assertThat(actual.valueList(), hasSize(100));
        assertThat(actual.valueList().get(0).stringProp(), is("002"));
        assertThat(actual.valueList().get(1).stringProp(), is("005"));
    }


    @Override
    public void givenFilterOnManyDateProperty__whenContainsAny__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyDateProperty__whenContainsAny__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyTZDatetimeProperty__whenContainsAll__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyTZDatetimeProperty__whenContainsAll__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyTimeProperty__whenContainsAll__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyTimeProperty__whenContainsAll__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyDatetimeProperty__whenContainsAll__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyDatetimeProperty__whenContainsAll__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyLongProperty__whenContains__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyLongProperty__whenContains__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyStringProperty__whenContainsAny__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyStringProperty__whenContainsAny__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyDoubleProperty__whenContainsAll__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyDoubleProperty__whenContainsAll__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyLongProperty__whenContainsAll__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyLongProperty__whenContainsAll__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyIntegerProperty__whenContains__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyIntegerProperty__whenContains__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyTimeProperty__whenContains__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyTimeProperty__whenContains__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyBooleanProperty__whenContainsAll__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyBooleanProperty__whenContainsAll__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyDateProperty__whenContainsAll__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyDateProperty__whenContainsAll__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyFloatProperty__whenContainsAny__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyFloatProperty__whenContainsAny__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyIntegerProperty__whenContainsAny__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyIntegerProperty__whenContainsAny__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyStringProperty__whenContains__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyStringProperty__whenContains__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyDoubleProperty__whenContains__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyDoubleProperty__whenContains__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyBooleanProperty__whenContains__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyBooleanProperty__whenContains__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyStringProperty__whenContainsAll__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyStringProperty__whenContainsAll__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyFloatProperty__whenContains__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyFloatProperty__whenContains__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyTZDatetimeProperty__whenContainsAny__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyTZDatetimeProperty__whenContainsAny__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyTimeProperty__whenContainsAny__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyTimeProperty__whenContainsAny__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyTZDatetimeProperty__whenContains__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyTZDatetimeProperty__whenContains__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyDatetimeProperty__whenContains__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyDatetimeProperty__whenContains__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyDatetimeProperty__whenContainsAny__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyDatetimeProperty__whenContainsAny__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyDoubleProperty__whenContainsAny__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyDoubleProperty__whenContainsAny__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyLongProperty__whenContainsAny__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyLongProperty__whenContainsAny__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyFloatProperty__whenContainsAll__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyFloatProperty__whenContainsAll__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyIntegerProperty__whenContainsAll__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyIntegerProperty__whenContainsAll__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyBooleanProperty__whenContainsAny__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyBooleanProperty__whenContainsAny__thenSelectedValueReturned");
    }
    @Override
    public void givenFilterOnManyDateProperty__whenContains__thenSelectedValueReturned() {
        System.err.println("NOT IMPELENTED givenFilterOnManyDateProperty__whenContains__thenSelectedValueReturned");
    }

    @Override
    public void givenFilterOnStringProperty__whenIsIn_andEmptyList__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPELENTED givenFilterOnStringProperty__whenIsIn_andEmptyList__thenSelectedValueReturned");
    }

    @Override
    public void givenFilterOnStringProperty__whenIsIn_andNullInList__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPELENTED givenFilterOnStringProperty__whenIsIn_andNullInList__thenSelectedValueReturned");
    }

    @Override
    public void givenFilterOnStringProperty__whenStartsWithAny_andEmptyList__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPELENTED givenFilterOnStringProperty__whenStartsWithAny_andEmptyList__thenSelectedValueReturned");
    }

    @Override
    public void givenFilterOnStringProperty__whenEndsWithAny_andEmptyList__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPELENTED givenFilterOnStringProperty__whenEndsWithAny_andEmptyList__thenSelectedValueReturned");
    }

    @Override
    public void givenFilterOnStringProperty__whenContainsAll_andEmptyList__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPELENTED givenFilterOnStringProperty__whenContainsAll_andEmptyList__thenSelectedValueReturned");
    }

    @Override
    public void givenFilterOnStringProperty__whenContainsAny_andEmptyList__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPELENTED givenFilterOnStringProperty__whenContainsAll_andEmptyList__thenSelectedValueReturned");
    }

    @Override
    public void givenFilterOnStringPropertyProperty__whenRegexAndInsensitive__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPELENTED givenFilterOnStringPropertyProperty__whenRegexAndInsensitive__thenSelectedValueReturned");
    }

    @Override
    public void givenFilterOnStringPropertyProperty__whenRegexAndSensitive__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPELENTED givenFilterOnStringPropertyProperty__whenRegexAndSensitive__thenSelectedValueReturned");
    }


    @Override
    public void givenFilterOnManyStringProperty__whenAnyIn__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPLEMENTED FEATURE : AnyIn");
    }

    @Override
    public void givenFilterOnManyStringProperty__whenAnyIn_andEmptyList__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPLEMENTED FEATURE : AnyIn");
    }

    @Override
    public void givenFilterOnManyStringProperty__whenAnyIn_andNullInList__thenSelectedValueReturned() throws Exception {
        System.out.println("NOT IMPLEMENTED FEATURE : AnyIn");
    }
}
