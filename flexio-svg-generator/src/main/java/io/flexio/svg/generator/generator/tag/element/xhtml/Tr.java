package io.flexio.svg.generator.generator.tag.element.xhtml;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.XHTMLElement;

public class Tr extends ClosableElement<Tr> implements XHTMLElement<Tr> {
    @Override
    public String name() {
        return "tr";
    }
}
