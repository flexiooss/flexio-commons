package org.codingmatters.poom.services.io.mysql.repository;

import com.fasterxml.jackson.core.JsonFactory;
import io.flexio.docker.DockerResource;
import org.codingmatters.generated.Value;
import org.codingmatters.generated.json.ValueReader;
import org.codingmatters.generated.json.ValueWriter;
import org.codingmatters.poom.servives.domain.entities.Entity;
import org.codingmatters.poom.servives.domain.entities.ImmutableEntity;
import org.codingmatters.poom.servives.domain.entities.PagedEntityList;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;


public class MySQLJsonRepositoryTest {

    @ClassRule
    static public DockerResource docker = DockerResource.client();

    @Rule
    public MariaDBResource mariaDBResource = new MariaDBResource(docker);

    private final JsonFactory jsonFactory = new JsonFactory();

    private Connection connection;

    @Before
    public void setUp() throws Exception {
        this.connection = this.mariaDBResource.ds().getConnection();
    }

    private MySQLJsonRepository<Value> createRepository(String tableName) throws Exception {
        return new MySQLJsonRepository<>(
                this.mariaDBResource.ds(),
                tableName,
                this.jsonFactory,
                (generator, value) -> new ValueWriter().write(generator, value),
                parser -> new ValueReader().read(parser)
        );
    }

    @Test
    public void givenEmptyDb__whenCreatingRepository__thenRepositoryTableIsCreated() throws Exception {
        this.createRepository("repository");

        assertThat(this.tableExists("repository"), is(true));
        assertThat(this.columns("repository"), containsInAnyOrder("id", "version", "doc"));
    }

    @Test
    public void givenTableExists__whenMissesColumn__thenTableUpgraded() throws Exception {
        this.connection.createStatement().execute("CREATE TABLE `repository` (id VARCHAR(256) PRIMARY KEY, version BIGINT)");

        this.createRepository("repository");

        assertThat(this.columns("repository"), containsInAnyOrder("id", "version", "doc"));
    }

    @Test
    public void givenEmptyDb__whenCreatingEntity__thenRepositoryTableHasOneRow_andRowPopulatedFromEntity() throws Exception {
        Entity<Value> entity = this.createRepository("repository").create(Value.builder().prop("val").build());

        assertThat(entity.version(), is(BigInteger.ONE));

        ResultSet rs = this.connection.createStatement().executeQuery("select * from repository");
        rs.next();

        assertThat(rs.getString("id"), is(entity.id()));
        assertThat(rs.getInt("version"), is(1));
        assertThat(rs.getString("doc"), is("{\"prop\":\"val\"}"));

        assertThat(rs.next(), is(false));
    }

    @Test
    public void givenEmptyDb__whenCreatingEntityWithId__thenRepositoryTableHasOneRow_andRowPopulatedFromEntity() throws Exception {
        Entity<Value> entity = this.createRepository("repository").createWithId("42", Value.builder().prop("val").build());

        assertThat(entity.id(), is("42"));
        assertThat(entity.version(), is(BigInteger.ONE));

        ResultSet rs = this.connection.createStatement().executeQuery("select * from repository");
        rs.next();

        assertThat(rs.getString("id"), is("42"));
        assertThat(rs.getInt("version"), is(1));
        assertThat(rs.getString("doc"), is("{\"prop\":\"val\"}"));

        assertThat(rs.next(), is(false));
    }

    @Test
    public void givenEmptyDb__whenCreatingEntityWithIdAndVersion__thenRepositoryTableHasOneRow_andRowPopulatedFromEntity() throws Exception {
        Entity<Value> entity = this.createRepository("repository").createWithIdAndVersion("42", BigInteger.TEN, Value.builder().prop("val").build());

        assertThat(entity.id(), is("42"));
        assertThat(entity.version(), is(BigInteger.TEN));

        ResultSet rs = this.connection.createStatement().executeQuery("select * from repository");
        rs.next();

        assertThat(rs.getString("id"), is("42"));
        assertThat(rs.getInt("version"), is(10));
        assertThat(rs.getString("doc"), is("{\"prop\":\"val\"}"));

        assertThat(rs.next(), is(false));
    }

    @Test
    public void givenTableHasToValues__whenRetrieveExisting__thenRetrivedBuildedFromRow() throws Exception {
        MySQLJsonRepository<Value> repository = this.createRepository("repository");

        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO `repository` (id, version, doc) VALUES " +
                "(?, ?, ?), " +
                "(?, ?, ?)");

        statement.setString(1, "42");
        statement.setInt(2, 2);
        statement.setString(3, "{\"prop\":\"v1\"}");

        statement.setString(4, "12");
        statement.setInt(5, 20);
        statement.setString(6, "{\"prop\":\"v2\"}");

        assertThat(statement.executeUpdate(), is(2));

        assertThat(repository.retrieve("12"), is(new ImmutableEntity<>("12", BigInteger.valueOf(20), Value.builder().prop("v2").build())));
    }

    @Test
    public void givenTableHasAValue__whenUpdatingValue__thenTableRowUpdated() throws Exception {
        MySQLJsonRepository<Value> repository = this.createRepository("repository");

        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO `repository` (id, version, doc) VALUES " +
                "(?, ?, ?)");

        statement.setString(1, "12");
        statement.setInt(2, 20);
        statement.setString(3, "{\"prop\":\"v2\"}");

        assertThat(statement.executeUpdate(), is(1));

        Entity<Value> entity = repository.update(new ImmutableEntity<>("12", null, null), Value.builder().prop("updated").build());

        assertThat(entity, is(new ImmutableEntity<>("12", BigInteger.valueOf(21), Value.builder().prop("updated").build())));

        ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM `repository`");
        rs.next();
        assertThat(rs.getString("id"), is("12"));
        assertThat(rs.getLong("version"), is(21L));
        assertThat(rs.getString("doc"), is("{\"prop\":\"updated\"}"));
    }

    @Test
    public void givenTableHasAValue__whenDeletingValue__thenTableEmpty() throws Exception {
        MySQLJsonRepository<Value> repository = this.createRepository("repository");

        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO `repository` (id, version, doc) VALUES (?, ?, ?)");

        statement.setString(1, "12");
        statement.setInt(2, 20);
        statement.setString(3, "{\"prop\":\"v2\"}");

        assertThat(statement.executeUpdate(), is(1));

        repository.delete(new ImmutableEntity<>("12", null, null));

        ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM `repository`");
        assertThat(rs.next(), is(false));
    }

    @Test
    public void givenSomeRowsInTable__whenListAll_andPageContainsAll__thenAllEntitiesReturned() throws Exception {
        MySQLJsonRepository<Value> repository = this.createRepository("repository");

        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO `repository` (id, version, doc) VALUES (?, ?, ?)");
        for (int i = 0; i < 100; i++) {
            statement.setString(1, String.format("%03d", i));
            statement.setLong(2, i);
            statement.setString(3, String.format("{\"prop\":\"%s\"}", i));
            statement.execute();
        }

        PagedEntityList<Value> actual = repository.all(0L, 200L);
        assertThat(actual.total(), is(100L));
        assertThat(actual.size(), is(100));
        for (int i = 0; i < 100; i++) {
            assertThat("" + i, actual.get(i).version(), is(BigInteger.valueOf(i)));
            assertThat("" + i, actual.get(i).value(), is(Value.builder().prop("" + i).build()));
        }
    }

    @Test
    public void givenSomeRowsInTable__whenListAll_andPageExactlyAll__thenAllEntitiesReturned() throws Exception {
        MySQLJsonRepository<Value> repository = this.createRepository("repository");

        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO `repository` (id, version, doc) VALUES (?, ?, ?)");
        for (int i = 0; i < 100; i++) {
            statement.setString(1, String.format("%03d", i));
            statement.setLong(2, i);
            statement.setString(3, String.format("{\"prop\":\"%s\"}", i));
            statement.execute();
        }

        PagedEntityList<Value> actual = repository.all(0L, 99L);
        assertThat(actual.total(), is(100L));
        assertThat(actual.size(), is(100));
        for (int i = 0; i < 100; i++) {
            assertThat("" + i, actual.get(i).version(), is(BigInteger.valueOf(i)));
            assertThat("" + i, actual.get(i).value(), is(Value.builder().prop("" + i).build()));
        }
    }

    @Test
    public void givenSomeRowsInTable__whenListAll_andInnerPage__thenAllEntitiesReturned() throws Exception {
        MySQLJsonRepository<Value> repository = this.createRepository("repository");

        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO `repository` (id, version, doc) VALUES (?, ?, ?)");
        for (int i = 0; i < 100; i++) {
            statement.setString(1, String.format("%03d", i));
            statement.setLong(2, i);
            statement.setString(3, String.format("{\"prop\":\"%s\"}", i));
            statement.execute();
        }

        PagedEntityList<Value> actual = repository.all(10L, 19L);
        assertThat(actual.total(), is(100L));
        assertThat(actual.size(), is(10));
        for (int i = 10; i < 20; i++) {
            assertThat("" + i, actual.get(i - 10).version(), is(BigInteger.valueOf(i)));
            assertThat("" + i, actual.get(i - 10).value(), is(Value.builder().prop("" + i).build()));
        }
    }

    @Test
    public void givenSomeRowsInTable__whenListAll_andOuterPage__thenAllEntitiesReturned() throws Exception {
        MySQLJsonRepository<Value> repository = this.createRepository("repository");

        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO `repository` (id, version, doc) VALUES (?, ?, ?)");
        for (int i = 0; i < 100; i++) {
            statement.setString(1, String.format("%03d", i));
            statement.setLong(2, i);
            statement.setString(3, String.format("{\"prop\":\"%s\"}", i));
            statement.execute();
        }

        PagedEntityList<Value> actual = repository.all(100L, 109L);
        assertThat(actual.size(), is(0));
        assertThat(actual.total(), is(100L));
    }

    @Test
    public void givenSomeRowsInTable__whenListAll_andOverlappingPage__thenAllEntitiesReturned() throws Exception {
        MySQLJsonRepository<Value> repository = this.createRepository("repository");

        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO `repository` (id, version, doc) VALUES (?, ?, ?)");
        for (int i = 0; i < 100; i++) {
            statement.setString(1, String.format("%03d", i));
            statement.setLong(2, i);
            statement.setString(3, String.format("{\"prop\":\"%s\"}", i));
            statement.execute();
        }

        PagedEntityList<Value> actual = repository.all(95L, 104L);
        assertThat(actual.size(), is(5));
        assertThat(actual.total(), is(100L));
        for (int i = 95; i < 100; i++) {
            assertThat("" + i, actual.get(i - 95).version(), is(BigInteger.valueOf(i)));
            assertThat("" + i, actual.get(i - 95).value(), is(Value.builder().prop("" + i).build()));
        }
    }

    private boolean tableExists(String tableName) throws SQLException {
        return this.connection.getMetaData().getTables(null, null, tableName, new String[]{"TABLE"}).next();
    }

    private Set<String> columns(String tableName) throws SQLException {
        HashSet<String> result = new HashSet<>();
        ResultSet columns = this.connection.getMetaData().getColumns(null, null, "repository", "%");
        while (columns.next()) {
            result.add(columns.getString("COLUMN_NAME"));
        }
        return result;
    }
}