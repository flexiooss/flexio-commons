package org.codingmatters.poom.services.io.mysql.repository.property.query;

import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.io.mysql.repository.table.TableModel;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class PropertyQueryToDocQueryParserTest {

    @Test
    public void givenIsEqualClause__whenGettingClause__thenSingleExtractEqualsClause() throws Exception {
        TableModel.Clause actual = new PropertyQueryToDocQueryParser().whereClause(PropertyQuery.builder()
                .filter("toto == 'tutu'")
                .build());

        assertThat(actual.clause(), is("JSON_EXTRACT(doc, \"$.toto\") = ?"));
    }

}