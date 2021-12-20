package io.flexio.svg.generator.generator.writer;

import io.flexio.svg.generator.generator.Attribute;

import java.io.*;

public class SvgWriter implements ElementWriter {
    private PrintWriter outputStream;

    public SvgWriter(PrintWriter outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void open(String name, Attribute attributes) {
        this.outputStream.write("<" + name + printAttributes(attributes) + ">");
    }

    @Override
    public void autoClosed(String name, Attribute attributes) {
        this.outputStream.write("<" + name +  printAttributes(attributes) + "/>");
    }

    @Override
    public void close(String name) {
        this.outputStream.write("</" + name + ">");
    }

    @Override
    public void text(String text) {
        this.outputStream.write(text);
    }

    private static String printAttributes(Attribute attributes) {
        StringBuilder stringBuilder = new StringBuilder();
        if (attributes != null) {
            attributes.attributesMap().forEach((key, value) -> {
                stringBuilder.append(" ")
                        .append(key)
                        .append("=\"")
                        .append(value)
                        .append("\"");
            });
        }
        return stringBuilder.toString();
    }
}