package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.AutoClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;
import io.flexio.svg.generator.generator.tag.property.*;

import java.util.Locale;

public final class Polyline extends AutoClosableElement<Polyline> implements SVGElement<Polyline>,
        Fillable<Polyline>, Strokable<Polyline>, StrokeWidthHolder<Polyline>, ClipPathHolder<Polyline>,
        OpacityHolder<Polyline>
{
    private static final String polyline = "polyline";
    @Override
    public String name() {
        return polyline;
    }

    public Polyline points(String points) {
        attributes("points", points);
        return this;
    }

    public Polyline points(String format, Object... args) {
        return this.points(String.format(Locale.US, format, args));
    }
}
