package io.flexio.svg.generator.generator;

import io.flexio.svg.generator.generator.tag.property.Classifiable;
import io.flexio.svg.generator.generator.tag.property.Identifiable;
import io.flexio.svg.generator.generator.tag.property.Stylable;
import io.flexio.svg.generator.generator.writer.ElementWriter;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Element<E extends Element> implements Attribute,
        Identifiable<E>, Classifiable<E>, Stylable<E>
{
    private final Map<String, String> attributes = new LinkedHashMap<>();

    public abstract String name();


    @SuppressWarnings("unchecked")
    @Override
    public final E attributes(String name, String value) {
        attributesMap().put(name, value);
        return (E) this;
    }

    @Override
    public Map<String, String> attributesMap() {
        return attributes;
    }
    
    public abstract void open(ElementWriter writer);
}