package io.flexio.commons.otsdb.api.client;

import org.codingmatters.value.objects.values.ObjectValue;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TagsTest {
    @Test
    public void name() {
        assertThat(
                Tags.tags()
                        .tag("n1", "v1")
                        .tag("n2", "v2")
                        .tag("n3", "v3")
                        .value().build(),
                is(ObjectValue.builder()
                        .property("n1", v -> v.stringValue("v1"))
                        .property("n2", v -> v.stringValue("v2"))
                        .property("n3", v -> v.stringValue("v3"))
                        .build())
        );
    }
}