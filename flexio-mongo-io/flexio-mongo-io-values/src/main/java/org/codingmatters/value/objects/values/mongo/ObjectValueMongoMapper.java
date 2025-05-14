package org.codingmatters.value.objects.values.mongo;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.codingmatters.value.objects.values.ObjectValue;
import org.codingmatters.value.objects.values.PropertyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ObjectValueMongoMapper {
    static private final Logger log = LoggerFactory.getLogger(ObjectValueMongoMapper.class);

    public static final LocalDate START_OF_TIME = LocalDate.ofYearDay(1970, 1);

    public ObjectValue toValue(Document document) {
        ObjectValue.Builder valueBuilder = ObjectValue.builder();

        for (String propertyName : document.keySet()) {
            this.addPropertyToValue(valueBuilder, propertyName, document.get(propertyName));
        }

        return valueBuilder.build();
    }

    private Object objectId(String id) {
        return ObjectId.isValid(id) ? new ObjectId(id) : id;
    }

    private void addPropertyToValue(ObjectValue.Builder builder, String name, Object value) {
        try {
            if(value instanceof List) {
                List<PropertyValue.Value> values = new LinkedList<>();
                PropertyValue.Type propertyType = null;
                for (Object element : ((List) value)) {
                    PropertyValue.Type elementType;
                    if (element instanceof Document) {
                        elementType = PropertyValue.Type.OBJECT;
                    } else if (element instanceof Date) {
                        elementType = PropertyValue.Type.DATETIME;
                    } else {
                        elementType = PropertyValue.Type.fromObject(element);
                    }
                    if (propertyType == null) {
                        propertyType = elementType;
                    }

                    PropertyValue.Builder elementBuilder = PropertyValue.builder();
                    if(element instanceof Document) {
                        elementType.set(elementBuilder, this.toValue((Document) element));
                    } else if (element instanceof Date) {
                        LocalDateTime ldt = LocalDateTime.ofInstant(((Date) element).toInstant(), ZoneOffset.UTC);
                        elementType.set(elementBuilder, ldt);
                    } else {
                        propertyType.set(elementBuilder, element);
                    }
                    values.add(elementBuilder.buildValue());
                }

                builder.property(name, PropertyValue.multiple(propertyType, values.toArray(new PropertyValue.Value[((List) value).size()])));
            } else {
                if(value instanceof Document) {
                    builder.property(name, p -> p.objectValue(this.toValue((Document) value)));
                } else if(value instanceof ObjectId) {
                    builder.property(name, p -> p.stringValue(((ObjectId) value).toString()));
                } else if(value instanceof Date) {
                    PropertyValue.Type type = PropertyValue.Type.DATETIME;
                    LocalDateTime ldt = LocalDateTime.ofInstant(((Date) value).toInstant(), ZoneOffset.UTC);
                    builder.property(name, v -> type.set(v, ldt));
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
        if (value == null) {
            return null;
        }
        Document document = new Document();
        for (String propertyName : value.propertyNames()) {
            PropertyValue property = value.property(propertyName);
            if (property == null) {
                document.put(propertyName, null);
            } else {
                if (PropertyValue.Cardinality.SINGLE.equals(property.cardinality())) {
                    if(propertyName.equals("_id") && PropertyValue.Type.STRING.equals(property.type())) {
                        document.put("_id", this.objectId(property.single().stringValue()));
                    } else {
                        this.addSinglePropertyToDocument(document, propertyName, property.single());
                    }
                } else {
                    this.addMultiplePropertyToDocument(document, propertyName, property.multiple());
                }
            }
        }
        return document;
    }

    private void addSinglePropertyToDocument(Document document, String name, PropertyValue.Value value) {
        if (value != null && !value.isNull()) {
            if (value.type().equals(PropertyValue.Type.OBJECT)) {
                document.put(name, this.toDocument(value.objectValue()));
            } else if (value.type().equals(PropertyValue.Type.DATE)) {
                document.put(name, Date.from(value.dateValue().atStartOfDay().toInstant(ZoneOffset.UTC)));
            } else if (value.type().equals(PropertyValue.Type.TIME)) {
                document.put(name, Date.from(value.timeValue().atDate(START_OF_TIME).toInstant(ZoneOffset.UTC)));
            } else if (value.type().equals(PropertyValue.Type.DATETIME)) {
                document.put(name, Date.from(value.datetimeValue().toInstant(ZoneOffset.UTC)));
            } else {
                document.put(name, value.rawValue());
            }
        }else{
            document.put(name, null);
        }
    }

    private void addMultiplePropertyToDocument(Document document, String name, PropertyValue.Value[] values) {
        List v = new LinkedList();
        for (PropertyValue.Value value : values) {
            if (value != null && !value.isNull()) {
                if (PropertyValue.Type.OBJECT.equals(value.type())) {
                    v.add(this.toDocument(value.objectValue()));
                } else if (PropertyValue.Type.DATETIME.equals(value.type())) {
                    v.add(Date.from(value.datetimeValue().toInstant(ZoneOffset.UTC)));
                } else if (PropertyValue.Type.DATE.equals(value.type())) {
                    v.add(Date.from(value.dateValue().atStartOfDay().toInstant(ZoneOffset.UTC)));
                } else if (PropertyValue.Type.TIME.equals(value.type())) {
                    v.add(Date.from(value.timeValue().atDate(START_OF_TIME).toInstant(ZoneOffset.UTC)));
                } else {
                    v.add(value.rawValue());
                }
            } else {
                v.add(null);
            }
        }

        document.put(name, v);
    }
}
