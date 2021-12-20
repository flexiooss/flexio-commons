package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;
import io.flexio.svg.generator.generator.tag.property.ClipPathHolder;
import io.flexio.svg.generator.generator.tag.property.OpacityHolder;
import io.flexio.svg.generator.generator.tag.property.Positionable;
import io.flexio.svg.generator.generator.tag.property.Rotatable;
import io.flexio.svg.generator.generator.tag.type.Point;


public final class Tspan extends ClosableElement<Tspan> implements SVGElement<Tspan>,
        Positionable<Tspan>, Rotatable<Tspan>, ClipPathHolder<Tspan>, OpacityHolder<Tspan>
{
    private static final String tspan = "tspan";
    @Override
    public String name() {
        return tspan;
    }

    public Tspan relativePosition(Point position) {
        attributes("dx", position.x());
        attributes("dy", position.y());
        return this;
    }
}
