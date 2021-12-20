package io.flexio.svg.generator.generator.tag;

import io.flexio.svg.generator.generator.tag.element.svg.Group;
import io.flexio.svg.generator.generator.tag.property.Classifiable;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

public class ClassifiableTest {
    private static final String[] classes = {"classe1", "classe2", "classe3"};

    @Test
    public void OneClass() {
        Classifiable c = new Group().classSelector(classes[0]);
        assertThat(c.attributesMap(), hasEntry("class", classes[0]));
    }

    @Test
    public void multipleClasses() {
        Classifiable c = new Group().classSelector(classes[0]);
        assertThat(c.attributesMap(), hasEntry("class", classes[0]));

        c.classSelector(classes[1], classes[2]);
        assertThat(c.attributesMap(), hasEntry("class", String.join(" ", classes)));
    }
}