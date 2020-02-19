package io.flexio.io.mongo.repository.property.query;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.codingmatters.poom.services.domain.property.query.FilterEvents;
import org.codingmatters.poom.services.domain.property.query.events.FilterEventError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class BsonFilterEvents implements FilterEvents {

    private final Stack<Bson> stack = new Stack<>();

    @Override
    public Object isEquals(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.eq(left, right));
        return null;
    }

    @Override
    public Object isEqualsProperty(String left, String right) throws FilterEventError {
        // db.myCollection.find({$expr: {$eq: ["$a1.a", "$a2.a"] } });
        this.stack.push(Filters.expr(new Document("$eq", Arrays.asList("$" + left, "$" + right))));
        return null;
    }

    @Override
    public Object isNotEquals(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.ne(left, right));
        return null;
    }

    @Override
    public Object isNotEqualsProperty(String left, String right) throws FilterEventError {
        this.stack.push(Filters.expr(new Document("$ne", Arrays.asList("$" + left, "$" + right))));
        return null;
    }

    @Override
    public Object lowerThan(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.lt(left, right));
        return null;
    }

    @Override
    public Object lowerThanProperty(String left, String right) throws FilterEventError {
        this.stack.push(Filters.expr(new Document("$lt", Arrays.asList("$" + left, "$" + right))));
        return null;
    }

    @Override
    public Object lowerThanOrEquals(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.lte(left, right));
        return null;
    }

    @Override
    public Object lowerThanOrEqualsProperty(String left, String right) throws FilterEventError {
        this.stack.push(Filters.expr(new Document("$lte", Arrays.asList("$" + left, "$" + right))));
        return null;
    }

    @Override
    public Object graterThan(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.gt(left, right));
        return null;
    }

    @Override
    public Object graterThanProperty(String left, String right) throws FilterEventError {
        this.stack.push(Filters.expr(new Document("$gt", Arrays.asList("$" + left, "$" + right))));
        return null;
    }

    @Override
    public Object graterThanOrEquals(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.gte(left, right));
        return null;
    }

    @Override
    public Object graterThanOrEqualsProperty(String left, String right) throws FilterEventError {
        this.stack.push(Filters.expr(new Document("$gte", Arrays.asList("$" + left, "$" + right))));
        return null;
    }

    @Override
    public Object isNull(String property) throws FilterEventError {
        this.stack.push(Filters.eq(property, null));
        return null;
    }

    @Override
    public Object isNotNull(String property) throws FilterEventError {
        this.stack.push(Filters.ne(property, null));
        return null;
    }

    @Override
    public Object startsWith(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.regex(left, "^" + right + ".*"));
        return null;
    }

    @Override
    public Object endsWith(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.regex(left, ".*" + right + "$"));
        return null;
    }

    @Override
    public Object contains(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.regex(left, "^.*" + right + ".*$"));
        return null;
    }

    @Override
    public Object and() throws FilterEventError {
        Bson and = Filters.and(new ArrayList<>(this.stack));
        this.stack.clear();
        this.stack.push(and);
        return null;
    }

    @Override
    public Object or() throws FilterEventError {
        Bson or = Filters.or(new ArrayList<>(this.stack));
        this.stack.clear();
        this.stack.push(or);
        return null;
    }

    @Override
    public Object not() throws FilterEventError {
        this.stack.push(Filters.not(this.stack.pop()));
        return null;
    }

    @Override
    public Object startsWithProperty(String left, String right) throws FilterEventError {
        throw new FilterEventError("cannot apply starts with to property");
    }

    @Override
    public Object endsWithProperty(String left, String right) throws FilterEventError {
        throw new FilterEventError("cannot apply end with to property");
    }

    @Override
    public Object containsProperty(String left, String right) throws FilterEventError {
        throw new FilterEventError("cannot apply contains to property");
    }

    public Bson filter() {
        if(this.stack.isEmpty()) {
            return null;
        }
        return this.stack.peek();
    }
}
