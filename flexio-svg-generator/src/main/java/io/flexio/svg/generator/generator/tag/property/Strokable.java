package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;

import java.util.Locale;

public interface Strokable<S extends Strokable> extends Attribute {
    String STROKE = "stroke";
    @SuppressWarnings("unchecked")
    default S stroke(String stroke) {
        final String defaultValue = "none";

        if (defaultValue.equals(stroke)) {
            this.attributesMap().remove(STROKE);
        } else {
            this.attributes(STROKE, stroke);
        }

        return (S) this;
    }

    default S stroke(String format, Object... args){
        return this.stroke(String.format(Locale.US, format, args));
    }
}
