package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;
import io.flexio.svg.generator.generator.tag.property.ClipPathHolder;
import io.flexio.svg.generator.generator.tag.property.OpacityHolder;

public final class Group extends ClosableElement<Group> implements SVGElement<Group>,
        ClipPathHolder<Group>, OpacityHolder<Group>
{
    private static final String group = "g";
    @Override
    public String name() {
        return group;
    }

    public static Group withClasses(String... classes) {
        return new Group().classSelector(classes);
    }
}
