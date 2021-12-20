package io.flexio.svg.generator.generator.writer;

import io.flexio.svg.generator.generator.Attribute;
import io.flexio.svg.generator.generator.Element;

public interface ElementWriter {
    void open(String name, Attribute attributes);
    default void open(String name) {
        this.open(name, Attribute.EMPTY);
    }
    default void open(Element element) {
        this.open(element.name(), element);
    }

    void autoClosed(String name, Attribute attributes);
    default void autoClosed(String name) {
        this.autoClosed(name, Attribute.EMPTY);
    }
    default void autoClosed(Element element) {
        this.autoClosed(element.name(), element);
    }

    void close(String name);
    void text(String text);
}
