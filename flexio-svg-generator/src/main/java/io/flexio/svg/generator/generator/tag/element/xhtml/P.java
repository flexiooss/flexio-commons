package io.flexio.svg.generator.generator.tag.element.xhtml;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.XHTMLElement;

public final class P extends ClosableElement<P> implements XHTMLElement<P> {
    @Override
    public String name() {
        return "p";
    }
}
