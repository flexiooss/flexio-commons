package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.ClosableElement;
import io.flexio.svg.generator.generator.tag.element.SVGElement;
import io.flexio.svg.generator.generator.tag.property.*;

import java.math.BigDecimal;
import java.util.Locale;

public final class Text extends ClosableElement<Text> implements SVGElement<Text>,
        Positionable<Text>, Fillable<Text>, Strokable<Text>, StrokeWidthHolder<Text>, Rotatable<Text>, ClipPathHolder<Text>,
        OpacityHolder<Text> {
    private static final String text = "text";

    @Override
    public String name() {
        return text;
    }

    private String format(int value) {
        return String.format(Locale.US, "%d", value);
    }

    private String format(long value) {
        return String.format(Locale.US, "%d", value);
    }

    private String format(double value) {
        return String.format(Locale.US, "%d", Math.round(value));
    }

    public Text fontFamily(String value) {
        this.attributes("font-family", value);
        return this;
    }

    public Text fontSize(String value) {
        this.attributes("font-size", value);
        return this;
    }

    public Text fontSize(double value) {
        return this.fontSize(this.format(value));
    }

    public Text fontSize(BigDecimal value) {
        return this.fontSize(value.toPlainString());
    }

    public Text fontWeight(String weight) {
        this.attributes("font-weight", weight);
        return this;
    }

    public Text fontWeight(int weight) {
        return this.fontWeight(this.format(weight));
    }

    public Text fontWeight(long weight) {
        return this.fontWeight(this.format(weight));
    }

    public Text fontWeight(double weight) {
        return this.fontWeight(this.format(weight));
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
        this.attributes("text-anchor", anchor);
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
