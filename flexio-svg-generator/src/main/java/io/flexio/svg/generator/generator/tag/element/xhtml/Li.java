package io.flexio.svg.generator.generator.tag.element.xhtml;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.XHTMLElement;

public final class Li extends ClosableElement<Li> implements XHTMLElement<Li> {
    @Override
    public String name() {
        return "il";
    }
}
