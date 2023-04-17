package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.AutoClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;
import io.flexio.svg.generator.generator.tag.property.*;
import io.flexio.svg.generator.generator.tag.type.Point;

import java.math.BigDecimal;
import java.util.Locale;

public final class Circle extends AutoClosableElement<Circle> implements SVGElement<Circle>,
        Fillable<Circle>, Strokable<Circle>, StrokeWidthHolder<Circle>, Positionable<Circle>, ClipPathHolder<Circle>,
        OpacityHolder<Circle> {
    @Override
    public String name() {
        return "circle";
    }

    public Circle radius(String radius) {
        this.attributes("r", radius);
        return this;
    }

    public Circle radius(double radius) {
        return this.radius(String.format(Locale.US, "%.1f", radius));
    }

    public Circle radius(BigDecimal radius) {
        return this.radius(radius.toPlainString());
    }

    @Override
    public Circle position(Point position) {
        this.attributes("cx", position.x());
        this.attributes("cy", position.y());
        return this;
    }

    @Override
    public Circle position(String x, String y) {
        this.attributes("cx", x);
        this.attributes("cy", y);
        return this;
    }

    @Override
    public Circle position(double x, double y) {
        this.attributes("cx", String.format(Locale.US, NUMERIC_FORMAT, x));
        this.attributes("cy", String.format(Locale.US, NUMERIC_FORMAT, y));
        return this;
    }
}
