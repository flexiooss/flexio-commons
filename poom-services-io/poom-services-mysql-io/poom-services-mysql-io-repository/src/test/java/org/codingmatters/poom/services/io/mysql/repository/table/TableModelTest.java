package org.codingmatters.poom.services.io.mysql.repository.table;

import io.flexio.docker.DockerResource;
import org.codingmatters.poom.services.io.mysql.repository.MariaDBResource;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigInteger;
import java.sql.Connection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class TableModelTest {

    @ClassRule
    static public DockerResource docker = DockerResource.client();

    @Rule
    public MariaDBResource mariaDBResource = new MariaDBResource(docker);

    private Connection connection;

    @Before
    public void setUp() throws Exception {
        this.connection = this.mariaDBResource.ds().getConnection();
    }

    @Test
    public void whenEmptyDb__thenUpgradeQueryIsCreateTable() throws Exception {
        assertThat(
                new TableModel("repository").upgradeTableQuery(this.connection).get(),
                is("CREATE TABLE `repository` (id VARCHAR(256) PRIMARY KEY, version BIGINT, doc JSON)")
        );
    }

    @Test
    public void givenTableExists__whenExactlyTableModel__thenNoUpgradeQuery() throws Exception {
        this.connection.createStatement().execute("CREATE TABLE `repository` (id VARCHAR(256) PRIMARY KEY, version BIGINT, doc JSON)");

        assertThat(
                new TableModel("repository").upgradeTableQuery(this.connection).isPresent(),
                is(false)
        );
    }

    @Test
    public void givenTableExists__whenMissingVersion__thenUpgradeQueryIsAlterTable_andAddVersion() throws Exception {
        this.connection.createStatement().execute("CREATE TABLE `repository` (id VARCHAR(256) PRIMARY KEY, doc JSON)");

        assertThat(
                new TableModel("repository").upgradeTableQuery(this.connection).get(),
                is("ALTER TABLE `repository` ADD COLUMN version BIGINT")
        );
    }

    @Test
    public void givenTableExists__whenMissingDoc__thenUpgradeQueryIsAlterTable_andAddDoc() throws Exception {
        this.connection.createStatement().execute("CREATE TABLE `repository` (id VARCHAR(256) PRIMARY KEY, version BIGINT)");

        assertThat(
                new TableModel("repository").upgradeTableQuery(this.connection).get(),
                is("ALTER TABLE `repository` ADD COLUMN doc JSON")
        );
    }
}