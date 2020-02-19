package io.flexio.io.mongo.repository.property.query;

import com.mongodb.client.model.Sorts;
import org.bson.conversions.Bson;
import org.codingmatters.poom.services.domain.property.query.SortEvents;
import org.codingmatters.poom.services.domain.property.query.events.SortEventError;

import java.util.LinkedList;
import java.util.List;

public class BsonSortEvents implements SortEvents {

    private final List<Bson> list = new LinkedList<>();

    @Override
    public Object sorted(String property, Direction direction) throws SortEventError {
        list.add(direction.equals(Direction.ASC) ? Sorts.ascending(property) : Sorts.descending(property));
        return null;
    }

    public Bson sort() {
        if(this.list.isEmpty()) {
            return null;
        }
        return Sorts.orderBy(this.list);
    }
}
