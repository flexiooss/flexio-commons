package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;

public final class Link extends ClosableElement<Link> implements SVGElement<Link> {
    private static final String link = "a";
    @Override
    public String name() {
        return link;
    }

    public Link href(String href) {
        this.attributes("href", href);
        return this;
    }
}
