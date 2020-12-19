package org.codingmatters.poom.services.io.mysql.repository.property.query;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.docker.DockerResource;
import org.codingmatters.generated.ComplexValue;
import org.codingmatters.generated.complexvalue.Nested;
import org.codingmatters.generated.complexvalue.nested.Deep;
import org.codingmatters.generated.json.ComplexValueReader;
import org.codingmatters.generated.json.ComplexValueWriter;
import org.codingmatters.poom.services.domain.exceptions.RepositoryException;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.property.query.PropertyQueryAcceptanceTest;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.io.mysql.repository.MariaDBResource;
import org.codingmatters.poom.services.io.mysql.repository.MySQLJsonRepository;
import org.codingmatters.poom.servives.domain.entities.Entity;
import org.codingmatters.poom.servives.domain.entities.PagedEntityList;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import java.time.*;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MySQLJsonRepositoryWithPropertyQueryTest extends PropertyQueryAcceptanceTest {

    @ClassRule
    static public DockerResource docker = DockerResource.client();

    @Rule
    public MariaDBResource mariaDBResource = new MariaDBResource(docker);

    private final JsonFactory jsonFactory = new JsonFactory();


    @Override
    protected Repository<ComplexValue, PropertyQuery> createRepository() {
        try {
            return new MySQLJsonRepository<ComplexValue, PropertyQuery>(
                    this.mariaDBResource.ds(),
                    "repository",
                    this.jsonFactory,
                    (generator, value) -> new ComplexValueWriter().write(generator, value),
                    parser -> new ComplexValueReader().read(parser),
                    new PropertyQueryToDocQueryParser()
            );
        } catch (Exception e) {
            throw new AssertionError("error creating repository", e);
        }
    }

}
