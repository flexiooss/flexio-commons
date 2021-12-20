package io.flexio.svg.generator.generator;

import io.flexio.svg.generator.generator.writer.ElementWriter;

public abstract class AutoClosableElement<A extends AutoClosableElement> extends Element<A> {
    @Override
    public final void open(ElementWriter writer) {
        writer.autoClosed(this);
    }
}
