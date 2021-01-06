package org.codingmatters.poom.services.io.mysql.repository.property.query;

import org.codingmatters.poom.services.domain.property.query.SortEvents;
import org.codingmatters.poom.services.domain.property.query.events.SortEventError;
import org.codingmatters.poom.services.io.mysql.repository.table.TableModel;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DocSortEvents implements SortEvents {

    private final List<String> orders = new LinkedList<>();

    @Override
    public Object sorted(String property, Direction direction) throws SortEventError {
        this.orders.add(String.format("JSON_VALUE(JSON_EXTRACT(doc, \"$.%s\"), \"$\") %s", property, direction.name()));
        return null;
    }

    public TableModel.Clause withOrderBy(TableModel.Clause clause) {
        if(this.orders.isEmpty()) {
            return clause;
        } else {
            return clause.withOrderBy(this.orders.stream().collect(Collectors.joining(", ")));
        }
    }
}
