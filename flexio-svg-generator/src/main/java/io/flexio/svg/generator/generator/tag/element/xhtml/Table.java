package io.flexio.svg.generator.generator.tag.element.xhtml;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.XHTMLElement;

public class Table extends ClosableElement<Table> implements XHTMLElement<Table> {
    @Override
    public String name() {
        return "table";
    }
}
