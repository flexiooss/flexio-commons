package org.codingmatters.poom.services.io.mysql.repository;

import io.flexio.docker.DockerResource;
import io.flexio.docker.api.types.ContainerCreationData;
import org.junit.rules.ExternalResource;
import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class MariaDBResource extends ExternalResource {
    private static final String CONTAINER_NAME = "mariadb-ut";

    public static final String MYSQL_ROOT_PASSWORD = "rootpass";
    public static final String MYSQL_DATABASE = "testdb";
    public static final String MYSQL_USER = "testuser";
    public static final String MYSQL_PASSWORD = "testpass";

    private final DockerResource docker;

    private boolean inited = false;
    private DataSource ds;

    public MariaDBResource(DockerResource docker) {
        this.docker = docker;

        this.docker.with(CONTAINER_NAME, ContainerCreationData.builder()
                .image("harbor.ci.flexio.io/ci/mariadb:10")
                .env(
                        "MYSQL_ROOT_PASSWORD=" + MYSQL_ROOT_PASSWORD,
                        "MYSQL_DATABASE=" + MYSQL_DATABASE,
                        "MYSQL_USER=" + MYSQL_USER,
                        "MYSQL_PASSWORD=" + MYSQL_PASSWORD
                )
                .build()).started().finallyStopped();
    }

    public DataSource ds() throws Exception {
        if(!this.inited) {
            this.initialize();
        }
        return this.ds;
    }

    private void initialize() throws Exception {
        this.ds = new MariaDbDataSource(
                String.format("jdbc:mariadb://%s:3306/%s?user=%s&password=%s",
                        docker.dockerClient().containerForName("mariadb-ut").get().networkSettings().iPAddress(),
                        MYSQL_DATABASE, MYSQL_USER, MYSQL_PASSWORD
                )
        );
        this.awaitServerReady();

        Connection connection = this.ds.getConnection();

        ResultSet tables = connection.getMetaData().getTables(MYSQL_DATABASE, "%", "%", new String[]{"TABLE"});
        while(tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            connection.createStatement().execute("DROP TABLE " + tableName);
        }
        this.inited = true;
    }

    private void awaitServerReady() {
        Connection result = null;
        do {
            try {
                result = this.ds.getConnection();
            } catch (SQLException e) {}
        } while (result == null);
    }

    @Override
    protected void before() throws Throwable {
        super.before();
        this.inited = false;
    }

    @Override
    protected void after() {
        this.inited = false;
        super.after();
    }
}
