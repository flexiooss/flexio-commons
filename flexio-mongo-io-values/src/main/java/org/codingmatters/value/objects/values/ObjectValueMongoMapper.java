package org.codingmatters.value.objects.values;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class ObjectValueMongoMapper {
    static private final Logger log = LoggerFactory.getLogger(ObjectValueMongoMapper.class);


    public ObjectValue toValue(Document document) {
        ObjectValue.Builder valueBuilder = ObjectValue.builder();

        for (String propertyName : document.keySet()) {
            this.addProperty(valueBuilder, propertyName, document.get(propertyName));
        }

        return valueBuilder.build();
    }

    private void addProperty(ObjectValue.Builder builder, String name, Object value) {
        try {
            if(value instanceof List) {
                List<PropertyValue.Value> values = new LinkedList<>();
                PropertyValue.Type propertyType = null;
                for (Object element : ((List) value)) {
                    PropertyValue.Type elementType = element instanceof Document ?
                            PropertyValue.Type.OBJECT :
                            PropertyValue.Type.fromObject(element);
                    if (propertyType == null) {
                        propertyType = elementType;
                    }

                    PropertyValue.Builder elementBuilder = PropertyValue.builder();
                    if(element instanceof Document) {
                        elementType.set(elementBuilder, this.toValue((Document) element));
                    } else {
                        propertyType.set(elementBuilder, element);
                    }
                    values.add(elementBuilder.buildValue());
                }

                builder.property(name, PropertyValue.multiple(propertyType, values.toArray(new PropertyValue.Value[((List) value).size()])));
            } else {
                if(value instanceof Document) {
                    builder.property(name, p -> p.objectValue(this.toValue((Document) value)));
                } else {
                    PropertyValue.Type type = PropertyValue.Type.fromObject(value);
                    builder.property(name, v -> type.set(v, value));
                }
            }
        } catch (PropertyValue.Type.UnsupportedTypeException e) {
            log.error("failed to read mongo property " + name + " with value " + value, e);
        }
    }

    public Document toDocument(ObjectValue value) {
        Document document = new Document();
        for (String propertyName : value.propertyNames()) {
            PropertyValue property = value.property(propertyName);
            if(PropertyValue.Cardinality.SINGLE.equals(property.cardinality())) {
                this.addSingleProperty(document, propertyName, property.single());
            } else {
                this.addMultipleProperty(document, propertyName, property.multiple());
            }
        }
        return document;
    }

    private void addSingleProperty(Document document, String name, PropertyValue.Value value) {
        if(value.type().equals(PropertyValue.Type.OBJECT)) {
            document.put(name, this.toDocument(value.objectValue()));
        } else {
            document.put(name, value.rawValue());
        }
    }

    private void addMultipleProperty(Document document, String name, PropertyValue.Value[] values) {
        List v = new LinkedList();
        for (PropertyValue.Value value : values) {
            if(PropertyValue.Type.OBJECT.equals(value.type())) {
                v.add(this.toDocument(value.objectValue()));
            } else {
                v.add(value.rawValue());
            }
        }

        document.put(name, v);
    }
}
