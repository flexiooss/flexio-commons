package io.flexio.svg.generator.generator;

import io.flexio.svg.generator.generator.writer.ElementWriter;

public abstract class ClosableElement<C extends ClosableElement> extends Element<C> {
    @Override
    public final void open(ElementWriter writer) {
        writer.open(this);
    }

    public final void close(ElementWriter writer) {
        writer.close(name());
    }
}
