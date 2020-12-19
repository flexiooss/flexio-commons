package org.codingmatters.poom.services.io.mysql.repository.property.query;

import org.codingmatters.poom.services.domain.exceptions.RepositoryException;
import org.codingmatters.poom.services.domain.property.query.FilterEvents;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.property.query.PropertyQueryParser;
import org.codingmatters.poom.services.domain.property.query.SortEvents;
import org.codingmatters.poom.services.domain.property.query.events.FilterEventException;
import org.codingmatters.poom.services.domain.property.query.events.SortEventException;
import org.codingmatters.poom.services.domain.property.query.validation.InvalidPropertyException;
import org.codingmatters.poom.services.io.mysql.repository.MySQLJsonRepository;
import org.codingmatters.poom.services.io.mysql.repository.table.TableModel;

public class PropertyQueryToDocQueryParser implements MySQLJsonRepository.QueryParser<PropertyQuery> {
    @Override
    public TableModel.Clause whereClause(PropertyQuery query) throws RepositoryException {
        DocFilterEvents filterEvents = new DocFilterEvents();
        DocSortEvents sortEvents = new DocSortEvents();
        try {
            PropertyQueryParser.builder().build(filterEvents, sortEvents).parse(query);
            return sortEvents.withOrderBy(filterEvents.clause());
        } catch (InvalidPropertyException e) {
            e.printStackTrace();
        } catch (FilterEventException e) {
            e.printStackTrace();
        } catch (SortEventException e) {
            e.printStackTrace();
        }
        return null;
    }

}
