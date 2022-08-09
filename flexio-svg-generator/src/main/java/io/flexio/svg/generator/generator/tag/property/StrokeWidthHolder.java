package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;

import java.math.BigDecimal;
import java.util.Locale;

public interface StrokeWidthHolder<S extends StrokeWidthHolder> extends Attribute {
    String STROKE_WIDTH = "stroke-width";
    @SuppressWarnings("unchecked")
    default S strokeWidth(String strokeWidth) {
        if (strokeWidth == null) {
            this.attributesMap().remove(STROKE_WIDTH);
        } else {
            this.attributes("stroke-width", strokeWidth);
        }
        return (S) this;
    }
    
    default S strokeWidth(String format, Object... args) {
        return this.strokeWidth(String.format(Locale.US, format, args));
    }

    default S strokeWidth(double strokeWidth) {
        return this.strokeWidth(NUMERIC_FORMAT, strokeWidth);
    }

    default S strokeWidth(BigDecimal strokeWidth) {
        return this.strokeWidth(strokeWidth.toPlainString());
    }

    default S strokeWidthPercent(double strokeWidth) {
        return this.strokeWidth(NUMERIC_FORMAT+"%%", strokeWidth);
    }

    default S strokeWidthPercent(BigDecimal strokeWidth) {
        return this.strokeWidth(strokeWidth.toPlainString() + '%');
    }
}
