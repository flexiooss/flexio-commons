package io.flexio.io.mongo.repository.property.query;

import io.flexio.io.mongo.repository.BsonFromQueryProvider;
import io.flexio.io.mongo.repository.property.query.config.MongoFilterConfig;
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

    private final MongoFilterConfig filterConfig;

    public PropertyQuerier() {
        this(MongoFilterConfig.builder()
                .potentialOids("_id")
                .build());
    }
    public PropertyQuerier(MongoFilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public BsonFromQueryProvider<PropertyQuery> filterer() {
        return this::filter;
    }

    private Bson filter(PropertyQuery query) throws Exception {
        BsonFilterEvents events = new BsonFilterEvents(this.filterConfig);
        PropertyQueryParser.builder().build(events, SortEvents.noop()).parse(query);
        Bson filter = events.filter();
        return filter;
    }

    public BsonFromQueryProvider<PropertyQuery> sorter() {
        return this::sort;
    }

    private Bson sort(PropertyQuery query) throws Exception {
        BsonSortEvents events = new BsonSortEvents();
        PropertyQueryParser.builder().build(FilterEvents.noop(), events).parse(query);
        return events.sort();
    }

}
