package org.codingmatters.poom.services.io.mysql.repository.property.query;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.docker.DockerResource;
import org.codingmatters.generated.ComplexValue;
import org.codingmatters.generated.complexvalue.Nested;
import org.codingmatters.generated.complexvalue.nested.Deep;
import org.codingmatters.generated.json.ComplexValueReader;
import org.codingmatters.generated.json.ComplexValueWriter;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.io.mysql.repository.MariaDBResource;
import org.codingmatters.poom.services.io.mysql.repository.MySQLJsonRepository;
import org.codingmatters.poom.servives.domain.entities.Entity;
import org.codingmatters.poom.servives.domain.entities.PagedEntityList;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MySQLJsonRepositoryWithPropertyQueryTest {

    @ClassRule
    static public DockerResource docker = DockerResource.client();

    @Rule
    public MariaDBResource mariaDBResource = new MariaDBResource(docker);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final JsonFactory jsonFactory = new JsonFactory();
    private MySQLJsonRepository<ComplexValue, PropertyQuery> repository;

    @Before
    public void setUp() throws Exception {
        this.repository = new MySQLJsonRepository<ComplexValue, PropertyQuery>(
                this.mariaDBResource.ds(),
                "repository",
                this.jsonFactory,
                (generator, value) -> new ComplexValueWriter().write(generator, value),
                parser -> new ComplexValueReader().read(parser),
                new PropertyQueryToDocQueryParser()
        );
        for (int i = 0; i < 100; i++) {
            this.repository.create(ComplexValue.builder()
                    .stringProp("%03d", i)
                    .integerProp(i)
                    .longProp((long) i)
                    .floatProp(i + 0.2f)
                    .nested(Nested.builder()
                            .nestedProp("%03d", 100 - i)
                            .deep(Deep.builder().deepProp("04").build())
                            .build())
                    .build());
        }
    }

    @Test
    public void whenNoFilter_andNoOrderBy__thenAllValuesReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder().build(), 0, 1000);

        assertThat(actual.total(), is(this.repository.all(0, 0).total()));
    }

    @Test
    public void givenFilterOnStringProperty__whenIsEqual__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp == '006'")
                .build(), 0, 1000);

        assertThat(actual.total(), is(1L));
        assertThat(actual.get(0).value().stringProp(), is("006"));
    }

    @Test
    public void givenFilterOnIntegerProperty__whenIsEqual__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("integerProp == 6")
                .build(), 0, 1000);

        assertThat(actual.total(), is(1L));
        assertThat(actual.get(0).value().integerProp(), is(6));
    }

    @Test
    public void givenFilterOnLongProperty__whenIsEqual__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("longProp == 6")
                .build(), 0, 1000);

        assertThat(actual.total(), is(1L));
        assertThat(actual.get(0).value().longProp(), is(6L));
    }

    @Test
    public void givenFilterOnFloatProperty__whenIsEqual__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("floatProp == 6.2")
                .build(), 0, 1000);

        assertThat(actual.total(), is(1L));
        assertThat(actual.get(0).value().floatProp(), is(6.2f));
    }

    @Test
    public void givenFilterOnStringNestedProperty__whenIsEqual__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("nested.nestedProp == '006'")
                .build(), 0, 1000);

        assertThat(actual.total(), is(1L));
        assertThat(actual.get(0).value().nested().nestedProp(), is("006"));
    }

    @Test
    public void givenFilterOnStringProperty__whenIsEqualProperty__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp == nested.nestedProp")
                .build(), 0, 1000);

        assertThat(actual.total(), is(1L));
        assertThat(actual.get(0).value().stringProp(), is("050"));
        assertThat(actual.get(0).value().nested().nestedProp(), is("050"));
    }

    @Test
    public void givenFilterOnStringProperty__whenNotIsEqual__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp != '006'")
                .build(), 0, 1000);

        assertThat(actual.total(), is(99L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                not(hasItemInArray("006"))
        );
    }

    @Test
    public void givenFilterOnIntegerProperty__whenNotIsEqual__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("integerProp != 6")
                .build(), 0, 1000);

        assertThat(actual.total(), is(99L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.integerProp()).toArray(),
                not(hasItemInArray(6))
        );
    }

    @Test
    public void givenFilterOnLongProperty__whenNotIsEqual__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("longProp != 6")
                .build(), 0, 1000);

        assertThat(actual.total(), is(99L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.longProp()).toArray(),
                not(hasItemInArray(6L))
        );
    }

    @Test
    public void givenFilterOnFloatProperty__whenNotIsEqual__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("floatProp != 6.2")
                .build(), 0, 1000);

        assertThat(actual.total(), is(99L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.floatProp()).toArray(),
                not(hasItemInArray(6.2f))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenNotIsEqualProperty__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp != nested.nestedProp")
                .build(), 0, 1000);

        assertThat(actual.total(), is(99L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                not(hasItemInArray("050"))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenIsNull__thenSelectedValueReturned() throws Exception {
        Entity<ComplexValue> entity = this.repository.create(ComplexValue.builder().stringProp(null).build());

        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp == null")
                .build(), 0, 1000);

        assertThat(actual.total(), is(1L));
        assertThat(actual.get(0).id(), is(entity.id()));
    }

    @Test
    public void givenFilterOnIntegerProperty__whenIsNull__thenSelectedValueReturned() throws Exception {
        Entity<ComplexValue> entity = this.repository.create(ComplexValue.builder().integerProp(null).build());

        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("integerProp == null")
                .build(), 0, 1000);

        assertThat(actual.total(), is(1L));
        assertThat(actual.get(0).id(), is(entity.id()));
    }

    @Test
    public void givenFilterOnLongProperty__whenIsNull__thenSelectedValueReturned() throws Exception {
        Entity<ComplexValue> entity = this.repository.create(ComplexValue.builder().longProp(null).build());

        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("longProp == null")
                .build(), 0, 1000);

        assertThat(actual.total(), is(1L));
        assertThat(actual.get(0).id(), is(entity.id()));
    }

    @Test
    public void givenFilterOnFloatProperty__whenIsNull__thenSelectedValueReturned() throws Exception {
        Entity<ComplexValue> entity = this.repository.create(ComplexValue.builder().floatProp(null).build());

        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("floatProp == null")
                .build(), 0, 1000);

        assertThat(actual.total(), is(1L));
        assertThat(actual.get(0).id(), is(entity.id()));
    }

    @Test
    public void givenFilterOnStringProperty__whenIsNotNull__thenSelectedValueReturned() throws Exception {
        Entity<ComplexValue> entity = this.repository.create(ComplexValue.builder().stringProp(null).build());

        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp != null")
                .build(), 0, 1000);

        assertThat(actual.total(), is(100L));
        assertThat(
                actual.stream().map(complexValueEntity -> complexValueEntity.id()).toArray(),
                not(hasItemInArray(entity.id()))
        );
    }

    @Test
    public void givenFilterOnIntegerProperty__whenIsNotNull__thenSelectedValueReturned() throws Exception {
        Entity<ComplexValue> entity = this.repository.create(ComplexValue.builder().integerProp(null).build());

        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("integerProp != null")
                .build(), 0, 1000);

        assertThat(actual.total(), is(100L));
        assertThat(
                actual.stream().map(complexValueEntity -> complexValueEntity.id()).toArray(),
                not(hasItemInArray(entity.id()))
        );
    }

    @Test
    public void givenFilterOnLongProperty__whenIsNotNull__thenSelectedValueReturned() throws Exception {
        Entity<ComplexValue> entity = this.repository.create(ComplexValue.builder().longProp(null).build());

        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("longProp != null")
                .build(), 0, 1000);

        assertThat(actual.total(), is(100L));
        assertThat(
                actual.stream().map(complexValueEntity -> complexValueEntity.id()).toArray(),
                not(hasItemInArray(entity.id()))
        );
    }

    @Test
    public void givenFilterOnFloatProperty__whenIsNotNull__thenSelectedValueReturned() throws Exception {
        Entity<ComplexValue> entity = this.repository.create(ComplexValue.builder().floatProp(null).build());

        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("floatProp != null")
                .build(), 0, 1000);

        assertThat(actual.total(), is(100L));
        assertThat(
                actual.stream().map(complexValueEntity -> complexValueEntity.id()).toArray(),
                not(hasItemInArray(entity.id()))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenGraterThan__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp > '097'")
                .build(), 0, 1000);

        assertThat(actual.total(), is(2L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                is(arrayContainingInAnyOrder("098", "099"))
        );
    }

    @Test
    public void givenFilterOnIntegerProperty__whenGraterThan__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("integerProp > 97")
                .build(), 0, 1000);

        assertThat(actual.total(), is(2L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.integerProp()).toArray(),
                is(arrayContainingInAnyOrder(98, 99))
        );
    }

    @Test
    public void givenFilterOnLongProperty__whenGraterThan__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("longProp > 97")
                .build(), 0, 1000);

        assertThat(actual.total(), is(2L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.longProp()).toArray(),
                is(arrayContainingInAnyOrder(98L, 99L))
        );
    }

    @Test
    public void givenFilterOnFloatProperty__whenGraterThan__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("floatProp > 97.2")
                .build(), 0, 1000);

        assertThat(actual.total(), is(2L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.floatProp()).toArray(),
                is(arrayContainingInAnyOrder(98.2f, 99.2f))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenGraterThanProperty__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp > nested.nestedProp")
                .build(), 0, 1000);

        assertThat(actual.total(), is(49L));
        for (Entity<ComplexValue> entity : actual) {
            assertThat(entity.value().toString(), entity.value().stringProp(), is(greaterThan(entity.value().nested().nestedProp())));
        }
    }

    @Test
    public void givenFilterOnStringProperty__whenGraterThanOrEquals__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp >= '097'")
                .build(), 0, 1000);

        assertThat(actual.total(), is(3L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                is(arrayContainingInAnyOrder("097", "098", "099"))
        );
    }

    @Test
    public void givenFilterOnIntegerProperty__whenGraterThanOrEquals__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("integerProp >= 97")
                .build(), 0, 1000);

        assertThat(actual.total(), is(3L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.integerProp()).toArray(),
                is(arrayContainingInAnyOrder(97, 98, 99))
        );
    }

    @Test
    public void givenFilterOnLongProperty__whenGraterThanOrEquals__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("longProp >= 97")
                .build(), 0, 1000);

        assertThat(actual.total(), is(3L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.longProp()).toArray(),
                is(arrayContainingInAnyOrder(97L, 98L, 99L))
        );
    }

    @Test
    public void givenFilterOnFloatProperty__whenGraterThanOrEquals__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("floatProp >= 97.2")
                .build(), 0, 1000);

        assertThat(actual.total(), is(3L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.floatProp()).toArray(),
                is(arrayContainingInAnyOrder(97.2f, 98.2f, 99.2f))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenGraterThanOrEqualProperty__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp >= nested.nestedProp")
                .build(), 0, 1000);

        assertThat(actual.total(), is(50L));
        for (Entity<ComplexValue> entity : actual) {
            assertThat(entity.value().toString(), entity.value().stringProp(), is(greaterThanOrEqualTo(entity.value().nested().nestedProp())));
        }
    }

    @Test
    public void givenFilterOnStringProperty__whenLowerThan__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp < '002'")
                .build(), 0, 1000);

        assertThat(actual.total(), is(2L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                is(arrayContainingInAnyOrder("000", "001"))
        );
    }

    @Test
    public void givenFilterOnIntegerProperty__whenLowerThan__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("integerProp < 2")
                .build(), 0, 1000);

        assertThat(actual.total(), is(2L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.integerProp()).toArray(),
                is(arrayContainingInAnyOrder(0, 1))
        );
    }

    @Test
    public void givenFilterOnLongProperty__whenLowerThan__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("longProp < 2")
                .build(), 0, 1000);

        assertThat(actual.total(), is(2L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.longProp()).toArray(),
                is(arrayContainingInAnyOrder(0L, 1L))
        );
    }

    @Test
    public void givenFilterOnFloatProperty__whenLowerThan__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("floatProp < 2.2")
                .build(), 0, 1000);

        assertThat(actual.total(), is(2L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.floatProp()).toArray(),
                is(arrayContainingInAnyOrder(0.2f, 1.2f))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenLowerThanProperty__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp < nested.nestedProp")
                .build(), 0, 1000);

        assertThat(actual.total(), is(50L));
        for (Entity<ComplexValue> entity : actual) {
            assertThat(entity.value().toString(), entity.value().stringProp(), is(lessThan(entity.value().nested().nestedProp())));
        }
    }

    @Test
    public void givenFilterOnStringProperty__whenLowerThanOrEquals__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp <= '002'")
                .build(), 0, 1000);

        assertThat(actual.total(), is(3L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                is(arrayContainingInAnyOrder("000", "001", "002"))
        );
    }

    @Test
    public void givenFilterOnIntegerProperty__whenLowerThanOrEquals__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("integerProp <= 2")
                .build(), 0, 1000);

        assertThat(actual.total(), is(3L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.integerProp()).toArray(),
                is(arrayContainingInAnyOrder(0, 1, 2))
        );
    }

    @Test
    public void givenFilterOnLongProperty__whenLowerThanOrEquals__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("longProp <= 2")
                .build(), 0, 1000);

        assertThat(actual.total(), is(3L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.longProp()).toArray(),
                is(arrayContainingInAnyOrder(0L, 1L, 2L))
        );
    }

    @Test
    public void givenFilterOnFloatProperty__whenLowerThanOrEquals__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("floatProp <= 2.2")
                .build(), 0, 1000);

        assertThat(actual.total(), is(3L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.floatProp()).toArray(),
                is(arrayContainingInAnyOrder(0.2f, 1.2f, 2.2f))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenLowerThanOrEqualProperty__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp <= nested.nestedProp")
                .build(), 0, 1000);

        assertThat(actual.total(), is(51L));
        for (Entity<ComplexValue> entity : actual) {
            assertThat(entity.value().toString(), entity.value().stringProp(), is(lessThanOrEqualTo(entity.value().nested().nestedProp())));
        }
    }

    @Test
    public void givenFilterOnStringProperty__whenStartsWithProperty__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp starts with nested.deep.deepProp")
                .build(), 0, 1000);

        assertThat(actual.total(), is(10L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                is(arrayContainingInAnyOrder("040", "041", "042", "043", "044", "045", "046", "047", "048", "049"))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenStartsWith__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp starts with '01'")
                .build(), 0, 1000);

        assertThat(actual.total(), is(10L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                is(arrayContainingInAnyOrder("010", "011", "012", "013", "014", "015", "016", "017", "018", "019"))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenEndsWith__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp ends with '8'")
                .build(), 0, 1000);

        assertThat(actual.total(), is(10L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                is(arrayContainingInAnyOrder("008", "018", "028", "038", "048", "058", "068", "078", "088", "098"))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenEndsWithProperty__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp ends with nested.deep.deepProp")
                .build(), 0, 1000);

        assertThat(actual.total(), is(1L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                is(arrayContainingInAnyOrder("004"))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenContains__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp contains '08'")
                .build(), 0, 1000);

        assertThat(actual.total(), is(11L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                is(arrayContainingInAnyOrder("008", "080", "081", "082", "083", "084", "085", "086", "087", "088", "089"))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenContainsProperty__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp contains nested.deep.deepProp")
                .build(), 0, 1000);

        assertThat(actual.total(), is(11L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                is(arrayContainingInAnyOrder("004", "040", "041", "042", "043", "044", "045", "046", "047", "048", "049"))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenOr__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp == '001' || stringProp == '002'")
                .build(), 0, 1000);

        assertThat(actual.total(), is(2L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                is(arrayContainingInAnyOrder("001", "002"))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenAnd__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("stringProp == '050' && nested.nestedProp == '050'")
                .build(), 0, 1000);

        assertThat(actual.total(), is(1L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                is(arrayContainingInAnyOrder("050"))
        );
    }

    @Test
    public void givenFilterOnStringProperty__whenNot__thenSelectedValueReturned() throws Exception {
        PagedEntityList<ComplexValue> actual = this.repository.search(PropertyQuery.builder()
                .filter("! stringProp <= '097'")
                .build(), 0, 1000);

        assertThat(actual.total(), is(2L));
        assertThat(
                actual.valueList().stream().map(complexValue -> complexValue.stringProp()).toArray(),
                is(arrayContainingInAnyOrder("098", "099"))
        );
    }
}