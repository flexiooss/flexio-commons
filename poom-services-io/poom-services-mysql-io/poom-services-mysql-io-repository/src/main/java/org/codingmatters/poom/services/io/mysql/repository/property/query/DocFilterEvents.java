package org.codingmatters.poom.services.io.mysql.repository.property.query;

import org.codingmatters.poom.services.domain.property.query.FilterEvents;
import org.codingmatters.poom.services.domain.property.query.events.FilterEventError;
import org.codingmatters.poom.services.io.mysql.repository.table.TableModel;

public class DocFilterEvents implements FilterEvents {
    StringBuilder clauseBuilder = new StringBuilder();

    @Override
    public Object isEquals(String left, Object right) throws FilterEventError {
        this.clauseBuilder.append(String.format("JSON_EXTRACT(doc, \"$.%s\") = ?", left));
        return null;
    }

    public TableModel.Clause clause() {
        String clause = this.clauseBuilder.toString();
        return new TableModel.Clause(clause != null ? clause : null);
    }
}
