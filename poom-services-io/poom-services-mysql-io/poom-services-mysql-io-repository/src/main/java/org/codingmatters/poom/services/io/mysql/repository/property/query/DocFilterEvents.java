package org.codingmatters.poom.services.io.mysql.repository.property.query;

import org.codingmatters.poom.services.domain.property.query.FilterEvents;
import org.codingmatters.poom.services.domain.property.query.events.FilterEventError;
import org.codingmatters.poom.services.io.mysql.repository.table.TableModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class DocFilterEvents implements FilterEvents {
    private final Stack<String> stack = new Stack<>();
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
    public Object startsWithProperty(String left, String right) throws FilterEventError {
        this.appendPropertySurroundedPrepicate(left, "LIKE", "", right, "%");
        return null;
    }

    @Override
    public Object endsWith(String left, Object right) throws FilterEventError {
        this.appendSimplePredicate(left, "LIKE", "%" + right);
        return null;
    }

    @Override
    public Object endsWithProperty(String left, String right) throws FilterEventError {
        this.appendPropertySurroundedPrepicate(left, "LIKE", "%", right, "");
        return null;
    }

    @Override
    public Object contains(String left, Object right) throws FilterEventError {
        this.appendSimplePredicate(left, "LIKE", "%" + right + "%");
        return null;
    }

    @Override
    public Object containsProperty(String left, String right) throws FilterEventError {
        this.appendPropertySurroundedPrepicate(left, "LIKE", "%", right, "%");
        return null;
    }

    @Override
    public Object or() throws FilterEventError {
        this.stack.push(String.format("(%s) OR (%s)", this.stack.pop(), this.stack.pop()));
        return null;
    }

    @Override
    public Object and() throws FilterEventError {
        this.stack.push(String.format("(%s) AND (%s)", this.stack.pop(), this.stack.pop()));
        return null;
    }

    @Override
    public Object not() throws FilterEventError {
        this.stack.push(String.format("NOT (%s)", this.stack.pop()));
        return null;
    }

    private void appendSimplePredicate(String propertyPath, String operator, Object value) {
        this.stack.push(String.format("JSON_VALUE(JSON_EXTRACT(doc, \"$.%s\"), \"$\") %s ?", propertyPath, operator));
        this.paramsSetters.add((statement, index) -> statement.setObject(index, value));
    }

    private void appendNullPredicate(String propertyPath, String operator) {
        this.stack.push(String.format("JSON_VALUE(JSON_EXTRACT(doc, \"$.%s\"), \"$\") %s 'null'", propertyPath, operator));
    }

    private void appendPropertySimplePredicate(String leftProperty, String operator, String rightProperty) {
        this.stack.push(String.format(
                "JSON_VALUE(JSON_EXTRACT(doc, \"$.%s\"), \"$\") %s JSON_VALUE(JSON_EXTRACT(doc, \"$.%s\"), \"$\")",
                leftProperty, operator, rightProperty));
    }

    private void appendPropertySurroundedPrepicate(String leftProperty, String operator, String prefix, String rightProperty, String postfix) {
        this.stack.push(String.format(
                "JSON_VALUE(JSON_EXTRACT(doc, \"$.%s\"), \"$\") %s CONCAT(\"%s\", JSON_VALUE(JSON_EXTRACT(doc, \"$.%s\"), \"$\"), \"%s\")",
                leftProperty, operator, prefix, rightProperty, postfix));
    }

    public TableModel.Clause clause() {
        String clause = this.stack.isEmpty() ? null : this.stack.pop();
        System.out.println(clause);
        return new TableModel.Clause(
                clause != null ? clause : null,
                this.paramsSetters.isEmpty() ? null : this.paramsSetters.toArray(new TableModel.ParamSetter[0])
        );
    }
}
