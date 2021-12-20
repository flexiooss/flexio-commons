package io.flexio.svg.generator.generator.tag.element.xhtml;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.XHTMLElement;

public final class Div extends ClosableElement<Div> implements XHTMLElement<Div> {
    @Override
    public String name() {
        return "div";
    }
}
