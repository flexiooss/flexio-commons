package org.codingmatters.poom.services.io.mysql.repository.property.query;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.docker.DockerResource;
import org.codingmatters.generated.QAValue;
import org.codingmatters.generated.json.QAValueReader;
import org.codingmatters.generated.json.QAValueWriter;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.property.query.PropertyQueryAcceptanceTest;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.io.mysql.repository.MariaDBResource;
import org.codingmatters.poom.services.io.mysql.repository.MySQLJsonRepository;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;


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

}
