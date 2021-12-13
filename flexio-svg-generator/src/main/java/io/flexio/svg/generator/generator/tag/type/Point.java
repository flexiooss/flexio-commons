package io.flexio.svg.generator.generator.tag.type;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;

import static io.flexio.svg.generator.generator.Attribute.NUMERIC_FORMAT;

public class Point {
    String x;
    String y;

    public Point(BigDecimal x, BigDecimal y) {
        this(x.toPlainString(), y.toPlainString());
    }

    public Point(double x, double y) {
        this(String.format(Locale.US, NUMERIC_FORMAT, x), String.format(Locale.US, NUMERIC_FORMAT, y));
    }

    public Point(String x, String y) {
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
        return "Point{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(x, point.x) &&
                Objects.equals(y, point.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
