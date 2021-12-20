package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;

import java.util.Locale;

public interface Strokable<S extends Strokable> extends Attribute {
    @SuppressWarnings("unchecked")
    default S stroke(String stroke) {
        final String strokeProperty = "stroke", defaultValue = "none";

        if (defaultValue.equals(stroke)) {
            attributesMap().remove(strokeProperty);
        } else {
            attributes(strokeProperty, stroke);
        }

        return (S) this;
    }

    default S stroke(String format, Object... args){
        return this.stroke(String.format(Locale.US, format, args));
    }
}
