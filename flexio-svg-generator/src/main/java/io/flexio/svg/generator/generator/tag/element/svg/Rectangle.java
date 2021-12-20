package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.AutoClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;
import io.flexio.svg.generator.generator.tag.property.*;
import io.flexio.svg.generator.generator.tag.type.Radius;

public final class Rectangle extends AutoClosableElement<Rectangle> implements SVGElement<Rectangle>,
        Fillable<Rectangle>, Strokable<Rectangle>, StrokeWidthHolder<Rectangle>, PathLengthHolder<Rectangle>,
        Positionable<Rectangle>, Sizeable<Rectangle>, ClipPathHolder<Rectangle>, OpacityHolder<Rectangle>
{
    private static final String rectangle = "rect";
    @Override
    public String name() {
        return rectangle;
    }

    public Rectangle rounded(Radius radius) {
        attributes("rx", radius.x());
        attributes("ry", radius.y());
        return this;
    }
}