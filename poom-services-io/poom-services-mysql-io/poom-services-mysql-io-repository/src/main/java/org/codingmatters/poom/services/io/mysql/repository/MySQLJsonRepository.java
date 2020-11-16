package org.codingmatters.poom.services.io.mysql.repository;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import org.codingmatters.poom.services.domain.exceptions.RepositoryException;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.io.mysql.repository.table.TableModel;
import org.codingmatters.poom.servives.domain.entities.Entity;
import org.codingmatters.poom.servives.domain.entities.ImmutableEntity;
import org.codingmatters.poom.servives.domain.entities.PagedEntityList;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

public class MySQLJsonRepository<V> implements Repository<V, PropertyQuery> {
    @FunctionalInterface
    public interface ValueToJson<V> {
        void write(JsonGenerator generator, V value) throws IOException;
    }
    @FunctionalInterface
    public interface ValueFromJson<V> {
        V read(JsonParser parser) throws IOException;
    }

    private final DataSource dataSource;
    private final TableModel tableModel;
    private final JsonFactory jsonFactory;
    private final ValueToJson<V> valueToJson;
    private final ValueFromJson<V> valueFromJson;

    public MySQLJsonRepository(DataSource dataSource, String tableName, JsonFactory jsonFactory, ValueToJson<V> valueToJson, ValueFromJson<V> valueFromJson) throws RepositoryException {
        this.dataSource = dataSource;
        this.tableModel = new TableModel(tableName);
        this.jsonFactory = jsonFactory;
        this.valueToJson = valueToJson;
        this.valueFromJson = valueFromJson;

        try {
            this.createOrUpdateTable();
        } catch (SQLException e) {
            throw new RepositoryException("failed creating repository table " + tableName + " with data source " + this.dataSource, e);
        }
    }

    @Override
    public Entity<V> create(V v) throws RepositoryException {
        ImmutableEntity<V> result = new ImmutableEntity<>(UUID.randomUUID().toString(), BigInteger.ONE, v);
        this.createEntity(v, result);
        return result;
    }

    @Override
    public Entity<V> createWithId(String id, V v) throws RepositoryException {
        ImmutableEntity<V> result = new ImmutableEntity<>(id, BigInteger.ONE, v);
        this.createEntity(v, result);
        return result;
    }

    @Override
    public Entity<V> createWithIdAndVersion(String id, BigInteger version, V v) throws RepositoryException {
        ImmutableEntity<V> result = new ImmutableEntity<>(id,version, v);
        this.createEntity(v, result);
        return result;
    }

    @Override
    public Entity<V> retrieve(String id) throws RepositoryException {
        try(Connection connection = this.dataSource.getConnection()) {
            PreparedStatement statement = this.tableModel.retrieveEntity(connection, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return this.entityFromResultSet(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RepositoryException("error retrieving entity from database, id : " + id, e);
        }
    }

    @Override
    public Entity<V> update(Entity<V> entity, V v) throws RepositoryException {
        Entity<V> current = this.retrieve(entity.id());
        Entity<V> result = new ImmutableEntity<>(entity.id(), current.version().add(BigInteger.ONE), v);
        try(Connection connection = this.dataSource.getConnection()) {
            PreparedStatement statement = this.tableModel.updateEntity(connection, result.id(), result.version(), this.json(v));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("failed updating entity in database for id : " + result.id(), e);
        }
        return result;
    }

    @Override
    public void delete(Entity<V> entity) throws RepositoryException {
        try(Connection connection = this.dataSource.getConnection()) {
            PreparedStatement statement = this.tableModel.deleteEntity(connection, entity.id());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("error retrieving entity from database, id : " + entity.id(), e);
        }
    }

    @Override
    public PagedEntityList<V> all(long start, long end) throws RepositoryException {
        LinkedList<Entity<V>> result = new LinkedList<>();
        try(Connection connection = this.dataSource.getConnection()) {
            ResultSet countRs = this.tableModel.countAllEntities(connection).executeQuery();
            countRs.next();
            long count = countRs.getLong("cnt");

            ResultSet rs = this.tableModel.allEntities(connection, start, end).executeQuery();
            while(rs.next()) {
                result.add(this.entityFromResultSet(rs));
            }
            return new PagedEntityList.DefaultPagedEntityList<>(start, start + result.size(), count, result);
        } catch (SQLException e) {
            throw new RepositoryException("failed listing all entities", e);
        }
    }

    @Override
    public PagedEntityList<V> search(PropertyQuery propertyQuery, long l, long l1) throws RepositoryException {
        throw new RuntimeException("NYIMPL");
    }

    @Override
    public void deleteFrom(PropertyQuery query) throws RepositoryException {
        throw new RuntimeException("NYIMPL");
    }

    private void createOrUpdateTable() throws SQLException {
        try(Connection connection = this.dataSource.getConnection()) {
            Optional<String> query = this.tableModel.upgradeTableQuery(connection);
            if(query.isPresent()) {
                connection.createStatement().execute(query.get());
            }
        }
    }

    private String json(V v) throws RepositoryException {
        String json = null;
        try {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream(); JsonGenerator generator = this.jsonFactory.createGenerator(out)) {
                this.valueToJson.write(generator, v);
                generator.flush();
                generator.close();
                json = out.toString("UTF-8");
            }
        } catch (IOException e) {
            throw new RepositoryException("failed serializing value to json : " + v, e);
        }
        return json;
    }


    private V value(String json) throws RepositoryException {
        try(JsonParser parser = this.jsonFactory.createParser(json)) {
            return this.valueFromJson.read(parser);
        } catch (IOException e) {
            throw new RepositoryException("failed reading value from json : " + json, e);
        }
    }

    private void createEntity(V v, ImmutableEntity<V> result) throws RepositoryException {
        try(Connection connection = this.dataSource.getConnection()) {
            PreparedStatement statement = this.tableModel.createEntity(connection, result.id(), result.version(), this.json(v));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("failed creating entity", e);
        }
    }

    private ImmutableEntity<V> entityFromResultSet(ResultSet rs) throws SQLException, RepositoryException {
        return new ImmutableEntity<>(
                rs.getString("id"),
                BigInteger.valueOf(rs.getLong("version")),
                this.value(rs.getString("doc"))
        );
    }
}
