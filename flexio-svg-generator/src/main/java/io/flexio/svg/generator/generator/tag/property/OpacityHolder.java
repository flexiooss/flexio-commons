package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;

import java.math.BigDecimal;
import java.util.Locale;

public interface OpacityHolder<O extends OpacityHolder<O>> extends Attribute {
    String OPACITY = "opacity";
    @SuppressWarnings("unchecked")
    default O opacity(String opacity) {
        final String defaultValue = "1";

        if (defaultValue.equals(opacity)) {
            this.attributesMap().remove(OPACITY);
        } else {
            attributes(OPACITY, opacity);
        }

        return (O) this;
    }

    default O opacity(String format, Object... args) {
        return this.opacity(String.format(Locale.US, format, args));
    }

    @SuppressWarnings("unchecked")
    default O opacity(double opacity) {
        opacity = Math.min(Math.max(opacity, 0d), 1d);

        if (opacity == 1d) {
            this.attributesMap().remove(OPACITY);
            return (O) this;
        }

        return this.opacity(NUMERIC_FORMAT, opacity);
    }

    default O opacity(BigDecimal opacity) {
        return this.opacity(opacity.toPlainString());
    }
}
