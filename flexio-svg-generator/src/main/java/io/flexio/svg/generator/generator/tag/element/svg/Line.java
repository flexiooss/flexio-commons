package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.AutoClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;
import io.flexio.svg.generator.generator.tag.property.*;
import io.flexio.svg.generator.generator.tag.type.Point;
import io.flexio.svg.generator.generator.writer.ElementWriter;

public final class Line extends AutoClosableElement<Line> implements SVGElement<Line>,
        Fillable<Line>, Strokable<Line>, StrokeWidthHolder<Line> , ClipPathHolder<Line>, OpacityHolder<Line>
{
    private static final String line = "line";
    @Override
    public String name() {
        return line;
    }

    public Line positionStart(Point position) {
        attributes("x1", position.x());
        attributes("y1", position.y());
        return this;
    }

    public Line positionEnd(Point position) {
        attributes("x2", position.x());
        attributes("y2", position.y());
        return this;
    }

    public void autoClosed(ElementWriter writer) {
        writer.autoClosed(line);
    }
}
