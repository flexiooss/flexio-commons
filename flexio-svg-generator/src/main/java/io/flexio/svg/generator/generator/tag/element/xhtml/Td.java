package io.flexio.svg.generator.generator.tag.element.xhtml;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.XHTMLElement;

public class Td extends ClosableElement<Td> implements XHTMLElement<Td> {
    @Override
    public String name() {
        return "td";
    }
}
