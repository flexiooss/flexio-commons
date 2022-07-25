package org.codingmatters.value.objects.values;

import org.bson.Document;
import org.codingmatters.value.objects.values.mongo.ObjectValueMongoMapper;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.codingmatters.poom.services.tests.DateMatchers.around;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
    public void dateProperty() {
        LocalDate now = LocalDate.now();
        ObjectValue initial = ObjectValue.builder().property("prop", p -> p.dateValue(now)).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(doc.get("prop"), is(Date.from(now.atStartOfDay().toInstant(ZoneOffset.UTC))));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").type(), is(PropertyValue.Type.DATETIME));
        assertThat(value.property("prop").single().datetimeValue(), is(now.atStartOfDay()));
    }

    @Test
    public void timeProperty() {
        LocalTime now = LocalTime.now();
        ObjectValue initial = ObjectValue.builder().property("prop", p -> p.timeValue(now)).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(doc.get("prop"), is(Date.from(now.atDate(LocalDate.ofYearDay(1970, 1)).toInstant(ZoneOffset.UTC))));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").type(), is(PropertyValue.Type.DATETIME));
        assertThat(value.property("prop").single().datetimeValue(), is(around(now.atDate(LocalDate.ofYearDay(1970, 1)))));
    }

    @Test
    public void datetimeProperty() {
        LocalDateTime now = LocalDateTime.now();
        ObjectValue initial = ObjectValue.builder().property("prop", p -> p.datetimeValue(now)).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(doc.get("prop"), is(Date.from(now.toInstant(ZoneOffset.UTC))));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").type(), is(PropertyValue.Type.DATETIME));
        assertThat(value.property("prop").single().datetimeValue(), is(around(now)));
    }

    @Test
    public void objectProperty() throws Exception {
        ObjectValue initial = ObjectValue.builder().property("prop", p -> p
                .objectValue(ObjectValue.builder().property("embedded", e -> e.stringValue("str")))
        ).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(((Document) doc.get("prop")).get("embedded"), is("str"));

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
        assertThat(((Document) ((List) doc.get("prop")).get(0)).get("embedded"), is("str"));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").multiple()[0].objectValue().property("embedded").single().stringValue(), is("str"));
    }

    @Test
    public void datetimeArrayProperty() {
        LocalDateTime now = LocalDateTime.now();
        ObjectValue initial = ObjectValue.builder().property("prop", PropertyValue.multiple(PropertyValue.Type.DATETIME,
                p -> p.datetimeValue(now),
                p -> p.datetimeValue(now.plusHours(1L))
        )).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(doc.get("prop"), is(Arrays.asList(
                        Date.from(now.toInstant(ZoneOffset.UTC)),
                        Date.from(now.plusHours(1L).toInstant(ZoneOffset.UTC))
                )
        ));
        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").multiple()[0].type(), is(PropertyValue.Type.DATETIME));
        assertThat(value.property("prop").multiple()[0].datetimeValue(), is(around(now)));

        assertThat(value.property("prop").multiple()[0].type(), is(PropertyValue.Type.DATETIME));
        assertThat(value.property("prop").multiple()[1].datetimeValue(), is(around(now.plusHours(1L))));
    }

    @Test
    public void dateArrayProperty() {
        LocalDate now = LocalDate.now();
        ObjectValue initial = ObjectValue.builder().property("prop", PropertyValue.multiple(PropertyValue.Type.DATE,
                p -> p.dateValue(now),
                p -> p.dateValue(now.plusDays(1L))
        )).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(doc.get("prop"), is(Arrays.asList(
                        Date.from(now.atStartOfDay().toInstant(ZoneOffset.UTC)),
                        Date.from(now.atStartOfDay().plusDays(1L).toInstant(ZoneOffset.UTC))
                )
        ));

        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").multiple()[0].type(), is(PropertyValue.Type.DATETIME));
        assertThat(value.property("prop").multiple()[0].datetimeValue(), is(now.atStartOfDay()));

        assertThat(value.property("prop").multiple()[1].type(), is(PropertyValue.Type.DATETIME));
        assertThat(value.property("prop").multiple()[1].datetimeValue(), is(now.plusDays(1L).atStartOfDay()));
    }

    @Test
    public void timeArrayProperty() {
        LocalTime now = LocalTime.now();
        ObjectValue initial = ObjectValue.builder().property("prop", PropertyValue.multiple(PropertyValue.Type.TIME,
                p -> p.timeValue(now),
                p -> p.timeValue(now.plusHours(1L))
        )).build();

        Document doc = this.mapper.toDocument(initial);
        assertThat(doc.get("prop"), is(Arrays.asList(
                        Date.from(now.atDate(LocalDate.ofYearDay(1970, 1)).toInstant(ZoneOffset.UTC)),
                        Date.from(now.atDate(LocalDate.ofYearDay(1970, 1)).plusHours(1L).toInstant(ZoneOffset.UTC))
                )
        ));
        ObjectValue value = this.mapper.toValue(doc);
        assertThat(value.property("prop").multiple()[0].type(), is(PropertyValue.Type.DATETIME));
        assertThat(value.property("prop").multiple()[0].datetimeValue(), is(around(now.atDate(LocalDate.ofYearDay(1970, 1)))));

        assertThat(value.property("prop").multiple()[0].type(), is(PropertyValue.Type.DATETIME));
        assertThat(value.property("prop").multiple()[1].datetimeValue(), is(around(now.plusHours(1L).atDate(LocalDate.ofYearDay(1970, 1)))));
    }

    @Test
    public void nullSubObjectProperty() {
        ObjectValue objectValue = ObjectValue.builder()
                .property("null-without-type", PropertyValue.builder().build())
                .property("null-with-type-object", PropertyValue.builder().objectValue((ObjectValue) null).build())
                .property("null-date-withValue", PropertyValue.builder().datetimeValue(null).build())
                .property("null-date", PropertyValue.builder().build())
                .build();
        Document doc = this.mapper.toDocument(objectValue);

        assertThat(doc.get("null-without-type"), is(nullValue()));
        assertThat(doc.containsKey("null-without-type"), is(true));
        assertThat(doc.get("null-with-type-object"), is(nullValue()));
        assertThat(doc.containsKey("null-with-type-object"), is(true));
        assertThat(doc.get("null-date-withValue"), is(nullValue()));
        assertThat(doc.getString("null-date-withValue"), is(nullValue()));
        assertThat(doc.get("null-date"), is(nullValue()));
        assertThat(doc.getString("null-date"), is(nullValue()));
    }

}