package org.codingmatters.poom.services.io.mysql.repository;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.docker.DockerResource;
import org.codingmatters.generated.QAValue;
import org.codingmatters.generated.json.QAValueReader;
import org.codingmatters.generated.json.QAValueWriter;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.domain.repositories.RepositoryBaseAcceptanceTest;
import org.junit.Before;
import org.junit.ClassRule;

import java.sql.Connection;

public class MySQLJsonRepositoryBaseAcceptanceTest extends RepositoryBaseAcceptanceTest {

    @ClassRule(order = 0)
    static public DockerResource docker = DockerResource.client();

    @ClassRule(order = 1)
    static public MariaDBResource mariaDBResource = new MariaDBResource(docker);

    private final JsonFactory jsonFactory = new JsonFactory();
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        mariaDBResource.wipe();
        this.connection = this.mariaDBResource.ds().getConnection();
        super.setUp();
    }

    @Override
    protected Repository<QAValue, Void> createRepository() throws Exception {
        return new MySQLJsonRepository<>(
                this.mariaDBResource.ds(),
                "repository",
                this.jsonFactory,
                (generator, value) -> new QAValueWriter().write(generator, value),
                parser -> new QAValueReader().read(parser)
        );
    }
}
