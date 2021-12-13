package io.flexio.svg.generator.generator.tag.element.xhtml;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.XHTMLElement;

public final class Ol extends ClosableElement<Ol> implements XHTMLElement<Ol> {
    @Override
    public String name() {
        return "ol";
    }
}
