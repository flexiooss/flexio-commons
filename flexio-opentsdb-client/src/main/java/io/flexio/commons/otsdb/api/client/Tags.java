package io.flexio.commons.otsdb.api.client;

import org.codingmatters.value.objects.values.ObjectValue;
import org.codingmatters.value.objects.values.PropertyValue;

import java.util.LinkedHashMap;

public class Tags {

    public static Tags tags() {
        return new Tags();
    }

    public static Tags from(Tags tags) {
        return new Tags(new LinkedHashMap<>(tags.values));
    }

    private final LinkedHashMap<String, String> values;

    private Tags() {
        values = new LinkedHashMap<>();
    }

    private Tags(LinkedHashMap<String, String> values) {
        this.values = values;
    }

    public Tags tag(String name, String value) {
        this.values.put(name, value);
        return this;
    }

    public ObjectValue.Builder value() {
        ObjectValue.Builder result = ObjectValue.builder();
        this.values.forEach((name, value) -> result.property(name, PropertyValue.builder().stringValue(value).build()));
        return result;
    }

}
