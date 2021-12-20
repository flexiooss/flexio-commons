package io.flexio.svg.generator.generator.tag.property;

import io.flexio.svg.generator.generator.Attribute;

import java.util.Locale;

public interface Rotatable<R extends Rotatable> extends Attribute {
    @SuppressWarnings("unchecked")
    default R rotate(String mode) {
        this.attributes("rotate", mode);
        return (R) this;
    }

    default R rotate(String modeFormat, Object... args) {
        return this.rotate(String.format(Locale.US, modeFormat, args));
    }

    default R rotate(double alpha) {
        return this.rotate(NUMERIC_FORMAT, alpha);
    }

    default R rotate(Mode mode) {
        return this.rotate(mode.toString());
    }

    enum Mode {
        AUTO {
            @Override
            public String toString() {
                return "auto";
            }
        },
        REVERSE {
            @Override
            public String toString() {
                return "auto-reverse";
            }
        }
    }
}
