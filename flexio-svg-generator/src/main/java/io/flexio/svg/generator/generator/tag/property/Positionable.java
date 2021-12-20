package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;
import io.flexio.svg.generator.generator.tag.type.Point;

import java.util.Locale;

public interface Positionable<P extends Positionable> extends Attribute {
    String X = "x", Y = "y";
    @SuppressWarnings("unchecked")
    default P position(Point position) {
        attributes(X, position.x());
        attributes(Y, position.y());
        return (P) this;
    }

    @SuppressWarnings("unchecked")
    default P position(String x, String y) {
        attributes(X, x);
        attributes(Y, y);
        return (P) this;
    }

    default P position(double x, double y) {
        return this.position(
                String.format(Locale.US, NUMERIC_FORMAT, x),
                String.format(Locale.US, NUMERIC_FORMAT, y)
        );
    }
}
