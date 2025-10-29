package io.flexio.services.support.mondo;

import com.mongodb.Function;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MongoFilterBuilder {

    static public MongoFilterBuilder filter() {
        return new MongoFilterBuilder();
    }

    private final List<Bson> criteria = new LinkedList<>();


    public <T> MongoFilterBuilder having(Optional<T> value, Function<T, Bson> criterion) {
        value.ifPresent(v -> this.criteria.add(criterion.apply(v)));
        return this;
    }

    public <T> MongoFilterBuilder and(Bson criterion) {
        this.criteria.add(criterion);
        return this;
    }

    public Bson build() {
        if (this.criteria.isEmpty()) {
            return null;
        }
        if (this.criteria.size() == 1) {
            return this.criteria.getFirst();
        }
        return Filters.and(this.criteria);
    }
}
