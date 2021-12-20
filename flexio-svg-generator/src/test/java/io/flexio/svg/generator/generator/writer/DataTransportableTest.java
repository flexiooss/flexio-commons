package io.flexio.svg.generator.generator.writer;

import io.flexio.svg.generator.generator.tag.element.SVGElement;
import org.junit.Before;
import org.junit.Test;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DataTransportableTest {
    private final static String originalData = "DATA_TO_BE_ENCODED";
    private final static String encodedData = new String(Base64.getEncoder().encode(originalData.getBytes()));

    private Map<String, String> attributes;
    private SVGElement markup;

    @Before
    public void setup(){
        attributes = new HashMap<>();
        markup = () -> attributes;
    }


    @Test
    public void givenNullProperty__WhenEmbed__DoesntAddToAttribute() {
        markup.embed(null, "data");

        assertThat(attributes.values(), emptyIterable());
    }

    @Test
    public void givenBlankProperty__WhenEmbed__DoesntAddToAttribute() {
        markup.embed(" ", "data");

        assertThat(attributes.values(), emptyIterable());
    }

    @Test
    public void givenEmptyProperty__WhenEmbed__DoesntAddToAttribute() {
        markup.embed("", "data");

        assertThat(attributes.values(), emptyIterable());
    }

    @Test
    public void givenNullData__WhenEmbed__DoesntAddToAttribute() {
        markup.embed("data-data", null);

        assertThat(attributes.values(), emptyIterable());
    }

    @Test
    public void givenEmptyData__WhenEmbed__AddToAttribute() {
        markup.embed("data-data", "");

        assertThat(attributes.values(), hasSize(1));
        assertThat(attributes, hasEntry("data-data", ""));
    }

    @Test
    public void givenOriginalData__WhenEmbed__AddToAttributeEncoded() {
        markup.embed("data-data", originalData);

        assertThat(attributes.values(), hasSize(1));
        assertThat(attributes, hasEntry("data-data", encodedData));
    }
}
