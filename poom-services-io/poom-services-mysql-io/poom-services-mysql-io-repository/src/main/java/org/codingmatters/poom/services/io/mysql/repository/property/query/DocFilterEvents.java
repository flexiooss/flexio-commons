package org.codingmatters.poom.services.io.mysql.repository.property.query;

import org.codingmatters.poom.services.domain.property.query.FilterEvents;
import org.codingmatters.poom.services.domain.property.query.events.FilterEventError;
import org.codingmatters.poom.services.io.mysql.repository.table.TableModel;

import java.util.LinkedList;
import java.util.List;

public class DocFilterEvents implements FilterEvents {
    private final StringBuilder clauseBuilder = new StringBuilder();
    private final List<TableModel.ParamSetter> paramsSetters = new LinkedList<>();

    @Override
    public Object isEquals(String left, Object right) throws FilterEventError {
        this.appendSimplePredicate(left, "=", right);
        return null;
    }

    @Override
    public Object isEqualsProperty(String left, String right) throws FilterEventError {
        this.appendPropertySimplePredicate(left, "=", right);
        return null;
    }

    @Override
    public Object isNotEquals(String left, Object right) throws FilterEventError {
        this.appendSimplePredicate(left, "!=", right);
        return null;
    }

    @Override
    public Object isNotEqualsProperty(String left, String right) throws FilterEventError {
        this.appendPropertySimplePredicate(left, "!=", right);
        return null;
    }

    @Override
    public Object isNull(String property) throws FilterEventError {
        this.appendNullPredicate(property, "=");
        return null;
    }

    @Override
    public Object isNotNull(String property) throws FilterEventError {
        this.appendNullPredicate(property, "!=");
        return null;
    }

    @Override
    public Object graterThan(String left, Object right) throws FilterEventError {
        this.appendSimplePredicate(left, ">", right);
        return null;
    }

    @Override
    public Object graterThanProperty(String left, String right) throws FilterEventError {
        this.appendPropertySimplePredicate(left, ">", right);
        return null;
    }

    @Override
    public Object graterThanOrEquals(String left, Object right) throws FilterEventError {
        this.appendSimplePredicate(left, ">=", right);
        return null;
    }

    @Override
    public Object graterThanOrEqualsProperty(String left, String right) throws FilterEventError {
        this.appendPropertySimplePredicate(left, ">=", right);
        return null;
    }

    @Override
    public Object lowerThan(String left, Object right) throws FilterEventError {
        this.appendSimplePredicate(left, "<", right);
        return null;
    }

    @Override
    public Object lowerThanProperty(String left, String right) throws FilterEventError {
        this.appendPropertySimplePredicate(left, "<", right);
        return null;
    }

    @Override
    public Object lowerThanOrEquals(String left, Object right) throws FilterEventError {
        this.appendSimplePredicate(left, "<=", right);
        return null;
    }

    @Override
    public Object lowerThanOrEqualsProperty(String left, String right) throws FilterEventError {
        this.appendPropertySimplePredicate(left, "<=", right);
        return null;
    }

    @Override
    public Object startsWith(String left, Object right) throws FilterEventError {
        this.appendSimplePredicate(left, "LIKE", right + "%");
        return null;
    }

    @Override
    public Object endsWith(String left, Object right) throws FilterEventError {
        this.appendSimplePredicate(left, "LIKE", "%" + right);
        return null;
    }

    @Override
    public Object contains(String left, Object right) throws FilterEventError {
        this.appendSimplePredicate(left, "LIKE", "%" + right + "%");
        return null;
    }

    private void appendSimplePredicate(String propertyPath, String operator, Object value) {
        this.clauseBuilder.append(String.format("JSON_VALUE(JSON_EXTRACT(doc, \"$.%s\"), \"$\") %s ?", propertyPath, operator));
        this.paramsSetters.add((statement, index) -> statement.setObject(index, value));
    }

    private void appendNullPredicate(String propertyPath, String operator) {
        this.clauseBuilder.append(String.format("JSON_VALUE(JSON_EXTRACT(doc, \"$.%s\"), \"$\") %s 'null'", propertyPath, operator));
    }

    private void appendPropertySimplePredicate(String leftProperty, String operator, String rightProperty) {
        this.clauseBuilder.append(String.format(
                "JSON_VALUE(JSON_EXTRACT(doc, \"$.%s\"), \"$\") %s JSON_VALUE(JSON_EXTRACT(doc, \"$.%s\"), \"$\")",
                leftProperty, operator, rightProperty));
    }

    public TableModel.Clause clause() {
        String clause = this.clauseBuilder.toString();
        System.out.println(clause);
        return new TableModel.Clause(
                clause != null ? clause : null,
                this.paramsSetters.isEmpty() ? null : this.paramsSetters.toArray(new TableModel.ParamSetter[0])
        );
    }
}
