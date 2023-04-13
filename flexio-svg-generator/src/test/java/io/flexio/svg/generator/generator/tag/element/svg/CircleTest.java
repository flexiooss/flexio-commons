package io.flexio.svg.generator.generator.tag.element.svg;

import io.flexio.svg.generator.generator.tag.type.Point;
import io.flexio.svg.generator.generator.writer.SvgWriter;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CircleTest {

    @Test
    public void CircleWithPoints() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        SvgWriter writer = new SvgWriter(printer);
        new Circle().position(new Point(0.12345, 0.12345)).open(writer);
        String string = stringWriter.toString();
        assertThat(string, is("<circle cx=\"0.1\" cy=\"0.1\"/>"));
    }

    @Test
    public void CircleWithValues() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        SvgWriter writer = new SvgWriter(printer);
        new Circle().position(0.12345, 0.12345).radius(0.514551).open(writer);
        String string = stringWriter.toString();
        assertThat(string, is("<circle cx=\"0.1\" cy=\"0.1\" r=\"0.5\"/>"));
    }
}
