package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;

import java.math.BigDecimal;
import java.util.Locale;

public interface Sizeable<S extends Sizeable> extends Attribute {
    @SuppressWarnings("unchecked")
    default S width(String width) {
        attributes("width", width);
        return (S) this;
    }

    default S width(String format, Object... args) {
        return this.width(String.format(Locale.US, format, args));
    }

    default S width(double width) {
        return this.width(NUMERIC_FORMAT, Math.max(width, 0d));
    }

    default S width(BigDecimal width) {
        return this.width(width.toPlainString());
    }


    @SuppressWarnings("unchecked")
    default S height(String height) {
        attributes("height", height);
        return (S) this;
    }

    default S height(String format, Object... args) {
        return this.height(String.format(Locale.US, format, args));
    }

    default S height(double height) {
        return this.height(NUMERIC_FORMAT, Math.max(height, 0d));
    }

    default S height(BigDecimal height) {
        return this.height(height.toPlainString());
    }
}