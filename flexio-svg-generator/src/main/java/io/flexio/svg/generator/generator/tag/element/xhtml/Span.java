package io.flexio.svg.generator.generator.tag.element.xhtml;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.XHTMLElement;

public class Span extends ClosableElement<Span> implements XHTMLElement<Span> {
    @Override
    public String name() {
        return "span";
    }
}
