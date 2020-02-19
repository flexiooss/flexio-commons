package io.flexio.io.mongo.repository.property.query;

import org.bson.conversions.Bson;
import org.codingmatters.poom.services.domain.property.query.FilterEvents;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.property.query.PropertyQueryParser;
import org.codingmatters.poom.services.domain.property.query.SortEvents;
import org.codingmatters.poom.services.domain.property.query.events.FilterEventException;
import org.codingmatters.poom.services.domain.property.query.events.SortEventException;
import org.codingmatters.poom.services.domain.property.query.validation.InvalidPropertyException;
import org.codingmatters.poom.services.logging.CategorizedLogger;

import java.util.function.Function;

public class PropertyQuerier {
    static private final CategorizedLogger log = CategorizedLogger.getLogger(PropertyQuerier.class);


    public PropertyQuerier() {
    }

    public Function<PropertyQuery, Bson> filterer() {
        return this::filter;
    }

    private Bson filter(PropertyQuery query) {
        BsonFilterEvents events = new BsonFilterEvents();
        try {
            PropertyQueryParser.builder().build(events, SortEvents.noop()).parse(query);
            Bson filter = events.filter();
            return filter;
        } catch (InvalidPropertyException | FilterEventException | SortEventException e) {
            log.warn("property query filter parsing failed for mongo repo, should throw exception, returning un filtered", e);
            return null;
        }
    }

    public Function<PropertyQuery, Bson> sorter() {
        return this::sort;
    }

    private Bson sort(PropertyQuery query) {
        BsonSortEvents events = new BsonSortEvents();
        try {
            PropertyQueryParser.builder().build(FilterEvents.noop(), events).parse(query);
            return events.sort();
        } catch (InvalidPropertyException | FilterEventException | SortEventException e) {
            log.warn("property query filter parsing failed for mongo repo, should throw exception, returning un filtered", e);
            return null;
        }
    }

}
