package io.flexio.svg.generator.generator.tag.element;

import io.flexio.svg.generator.generator.Attribute;

import java.util.Base64;

@FunctionalInterface
public interface SVGElement<A extends SVGElement> extends Attribute {
    @SuppressWarnings("unchecked")
    default A embed(String property, String data) {
        if (property != null && ! property.trim().isEmpty() && data != null) {
            attributesMap().put(property, encodeBase64(data));
        }
        return (A) this;
    }

    private String encodeBase64(String data){
        return new String(Base64.getEncoder().encode(data.getBytes()));
    }
}
