package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.AutoClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;
import io.flexio.svg.generator.generator.tag.property.*;
import io.flexio.svg.generator.generator.tag.type.Point;

import java.math.BigDecimal;
import java.util.Locale;

public final class Circle extends AutoClosableElement<Circle> implements SVGElement<Circle>,
        Fillable<Circle>, Strokable<Circle>, StrokeWidthHolder<Circle>, Positionable<Circle>, ClipPathHolder<Circle>,
        OpacityHolder<Circle>
{
    @Override
    public String name() {
        return "circle";
    }

    public Circle radius(String radius) {
        attributes("r", radius);
        return this;
    }

    public Circle radius(double radius) {
        return this.radius(String.format(Locale.US, "%.5f",radius));
    }

    public Circle radius(BigDecimal radius) {
        return this.radius(radius.toPlainString());
    }

    @Override
    public Circle position(Point position) {
        attributes("cx", position.x());
        attributes("cy", position.y());
        return this;
    }

    @Override
    public Circle position(String x, String y) {
        attributes("cx", x);
        attributes("cy", y);
        return this;
    }

    @Override
    public Circle position(double x, double y) {
        attributes("cx", String.format(Locale.US, "%.5f", x));
        attributes("cy", String.format(Locale.US, "%.5f", y));
        return this;
    }
}