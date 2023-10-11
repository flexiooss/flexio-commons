package io.flexio.svg.generator.generator.tag.element.xhtml;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.XHTMLElement;

public class Thead extends ClosableElement<Thead> implements XHTMLElement<Thead> {
    @Override
    public String name() {
        return "thead";
    }
}
