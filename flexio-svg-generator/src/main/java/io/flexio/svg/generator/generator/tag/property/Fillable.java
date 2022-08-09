package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;

import java.math.BigDecimal;
import java.util.Locale;

public interface Fillable<F extends Fillable> extends Attribute {
    String FILL = "fill";
    @SuppressWarnings("unchecked")
    default F fill(String fill) {
        final String defaultValue = "black";

        if (fill == null || defaultValue.equals(fill)) {
            this.attributesMap().remove(FILL);
        } else {
            this.attributes(FILL, fill);
        }

        return (F) this;
    }

    default F fill(String format, Object... args) {
        return this.fill(String.format(Locale.US, format, args));
    }


    String FILL_OPACITY = "fill-opacity";
    @SuppressWarnings("unchecked")
    default F fillOpacity(String fill) {
        final String defaultValue = "1";

        if (fill == null || defaultValue.equals(fill)) {
            this.attributesMap().remove(FILL_OPACITY);
        } else {
            this.attributes(FILL_OPACITY, fill);
        }

        return (F) this;
    }

    default F fillOpacity(String format, Object... args) {
        return this.fillOpacity(String.format(Locale.US, format, args));
    }


    @SuppressWarnings("unchecked")
    default F fillOpacity(double opacity) {
        opacity = Math.min(Math.max(opacity, 0d), 1d);

        if (opacity == 1d) {
            this.attributesMap().remove(FILL_OPACITY);
            return (F) this;
        }

        return this.fillOpacity(NUMERIC_FORMAT, opacity);
    }

    default F fillOpacity(BigDecimal opacity) {
        return this.fillOpacity(opacity.toPlainString());
    }
}
