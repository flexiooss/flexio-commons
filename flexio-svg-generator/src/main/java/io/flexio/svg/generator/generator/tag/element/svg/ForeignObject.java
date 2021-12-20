package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;
import io.flexio.svg.generator.generator.tag.property.Positionable;
import io.flexio.svg.generator.generator.tag.property.Sizeable;

public final class ForeignObject extends ClosableElement<ForeignObject> implements SVGElement<ForeignObject>,
        Positionable<ForeignObject>, Sizeable<ForeignObject>
{
    @Override
    public String name() {
        return "foreignObject";
    }
}
