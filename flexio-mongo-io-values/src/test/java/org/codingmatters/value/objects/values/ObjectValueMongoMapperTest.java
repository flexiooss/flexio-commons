package org.codingmatters.value.objects.values;

import org.bson.Document;
import org.codingmatters.value.objects.values.mongo.ObjectValueMongoMapper;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ObjectValueMongoMapperTest {

    private ObjectValueMongoMapper mapper = new ObjectValueMongoMapper();

    @Test
    public void stringProperty() throws Exception {
        ObjectValue initial = ObjectValue.builder().property("prop", p -> p.stringValue("str")).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(doc.get("prop"), is("str"));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").single().stringValue(), is("str"));
    }

    @Test
    public void longProperty() throws Exception {
        ObjectValue initial = ObjectValue.builder().property("prop", p -> p.longValue(12L)).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(doc.get("prop"), is(12L));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").single().longValue(), is(12L));
    }

    @Test
    public void doubleProperty() throws Exception {
        ObjectValue initial = ObjectValue.builder().property("prop", p -> p.doubleValue(12.0)).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(doc.get("prop"), is(12.0));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").single().doubleValue(), is(12.0));
    }

    @Test
    public void booleanProperty() throws Exception {
        ObjectValue initial = ObjectValue.builder().property("prop", p -> p.booleanValue(true)).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(doc.get("prop"), is(true));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").single().booleanValue(), is(true));
    }

    @Test
    public void bytesProperty() throws Exception {
        ObjectValue initial = ObjectValue.builder().property("prop", p -> p.bytesValue("HELLO".getBytes())).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(doc.get("prop"), is("HELLO".getBytes()));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").single().bytesValue(), is("HELLO".getBytes()));
    }

    @Test
    public void objectProperty() throws Exception {
        ObjectValue initial = ObjectValue.builder().property("prop", p -> p
                .objectValue(ObjectValue.builder().property("embedded", e -> e.stringValue("str")))
        ).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(((Document)doc.get("prop")).get("embedded"), is("str"));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").single().objectValue().property("embedded").single().stringValue(), is("str"));
    }

    @Test
    public void stringArrayProperty() throws Exception {
        ObjectValue initial = ObjectValue.builder().property("prop", PropertyValue.multiple(PropertyValue.Type.STRING,
                p -> p.stringValue("str")))
                .build();
        Document doc = this.mapper.toDocument(initial);
        assertThat(doc.get("prop"), is(Arrays.asList("str")));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").multiple(), arrayContaining(PropertyValue.builder().stringValue("str").buildValue()));
    }

    @Test
    public void longArrayProperty() throws Exception {
        ObjectValue initial = ObjectValue.builder().property("prop", PropertyValue.multiple(PropertyValue.Type.LONG,
                p -> p.longValue(12L)))
                .build();
        Document doc = this.mapper.toDocument(initial);
        assertThat(doc.get("prop"), is(Arrays.asList(12L)));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").multiple(), arrayContaining(PropertyValue.builder().longValue(12L).buildValue()));
    }

    @Test
    public void objectArrayProperty() throws Exception {
        ObjectValue initial = ObjectValue.builder().property("prop", PropertyValue.multiple(PropertyValue.Type.OBJECT, p -> p
                .objectValue(ObjectValue.builder().property("embedded", e -> e.stringValue("str")))
                )
        ).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(((Document)((List)doc.get("prop")).get(0)).get("embedded"), is("str"));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").multiple()[0].objectValue().property("embedded").single().stringValue(), is("str"));
    }
}