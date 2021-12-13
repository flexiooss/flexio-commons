package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.AutoClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;
import io.flexio.svg.generator.generator.tag.property.*;
import io.flexio.svg.generator.generator.writer.ElementWriter;

public final class Path extends AutoClosableElement<Path> implements SVGElement<Path>,
        Fillable<Path>, Strokable<Path>, StrokeWidthHolder<Path>, PathLengthHolder<Path> , ClipPathHolder<Path>,
        OpacityHolder<Path>
{
    private static final String path = "path";
    @Override
    public String name() {
        return path;
    }

    public Path path(String d) {
        attributes("d", d);
        return this;
    }

    public void autoClosed(ElementWriter writer) {
        writer.autoClosed(this);
    }
}
