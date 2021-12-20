package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;

import java.util.Locale;

public interface Identifiable<I extends Identifiable> extends Attribute {
    String ID = "id";
    @SuppressWarnings("unchecked")
    default I id(String id) {
        attributes(ID, id);
        return (I) this;
    }

    default I id(String format, Object... args) {
        return this.id(String.format(Locale.US, format, args));
    }
}
