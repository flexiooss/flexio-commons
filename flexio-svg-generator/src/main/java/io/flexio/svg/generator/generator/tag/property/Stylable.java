package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;

import java.util.Locale;

public interface Stylable<S extends Stylable> extends Attribute {
    @SuppressWarnings("unchecked")
    default S style(String style) {
        attributes("style", style);
        return (S) this;
    }

    default S style(String format, Object... args) {
        return this.style(String.format(Locale.US, format, args));
    }
}
