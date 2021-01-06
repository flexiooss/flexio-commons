package io.flexio.io.mongo.repository.property.query;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.codingmatters.poom.services.domain.property.query.FilterEvents;
import org.codingmatters.poom.services.domain.property.query.events.FilterEventError;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Stack;

public class BsonFilterEvents implements FilterEvents {

    private final Stack<Bson> stack = new Stack<>();

    @Override
    public Object isEquals(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.eq(this.property(left, right), this.value(right)));
        return null;
    }

    private String property(String name, Object comparedToValue) {
        if(comparedToValue instanceof ZonedDateTime) {
            return name + ".ts";
        }
        return name;
    }

    @Override
    public Object isEqualsProperty(String left, String right) throws FilterEventError {
        this.stack.push(Filters.expr(new Document("$eq", Arrays.asList("$" + left, "$" + right))));
        return null;
    }

    @Override
    public Object isNotEquals(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.ne(this.property(left, right), this.value(right)));
        return null;
    }

    @Override
    public Object isNotEqualsProperty(String left, String right) throws FilterEventError {
        this.stack.push(Filters.expr(new Document("$ne", Arrays.asList("$" + left, "$" + right))));
        return null;
    }

    @Override
    public Object lowerThan(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.lt(this.property(left, right), this.value(right)));
        return null;
    }

    @Override
    public Object lowerThanProperty(String left, String right) throws FilterEventError {
        this.stack.push(Filters.expr(new Document("$lt", Arrays.asList("$" + left, "$" + right))));
        return null;
    }

    @Override
    public Object lowerThanOrEquals(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.lte(this.property(left, right), this.value(right)));
        return null;
    }

    @Override
    public Object lowerThanOrEqualsProperty(String left, String right) throws FilterEventError {
        this.stack.push(Filters.expr(new Document("$lte", Arrays.asList("$" + left, "$" + right))));
        return null;
    }

    @Override
    public Object graterThan(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.gt(this.property(left, right), this.value(right)));
        return null;
    }

    @Override
    public Object graterThanProperty(String left, String right) throws FilterEventError {
        this.stack.push(Filters.expr(new Document("$gt", Arrays.asList("$" + left, "$" + right))));
        return null;
    }

    @Override
    public Object graterThanOrEquals(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.gte(this.property(left, right), this.value(right)));
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
        this.stack.push(Filters.regex(this.property(left, right), "^" + this.value(right) + ".*"));
        return null;
    }

    @Override
    public Object endsWith(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.regex(this.property(left, right), ".*" + this.value(right) + "$"));
        return null;
    }

    @Override
    public Object contains(String left, Object right) throws FilterEventError {
        this.stack.push(Filters.regex(this.property(left, right), "^.*" + this.value(right) + ".*$"));
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

    private Object value(Object v) {
        if(v != null) {
            if(v instanceof Temporal) {
                if(v instanceof ZonedDateTime) {
                    return Date.from(((ZonedDateTime)v).toInstant());
                } if(v instanceof LocalDateTime) {
                    return Date.from(((LocalDateTime)v).atZone(ZoneId.of("Z")).toInstant());
                } if(v instanceof LocalDate) {
                    return Date.from(((LocalDate)v).atStartOfDay().atZone(ZoneId.of("Z")).toInstant());
                } if(v instanceof LocalTime) {
                    return Date.from(((LocalTime)v).atDate(LocalDate.of(1970, 1, 1)).atZone(ZoneId.of("Z")).toInstant());
                }
            }
            if(v instanceof Number) {
                return new BigDecimal(v.toString());
            }
        }
        return v;
    }


    public Bson filter() {
        if(this.stack.isEmpty()) {
            return null;
        }
        return this.stack.peek();
    }
}
