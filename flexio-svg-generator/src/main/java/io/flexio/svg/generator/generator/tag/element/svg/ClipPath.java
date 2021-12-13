package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;

public final class ClipPath extends ClosableElement<ClipPath> implements SVGElement<ClipPath> {
    private static final String clipPath = "clipPath";
    @Override
    public String name() {
        return clipPath;
    }

    public ClipPath clipPathUnits(Unit unit) {
        if (unit != null) {
            attributes("clipPathUnits", unit.toString());
        }
        return this;
    }

    public enum Unit {
        USER_SPACE_ON_USE {
            @Override
            public String toString() {
                return "userSpaceOnUse";
            }
        },
        OBJECT_BOUNDING_BOX {
            @Override
            public String toString() {
                return "objectBoundingBox";
            }
        }
    }
}
