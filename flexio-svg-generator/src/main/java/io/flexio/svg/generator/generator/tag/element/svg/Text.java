package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;
import io.flexio.svg.generator.generator.tag.property.*;

import java.math.BigDecimal;
import java.util.Locale;

public final class Text extends ClosableElement<Text> implements SVGElement<Text>,
        Positionable<Text>, Fillable<Text>, Strokable<Text>, StrokeWidthHolder<Text>, Rotatable<Text>, ClipPathHolder<Text>,
        OpacityHolder<Text>
{
    private static final String text = "text";
    @Override
    public String name() {
        return text;
    }

    public Text fontFamily(String value) {
        attributes("font-family", value);
        return this;
    }

    public Text fontSize(String value) {
        attributes("font-size", value);
        return this;
    }

    public Text fontSize(String fontSizeFormat, Object... args) {
        return this.fontSize(String.format(Locale.US, fontSizeFormat, args));
    }

    public Text fontSize(double value) {
        return this.fontSize("%.5f", value);
    }

    public Text fontSize(BigDecimal value) {
        return this.fontSize(value.toPlainString());
    }

    public Text fontWeight(String weight) {
        attributes("font-weight", weight);
        return this;
    }

    public Text fontWeight(String weightFormat, Object... args) {
        return this.fontWeight(String.format(Locale.US, weightFormat, args));
    }

    public Text fontWeight(int weight) {
        return this.fontWeight("%d", weight);
    }

    public Text fontWeight(long weight) {
        return this.fontWeight("%d", weight);
    }

    public Text fontWeight(double weight) {
        return this.fontWeight("%.5f", weight);
    }

    public Text fontWeight(BigDecimal weight) {
        return this.fontWeight(weight.toPlainString());
    }

    public Text fontWeight(FontWeight weight) {
        return this.fontWeight(weight.toString());
    }

    public enum FontWeight {
        NORMAL, BOLD, BOLDER, LIGHTER;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    public Text anchor(String anchor) {
        attributes("text-anchor", anchor);
        return this;
    }

    public Text anchor(Anchor anchor) {
        return this.anchor(anchor.toString());
    }

    public enum Anchor {
        START {
            @Override
            public String toString() {
                return "start";
            }
        },
        MIDDLE {
            @Override
            public String toString() {
                return "middle";
            }
        },
        END {
            @Override
            public String toString() {
                return "end";
            }
        }
    }
}