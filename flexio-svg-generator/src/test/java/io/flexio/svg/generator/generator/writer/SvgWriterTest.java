package io.flexio.svg.generator.generator.writer;

import io.flexio.svg.generator.generator.Attribute;
import io.flexio.svg.generator.generator.tag.element.svg.Circle;
import io.flexio.svg.generator.generator.tag.element.svg.Group;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class SvgWriterTest {

    private SvgWriter writer;
    private Attribute attributes;
    private StringWriter stringWriter;

    @Before
    public void setup() {
        attributes = () -> {
            Map<String, String> map =  new HashMap<>();
            map.put("attribute", "value");
            return map;
        };
        stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        writer = new SvgWriter(printer);
    }

    @Test
    public void writerOpenTest() {
        writer.open("tag", attributes);
        Assert.assertEquals(stringWriter.toString(), "<tag attribute=\"value\">");
    }

    @Test
    public void writerOpenWithoutAttributesTest() {
        writer.open("tag", null);
        Assert.assertEquals(stringWriter.toString(), "<tag>");
    }

    @Test
    public void writerAutoCloseTest() {
        writer.autoClosed("tag", attributes);
        Assert.assertEquals(stringWriter.toString(), "<tag attribute=\"value\"/>");
    }

    @Test
    public void writerCloseTest() {
        writer.close("tag");
        Assert.assertEquals(stringWriter.toString(), "</tag>");
    }

    @Test
    public void writerAutoCloseEmptyTest() {
        writer.autoClosed("tag");
        Assert.assertEquals(stringWriter.toString(), "<tag/>");
    }

    @Test
    public void writerOpenElement() {
        writer.open(new Group().id("ME"));
        Assert.assertEquals(stringWriter.toString(), "<g id=\"ME\">");
    }

    @Test
    public void writerAutoClosedElement() {
        writer.autoClosed(new Circle().fill("blue"));
        Assert.assertEquals(stringWriter.toString(), "<circle fill=\"blue\"/>");
    }
}
