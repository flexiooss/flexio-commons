package io.flexio.commons.otsdb.api.client;

import io.flexio.commons.otsdb.api.types.DataPoint;
import org.codingmatters.value.objects.values.ObjectValue;
import org.codingmatters.value.objects.values.PropertyValue;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Tags {
    public static Tags tags() {
        return new Tags();
    }

    private final LinkedHashMap<String, String> values = new LinkedHashMap<>();

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
