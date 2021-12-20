package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;

import java.math.BigDecimal;
import java.util.Locale;

public interface PathLengthHolder<P extends PathLengthHolder> extends Attribute {
    String PATH_LENGTH = "pathLength";
    @SuppressWarnings("unchecked")
    default P pathLength(String length) {
        final String defaultValue = "none";

        if (defaultValue.equals(length)) {
            attributesMap().remove(PATH_LENGTH);
        } else {
            attributes(PATH_LENGTH, length);
        }

        return (P) this;
    }

    default P pathLength(String lengthFormat, Object... args) {
        return pathLength(String.format(Locale.US, lengthFormat, args));
    }

    default P pathLength(double length) {
        return this.pathLength(NUMERIC_FORMAT, length);
    }

    default P pathLength(BigDecimal length) {
        return this.pathLength(length.toPlainString());
    }
}
