package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.AutoClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;
import io.flexio.svg.generator.generator.tag.property.*;

import java.util.Locale;

public final class Polygon extends AutoClosableElement<Polygon> implements SVGElement<Polygon>,
        Fillable<Polygon>, Strokable<Polygon>, StrokeWidthHolder<Polygon>, PathLengthHolder<Polygon>,
        ClipPathHolder<Polygon>, OpacityHolder<Polygon>
{
    private static final String polygon = "polygon";
    @Override
    public String name() {
        return polygon;
    }

    public Polygon points(String points) {
        attributes("points", points);
        return this;
    }

    public Polygon points(String format, Object... args) {
        return this.points(String.format(Locale.US, format, args));
    }
}
