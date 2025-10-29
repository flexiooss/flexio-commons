package io.flexio.io.mongo.repository.property.query;

import com.mongodb.client.model.Filters;
import io.flexio.io.mongo.repository.property.query.config.MongoFilterConfig;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.codingmatters.poom.services.domain.property.query.FilterEvents;
import org.codingmatters.poom.services.domain.property.query.events.FilterEventError;
import org.codingmatters.poom.services.domain.property.query.events.FilterEventsGenerator;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.regex.Pattern;

public class BsonFilterEvents implements FilterEvents<Object> {

    public static final Bson NEVER_MATCHES = Filters.eq("_id", "__idthatwillnevermatch__");
    public static final Bson ALWAYS_MATCHES = Filters.empty();

    private final MongoFilterConfig config;

    private final Stack<Bson> stack = new Stack<>();

    public BsonFilterEvents() {
        this(null);
    }

    public BsonFilterEvents(MongoFilterConfig config) {
        this.config = config != null ?
                config.withPotentialOids(config.opt().potentialOids().safe()) :
                MongoFilterConfig.builder()
                        .potentialOids(Collections.emptyList())
                        .build()
        ;
    }

    @Override
    public Object isEquals(String left, Object right) throws FilterEventError {
        Bson filter;
        if (this.config.potentialOids().contains(left)) {
            try {
                Object rightWithOids = new ObjectId(right.toString());
                filter = Filters.or(
                        Filters.eq(this.property(left, right), this.value(right)),
                        Filters.eq(this.property(left, right), rightWithOids)
                );
            } catch (IllegalArgumentException e) {
                filter = Filters.eq(this.property(left, right), this.value(right));
            }
        } else {
            filter = Filters.eq(this.property(left, right), this.value(right));
        }
        this.stack.push(filter);
        return null;
    }

    @Override
    public Object isEqualsProperty(String left, String right) throws FilterEventError {
        this.stack.push(Filters.expr(new Document("$eq", Arrays.asList("$" + left, "$" + right))));
        return null;
    }

    @Override
    public Object isNotEquals(String left, Object right) throws FilterEventError {
        Bson filter;
        if (this.config.potentialOids().contains(left)) {
            try {
                ObjectId rightAsObjectId = new ObjectId(right.toString());
                filter = Filters.and(
                        Filters.ne(this.property(left, right), this.value(right)),
                        Filters.ne(this.property(left, right), rightAsObjectId)
                );
            } catch (IllegalArgumentException e) {
                filter = Filters.ne(this.property(left, right), this.value(right));
            }
        } else {
            filter = Filters.ne(this.property(left, right), this.value(right));
        }
        this.stack.push(filter);
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
    public Object isEmpty(String property) throws FilterEventError {
        this.stack.push(Filters.or(
                Filters.eq(property, null),
                Filters.eq(property, "")
        ));
        return null;
    }

    @Override
    public Object isNotEmpty(String property) throws FilterEventError {
        this.stack.push(Filters.and(
                Filters.ne(property, null),
                Filters.ne(property, "")
        ));
        return null;
    }

    @Override
    public Object startsWith(String left, Object right) throws FilterEventError {
        this.stack.push(this.startsWithRegex(left, right));
        return null;
    }

    private Bson startsWithRegex(String left, Object right) {
        String value = (String) this.value(right);
        if (value != null) {
            return Filters.regex(this.property(left, right), "^" + Pattern.quote(value) + ".*");
        } else {
            return Filters.eq(left, null);
        }
    }

    @Override
    public Object endsWith(String left, Object right) throws FilterEventError {
        this.stack.push(this.endsWithRegex(left, right));
        return null;
    }

    private Bson endsWithRegex(String left, Object right) {
        String value = (String) this.value(right);
        if (value != null) {
            return Filters.regex(this.property(left, right), ".*" + Pattern.quote(value) + "$");
        } else {
            return Filters.eq(left, null);
        }
    }

    @Override
    public Object in(String left, List right) throws FilterEventError {
        Bson filter;
        if (this.config.potentialOids().contains(left)) {
            List<Object> oids = new ArrayList<>();
            List<Object> raw = new ArrayList<>();
            for (Object o : right) {
                try {
                    Object oid = new ObjectId(o.toString());
                    oids.add(oid);
                } catch (IllegalArgumentException e) {
                    raw.add(this.value(o));
                }
            }
            if (raw.isEmpty()) {
                filter = Filters.in(this.property(left, right), oids);
            } else if (oids.isEmpty()) {
                filter = Filters.in(this.property(left, right), raw);
            } else {
                filter = Filters.or(
                        Filters.in(this.property(left, right), raw),
                        Filters.in(this.property(left, right), oids)
                );
            }
        } else {
            filter = Filters.in(this.property(left, right), this.values(right));
        }
        this.stack.push(filter);
        return null;
    }

    @Override
    public Object anyIn(String left, List right) throws FilterEventError {
        Bson filter;
        if (this.config.potentialOids().contains(left)) {
            List<Object> oids = new ArrayList<>();
            List<Object> raw = new ArrayList<>();
            for (Object o : right) {
                try {
                    Object oid = new ObjectId(o.toString());
                    oids.add(oid);
                } catch (IllegalArgumentException e) {
                    raw.add(this.value(o));
                }
            }
            if (raw.isEmpty()) {
                filter = Filters.in(this.property(left, right), oids);
            } else if (oids.isEmpty()) {
                filter = Filters.in(this.property(left, right), raw);
            } else {
                filter = Filters.or(
                        Filters.in(this.property(left, right), raw),
                        Filters.in(this.property(left, right), oids)
                );
            }
        } else {
            filter = Filters.in(this.property(left, right), this.values(right));
        }
        this.stack.push(filter);
        return null;
    }

    @Override
    public Object startsWithAny(String left, List right) throws FilterEventError {
        List<Bson> any = new LinkedList<>();
        if (right != null && !right.isEmpty()) {
            for (Object value : right) {
                any.add(this.startsWithRegex(left, value));
            }
            this.stack.push(Filters.or(any));
        } else {
            this.stack.push(NEVER_MATCHES);
        }
        return null;
    }

    @Override
    public Object endsWithAny(String left, List right) throws FilterEventError {
        List<Bson> any = new LinkedList<>();
        if (right != null && !right.isEmpty()) {
            for (Object value : right) {
                any.add(this.endsWithRegex(left, value));
            }
            this.stack.push(Filters.or(any));
        } else {
            this.stack.push(NEVER_MATCHES);
        }
        return null;
    }

    @Override
    public Object contains(String left, Object right) throws FilterEventError {
        this.stack.push(this.containsRegex(left, right));
        return null;
    }

    @Override
    public Object containsAny(String left, List right) throws FilterEventError {
        List<Bson> any = new LinkedList<>();
        if (right != null && !right.isEmpty()) {
            for (Object value : right) {
                any.add(this.containsRegex(left, value));
            }
            this.stack.push(Filters.or(any));
        } else {
            this.stack.push(NEVER_MATCHES);
        }
        return null;
    }

    @Override
    public Object containsAll(String left, List right) throws FilterEventError {
        List<Bson> all = new LinkedList<>();
        if (right != null && !right.isEmpty()) {
            for (Object value : right) {
                all.add(this.containsRegex(left, value));
            }
            this.stack.push(Filters.and(all));
        } else {
            this.stack.push(ALWAYS_MATCHES);
        }
        return null;
    }

    @Override
    public Object isMatchingPattern(String left, String pattern, List<PatternOption> options) throws FilterEventError {
        if (!options.isEmpty() && options.contains(PatternOption.CASE_INSENSITIVE)) {
            this.stack.push(Filters.regex(left, pattern, "i"));
        } else {
            this.stack.push(Filters.regex(left, pattern));
        }
        return null;
    }

    private Bson containsRegex(String left, Object right) {
        if (right instanceof String) {
            String value = (String) this.value(right);
            if (value != null) {
                return Filters.regex(this.property(left, right), "^.*" + Pattern.quote(value) + ".*$");
            } else {
                return Filters.eq(left, null);
            }
        } else {
            return Filters.eq(this.property(left, right), this.value(right));
        }
    }

    @Override
    public Object and() throws FilterEventError {
        Bson right = this.stack.pop();
        Bson left = this.stack.pop();
        Bson and = Filters.and(left, right);
        this.stack.push(and);
        return null;
    }

    @Override
    public Object or() throws FilterEventError {
        Bson right = this.stack.pop();
        Bson left = this.stack.pop();
        Bson or = Filters.or(left, right);
        this.stack.push(or);
        return null;
    }

    @Override
    public Object not() throws FilterEventError {
        this.stack.push(Filters.nor(this.stack.pop()));
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

    private List<Object> values(List<Object> v) {
        if (v == null || v.isEmpty()) return v;
        List<Object> result = new ArrayList<>(v.size());
        for (Object o : v) {
            result.add(this.value(o));
        }

        return result;
    }

    private Object value(Object v) {
        if (v != null) {
            if (v instanceof Temporal) {
                if (v instanceof ZonedDateTime zdt) {
                    return Date.from(zdt.toInstant());
                }
                if (v instanceof LocalDateTime ldt) {
                    return Date.from(ldt.atZone(ZoneId.of("Z")).toInstant());
                }
                if (v instanceof LocalDate ld) {
                    return Date.from(ld.atStartOfDay().atZone(ZoneId.of("Z")).toInstant());
                }
                if (v instanceof LocalTime lt) {
                    return Date.from(lt.atDate(LocalDate.of(1970, 1, 1)).atZone(ZoneId.of("Z")).toInstant());
                }
            }
            if (v instanceof Number) {
                return new BigDecimal(v.toString());
            }
            if (v instanceof FilterEventsGenerator.SpecialValues values) {
                switch (values) {
                    case NULL:
                        return null;
                }
            }
        }
        return v;
    }


    public Bson filter() {
        if (this.stack.isEmpty()) {
            return null;
        }
        return this.stack.peek();
    }

    private String property(String name, Object comparedToValue) {
        if (comparedToValue instanceof ZonedDateTime) {
            return name + ".ts";
        }
        return name;
    }

}
