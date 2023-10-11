package io.flexio.svg.generator.generator.tag.element.xhtml;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.XHTMLElement;

public class Th extends ClosableElement<Th> implements XHTMLElement<Th> {
    @Override
    public String name() {
        return "th";
    }
}
