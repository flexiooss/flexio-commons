package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.XHTMLElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;
import io.flexio.svg.generator.generator.tag.property.ClipPathHolder;
import io.flexio.svg.generator.generator.tag.property.OpacityHolder;
import io.flexio.svg.generator.generator.tag.property.Positionable;
import io.flexio.svg.generator.generator.tag.property.Sizeable;

import java.util.Locale;

public final class Svg extends ClosableElement<Svg> implements SVGElement<Svg>, XHTMLElement<Svg>,
        Sizeable<Svg>, Positionable<Svg>, ClipPathHolder<Svg>, OpacityHolder<Svg>
{
    private static final String svg = "svg";
    @Override
    public String name() {
        return svg;
    }

    public Svg() {
        this.attributes("xmlns", "http://www.w3.org/2000/svg");
    }

    public Svg lang(String lang) {
        attributes("xml:lang", lang);
        return this;
    }

    public Svg viewBox(String viewBox) {
        attributes("viewBox", viewBox);
        return this;
    }

    public Svg viewBox(String viewBoxFormat, Object... args) {
        return this.viewBox(String.format(Locale.US, viewBoxFormat, args));
    }

    public Svg viewBox(double width, double height) {
        return this.viewBox("0 0 "+NUMERIC_FORMAT+" "+NUMERIC_FORMAT, width, height);
    }

    public Svg viewBox(int minX, int minY, int width, int height) {
        return this.viewBox("%d %d %d %d", minX, minY, width, height);
    }

    public Svg viewBox(long minX, long minY, long width, long height) {
        return this.viewBox("%d %d %d %d", minX, minY, width, height);
    }

    public Svg viewBox(double minX, double minY, double width, double height) {
        return this.viewBox(
                NUMERIC_FORMAT+" "+NUMERIC_FORMAT+" "+NUMERIC_FORMAT+" "+NUMERIC_FORMAT,
                minX, minY, width, height
        );
    }
}