package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;

import java.util.Locale;

public interface Classifiable<C extends Classifiable> extends Attribute {
    String CLASS = "class";

    @SuppressWarnings("unchecked")
    default C classSelector(String... classSelectors) {
        if (classSelectors.length > 0) {
            attributesMap().computeIfPresent(CLASS, (k, v) -> v + ' ' + String.join(" ", classSelectors));
            attributesMap().putIfAbsent(CLASS, String.join(" ", classSelectors));
        }
        return (C) this;
    }

    default C classSelector(String format, Object... args) {
        return this.classSelector(String.format(Locale.US, format, args));
    }
}
