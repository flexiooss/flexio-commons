package io.flexio.svg.generator.generator.tag.type;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;

import static io.flexio.svg.generator.generator.Attribute.NUMERIC_FORMAT;

public class Radius {
    String x;
    String y;

    public Radius(BigDecimal x, BigDecimal y) {
        this(x.toPlainString(), y.toPlainString());
    }

    public Radius(double x, double y) {
        this(String.format(Locale.US, NUMERIC_FORMAT, x), String.format(Locale.US, NUMERIC_FORMAT, y));
    }

    public Radius(String x, String y) {
        this.x = x;
        this.y = y;
    }

    public String x() {
        return x;
    }

    public String y() {
        return y;
    }

    @Override
    public String toString() {
        return "Radius{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Radius radius = (Radius) o;
        return Objects.equals(x, radius.x) &&
                Objects.equals(y, radius.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}