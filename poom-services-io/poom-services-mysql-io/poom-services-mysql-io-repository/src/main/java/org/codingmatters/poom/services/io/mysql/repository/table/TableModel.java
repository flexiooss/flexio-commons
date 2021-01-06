package org.codingmatters.poom.services.io.mysql.repository.table;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TableModel {
    static private Set<String> COLUMNS = new HashSet<>(Arrays.asList("id", "version", "doc"));

    private final String tableName;

    public TableModel(String tableName) {
        this.tableName = tableName;
    }

    public Optional<String> upgradeTableQuery(Connection connection) throws SQLException {
        ResultSet tableMetas = connection.getMetaData().getTables(null, null, this.tableName, new String[]{"TABLE"});
        if(tableMetas.next()) {
            ResultSet columnMetas = connection.getMetaData().getColumns(null, null, this.tableName, "%");
            HashSet<String> missingColumns = new HashSet<>(COLUMNS);
            while(columnMetas.next()) {
                missingColumns.remove(columnMetas.getString("COLUMN_NAME"));
            }

            if (missingColumns.isEmpty()) {
                return Optional.empty();
            } else {
                StringBuilder result = new StringBuilder(String.format("ALTER TABLE `%s`", this.tableName));
                boolean start = true;
                if(missingColumns.contains("id")) {
                    result.append(start ? "" : ",").append(" ADD COLUMN id VARCHAR(256) PRIMARY KEY");
                    start = false;
                }
                if(missingColumns.contains("version")) {
                    result.append(start ? "" : ",").append(" ADD COLUMN version BIGINT");
                    start = false;
                }
                if(missingColumns.contains("doc")) {
                    result.append(start ? "" : ",").append(" ADD COLUMN doc JSON");
                    start = false;
                }
                return Optional.of(result.toString());
            }
        } else {
            return Optional.of(
                    String.format("CREATE TABLE `%s` (" +
                            "id VARCHAR(256) PRIMARY KEY, version BIGINT, doc JSON" +
                            ")", this.tableName)
            );
        }
    }

    public PreparedStatement createEntity(Connection connection, String id, BigInteger version, String json) throws SQLException {
        PreparedStatement result = connection.prepareStatement(String.format("INSERT INTO `%s` (id, version, doc) VALUES (?, ?, ?)", this.tableName));
        result.setString(1, id);
        result.setObject(2, version);
        result.setString(3, json);
        return result;
    }

    public PreparedStatement retrieveEntity(Connection connection, String id) throws SQLException {
        PreparedStatement result = connection.prepareStatement(String.format("SELECT id, version, doc FROM `%s` WHERE id = ?", this.tableName));
        result.setString(1, id);
        return result;
    }

    public PreparedStatement updateEntity(Connection connection, String id, BigInteger version, String json) throws SQLException {
        PreparedStatement result = connection.prepareStatement(String.format("UPDATE `%s` SET version = ?, doc = ? WHERE id = ?", this.tableName));
        result.setObject(1, version);
        result.setString(2, json);
        result.setString(3, id);
        return result;
    }

    public PreparedStatement deleteEntity(Connection connection, String id) throws SQLException {
        PreparedStatement result = connection.prepareStatement(String.format("DELETE FROM `%s` WHERE id = ?", this.tableName));
        result.setString(1, id);
        return result;
    }

    public PreparedStatement deleteEntityFrom(Connection connection, Clause clause) throws SQLException {
        PreparedStatement result = connection.prepareStatement(String.format("DELETE FROM `%s` %s",
                this.tableName,
                clause != null && clause.clause() != null && ! clause.clause().isEmpty() ? "WHERE " + clause.clause() : ""
        ));

        int paramIndex = 0;

        if(clause != null) {
            for (ParamSetter setter : clause.setters()) {
                paramIndex++;
                setter.set(result, paramIndex);
            }
        }
        return result;
    }

    public PreparedStatement entitiesWithWhereClause(Connection connection, TableModel.Clause clause, long start, long end) throws SQLException {
        String statement = String.format("SELECT id, version, doc FROM `%s`%s %s LIMIT ? OFFSET ?",
                this.tableName,
                clause != null && clause.clause() != null && !clause.clause().isEmpty() ? "WHERE " + clause.clause() : "",
                clause != null && clause.orderBy() != null && !clause.orderBy.isEmpty() ? "ORDER BY " + clause.orderBy() : ""
        );
        PreparedStatement result = connection.prepareStatement(statement);
        int paramIndex = 0;

        if(clause != null) {
            for (ParamSetter setter : clause.setters()) {
                paramIndex++;
                setter.set(result, paramIndex);
            }
        }

        paramIndex++;
        result.setLong(paramIndex, end - start + 1);

        paramIndex++;
        result.setLong(paramIndex, start);
        return result;
    }

    public PreparedStatement countEntitiesWithWhereClause(Connection connection, TableModel.Clause clause) throws SQLException {
        PreparedStatement result = connection.prepareStatement(String.format("SELECT count(*) as cnt FROM `%s`%s",
                this.tableName,
                clause != null && clause.clause() != null && !clause.clause().isEmpty() ? "WHERE " + clause.clause() : ""
        ));

        if(clause != null) {
            int paramIndex = 0;
            for (ParamSetter setter : clause.setters()) {
                paramIndex++;
                setter.set(result, paramIndex);
            }
        }

        return result;
    }

    static public class Clause {
        private final String clause;
        private final String orderBy;
        private final ParamSetter[] setters;

        public Clause(String clause) {
            this(clause, null, null);
        }

        public Clause(String clause, ParamSetter ... setters) {
            this(clause, null, setters);
        }

        private Clause(String clause, String orderBy, ParamSetter ... setters) {
            this.clause = clause;
            this.setters = setters == null ? new ParamSetter[0] : setters;
            this.orderBy = orderBy;
        }

        public String clause() {
            return clause;
        }

        public ParamSetter[] setters() {
            return setters;
        }

        public String orderBy() {
            return orderBy;
        }

        public Clause withOrderBy(String orderBy) {
            return new Clause(this.clause, orderBy, this.setters);
        }
    }

    @FunctionalInterface
    public interface ParamSetter {
        void set(PreparedStatement statement, int index) throws SQLException;
    }
}
