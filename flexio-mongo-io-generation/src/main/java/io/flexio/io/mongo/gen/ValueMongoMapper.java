package io.flexio.io.mongo.gen;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.codingmatters.value.objects.spec.PropertySpec;
import org.codingmatters.value.objects.spec.TypeKind;
import org.codingmatters.value.objects.spec.TypeToken;

import javax.lang.model.element.Modifier;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ValueMongoMapper {
    private final MapperConfiguration mapperConfig;

    public ValueMongoMapper(MapperConfiguration mapperConfig) {
        this.mapperConfig = mapperConfig;
    }

    public TypeSpec typeSpec() {
        TypeSpec.Builder result = TypeSpec.classBuilder(this.mapperConfig.mapperName())
                .addModifiers(Modifier.PUBLIC)
                .addMethod(this.toValue())
                .addMethod(this.toDocument())
                ;
        return result.build();
    }

    private MethodSpec toValue() {
        MethodSpec.Builder result = MethodSpec.methodBuilder("toValue")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Document.class, "document")
                .returns(this.mapperConfig.valueType());
        result.addStatement("$T.Builder builder = $T.builder()", this.mapperConfig.valueType(), this.mapperConfig.valueType());
        for (PropertySpec propertySpec : this.mapperConfig.valueSpec().propertySpecs()) {
            result.beginControlFlow("if(document.get($S) != null)", this.documentProperty(propertySpec));

            if(propertySpec.typeSpec().typeKind().equals(TypeKind.ENUM)) {
                this.enumToValue(result, propertySpec);
            } else if(propertySpec.typeSpec().typeKind().isValueObject()) {
                this.valueObjectToValue(result, propertySpec);
            } else {
                this.simplePropertyToValue(result, propertySpec);
            }

            result.endControlFlow();
        }

        result.addStatement("return builder.build()");

        return result.build();
    }

    private void enumToValue(MethodSpec.Builder result, PropertySpec propertySpec) {
        if(propertySpec.typeSpec().cardinality().isCollection()) {
            result.addStatement("$T<$T> $LDocumentValues = ($T)document.get($S)",
                    List.class,
                    String.class,
                    propertySpec.name(),
                    List.class,
                    this.documentProperty(propertySpec)
            );
            result.addStatement("$T[] $LValues = new $T[$LDocumentValues.size()]",
                    this.mapperConfig.propertySingleType(propertySpec),
                    propertySpec.name(),
                    this.mapperConfig.propertySingleType(propertySpec),
                    propertySpec.name()
            );
            result.beginControlFlow("for(int i = 0 ; i < $LValues.length ; i++)", propertySpec.name())
                    .beginControlFlow("for($T enumValue : $T.values())",
                            this.mapperConfig.propertySingleType(propertySpec),
                            this.mapperConfig.propertySingleType(propertySpec))
                        .beginControlFlow("if($LDocumentValues.get(i).toString().equalsIgnoreCase(enumValue.name()))", propertySpec.name())
                            .addStatement("$LValues[i] = enumValue", propertySpec.name())
                        .endControlFlow()
                    .endControlFlow()
                .endControlFlow();
            result.addStatement("builder.$L($LValues)", propertySpec.name(), propertySpec.name());
        } else {
            result.beginControlFlow("for($T enumValue : $T.values())",
                    this.mapperConfig.propertySingleType(propertySpec),
                    this.mapperConfig.propertySingleType(propertySpec))
                    .beginControlFlow("if(document.get($S).toString().equalsIgnoreCase(enumValue.name()))", propertySpec.name())
                    .addStatement("builder.$L(enumValue)", propertySpec.name())
                    .endControlFlow()
                    .endControlFlow();
        }
    }

    private void valueObjectToValue(MethodSpec.Builder result, PropertySpec propertySpec) {
        if(propertySpec.typeSpec().cardinality().isCollection()) {
            result.addStatement("$T<$T> values = new $T<>()", List.class, this.mapperConfig.propertySingleType(propertySpec), LinkedList.class);
            result.beginControlFlow("for($T doc : ($T<$T>)document.get($S))",
                    Document.class,
                    List.class, Document.class,
                    this.documentProperty(propertySpec))
                        .addStatement("values.add(new $T().toValue(doc))", this.mapperConfig.mongoMapperClassName(propertySpec))
                    .endControlFlow();
            result.addStatement("builder.$L(values)", propertySpec.name());
        } else {
            result.addStatement("builder.$L(new $T().toValue(($T)document.get($S)))",
                    propertySpec.name(),
                    this.mapperConfig.mongoMapperClassName(propertySpec),
                    Document.class,
                    this.documentProperty(propertySpec));
        }
    }

    private void simplePropertyToValue(MethodSpec.Builder result, PropertySpec propertySpec) {
        if(this.mapperConfig.isTemporalProperty(propertySpec)) {
            this.temporalPropertyToValue(result, propertySpec);
        } else if(this.isObjectId(propertySpec)) {
            this.objectIdToValue(result, propertySpec);
        } else {
            if(propertySpec.typeSpec().cardinality().isCollection()) {
                result
                        .beginControlFlow("if(document.get($S) instanceof $T)",
                                this.documentProperty(propertySpec),
                                Collection.class)
                        .addStatement("builder.$L(new $T.Builder().with(($T)document.get($S)).build())",
                                propertySpec.name(),
                                this.mapperConfig.collectionRawType(propertySpec),
                                Collection.class,
                                this.documentProperty(propertySpec))
                        .nextControlFlow("else")
                        .addStatement("builder.$L(new $T.Builder().with(document.get($S)).build())",
                                propertySpec.name(),
                                this.mapperConfig.collectionRawType(propertySpec),
                                this.documentProperty(propertySpec))
                        .endControlFlow()
                ;
            } else {
                result.addStatement("builder.$L(new $T(document.get($S).toString()))",
                        propertySpec.name(),
                        this.mapperConfig.propertyType(propertySpec),
                        this.documentProperty(propertySpec));
            }
        }
    }

    private void temporalPropertyToValue(MethodSpec.Builder result, PropertySpec propertySpec) {
        if(propertySpec.typeSpec().cardinality().isCollection()) {
            result.addStatement("$T<$T> $LElmts = new $T<>()",
                    LinkedList.class, this.mapperConfig.propertySingleType(propertySpec), propertySpec.name(), LinkedList.class);
            result.beginControlFlow("for($T elmt : ($T<$T>)document.get($S))",
                    Date.class, Collection.class, Date.class, this.documentProperty(propertySpec));
        } else {
            result.addStatement("$T elmt = ($T)document.get($S)",
                    Date.class, Date.class, this.documentProperty(propertySpec));
        }

        result.addStatement("$T $LZoned = $T.ofInstant(elmt.toInstant(), $T.of($S))",
                ZonedDateTime.class, propertySpec.name(), ZonedDateTime.class, ZoneId.class, "Z");
        result.addStatement("$T $LElmt = $LZoned.to$L()",
                this.mapperConfig.propertySingleType(propertySpec),
                propertySpec.name(),
                propertySpec.name(),
                ClassName.bestGuess(propertySpec.typeSpec().typeRef()).simpleName()
        );

        if(propertySpec.typeSpec().cardinality().isCollection()) {
            result.addStatement("$LElmts.add($LElmt)", propertySpec.name(), propertySpec.name());
            result.endControlFlow();
            result.addStatement("builder.$L($LElmts)", propertySpec.name(), propertySpec.name());
        } else {
            result.addStatement("builder.$L($LElmt)", propertySpec.name(), propertySpec.name());
        }
    }



    private void objectIdToValue(MethodSpec.Builder method, PropertySpec propertySpec) {
        if(propertySpec.typeSpec().cardinality().isCollection()) {
            method.addStatement("$T<$T> $LElmts = new $T<>()", LinkedList.class, String.class, propertySpec.name(), LinkedList.class)
                .beginControlFlow("for($T $LVal : ($T)document.get($S))", Object.class, propertySpec.name(), Collection.class, this.documentProperty(propertySpec));
        } else {
            method.addStatement("$T $LVal = document.get($S)", Object.class, propertySpec.name(), this.documentProperty(propertySpec));
        }

        method.addStatement("$T $LElmt = $LVal.toString()", String.class, propertySpec.name(), propertySpec.name());

        if(propertySpec.typeSpec().cardinality().isCollection()) {
            method.addStatement("$LElmts.add($LElmt)", propertySpec.name(), propertySpec.name())
                .endControlFlow()
                .addStatement("builder.$L($LElmts)", propertySpec.name(), propertySpec.name());
        } else {
            method.addStatement("builder.$L($LElmt)", propertySpec.name(), propertySpec.name());
        }
    }

    private MethodSpec toDocument() {
        MethodSpec.Builder mainMethod = MethodSpec.methodBuilder("toDocument")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(this.mapperConfig.valueType(), "value")
                .returns(Document.class);

        mainMethod.addStatement("$T document = new $T()", Document.class, Document.class);
        for (PropertySpec propertySpec : this.mapperConfig.valueSpec().propertySpecs()) {
            if(! this.isTransient(propertySpec)) {
                mainMethod.beginControlFlow("if(value.$L() != null)", propertySpec.name());
                if(propertySpec.typeSpec().typeKind().equals(TypeKind.ENUM)) {
                    this.enumToDocument(mainMethod, propertySpec);
                } else if (propertySpec.typeSpec().typeKind().isValueObject()) {
                    this.valueObjectToDocumentStatement(mainMethod, propertySpec);
                } else {
                    this.documentSimplePropertySetterStatement(mainMethod, propertySpec);
                }
                mainMethod.nextControlFlow("else")
                        .addStatement("document.put($S, null)", this.documentProperty(propertySpec))
                        .endControlFlow();
            }
        }
        mainMethod.addStatement("return document");

        return mainMethod.build();
    }

    private void enumToDocument(MethodSpec.Builder method, PropertySpec propertySpec) {
        method.beginControlFlow("if(value.$L() != null)", propertySpec.name());
        if(propertySpec.typeSpec().cardinality().isCollection()) {
            method
                    .addStatement("$T<$T> values = new $T<>()", List.class, String.class, LinkedList.class)
                    .beginControlFlow("for($T val : value.$L())",
                            this.mapperConfig.propertySingleType(propertySpec),
                            propertySpec.name())
                        .addStatement("values.add(val != null ? val.name() : null)")
                    .endControlFlow()
                    .addStatement("document.put($S, values)", this.documentProperty(propertySpec))
            ;
        } else {
            method.addStatement("document.put($S, value.$L().name())",
                this.documentProperty(propertySpec),
                propertySpec.name());
        }
        method.endControlFlow();
    }

    private void valueObjectToDocumentStatement(MethodSpec.Builder method, PropertySpec propertySpec) {
        if(propertySpec.typeSpec().cardinality().isCollection()) {
            method.addStatement("$T<$T> docs = new $T<>()", List.class, Document.class, LinkedList.class);
            method.beginControlFlow("for($T v : value.$L())", this.mapperConfig.propertySingleType(propertySpec), propertySpec.name())
                .addStatement("docs.add(new $T().toDocument(v))",
                        this.mapperConfig.mongoMapperClassName(propertySpec))
                .endControlFlow();
            method.addStatement("document.put($S, docs)", this.documentProperty(propertySpec));
        } else {
            method.addStatement("document.put($S, new $T().toDocument(value.$L()))",
                    this.documentProperty(propertySpec),
                    this.mapperConfig.mongoMapperClassName(propertySpec),
                    propertySpec.name());
        }
    }

    private void documentSimplePropertySetterStatement(MethodSpec.Builder method, PropertySpec propertySpec) {
        if(this.isObjectId(propertySpec)) {
            this.documentObjectIdPropertySetterStatement(method, propertySpec);
        } else if(this.mapperConfig.isTemporalProperty(propertySpec)) {
            this.documentTemporalPropertySetterStatement(method, propertySpec);
        } else {
            method.addStatement("document.put($S, value.$L())",
                    this.documentProperty(propertySpec),
                    propertySpec.name());
        }
    }

    private void documentObjectIdPropertySetterStatement(MethodSpec.Builder method, PropertySpec propertySpec) {
        if(propertySpec.typeSpec().cardinality().isCollection()) {
            method.addStatement("$T $LElmts = new $T()", LinkedList.class, propertySpec.name(), LinkedList.class);
            method.beginControlFlow("for($T $LVal : value.$L())", String.class, propertySpec.name(), propertySpec.name());
        } else {
            method.addStatement("$T $LVal = value.$L()", String.class, propertySpec.name(), propertySpec.name());
        }

        method
                .addStatement("$T $LElmt", Object.class, propertySpec.name())
                .beginControlFlow("if($T.isValid($LVal))",
                        ObjectId.class, propertySpec.name())
                .addStatement("$LElmt = new $T($LVal)",
                        propertySpec.name(),
                        ObjectId.class,
                        propertySpec.name())
                .nextControlFlow("else")
                .addStatement("$LElmt = $LVal",
                        propertySpec.name(), propertySpec.name())
                .endControlFlow();

        if(propertySpec.typeSpec().cardinality().isCollection()) {
            method.addStatement("$LElmts.add($LElmt)", propertySpec.name(), propertySpec.name());
            method.endControlFlow();
            method.addStatement("document.put($S, $LElmts)", this.documentProperty(propertySpec), propertySpec.name());
        } else {
            method.addStatement("document.put($S, $LElmt)", this.documentProperty(propertySpec), propertySpec.name());
        }
    }

    private void documentTemporalPropertySetterStatement(MethodSpec.Builder method, PropertySpec propertySpec) {
        if(propertySpec.typeSpec().cardinality().isCollection()) {
            method.addStatement("$T<$T> $LValues = new $T<>()",
                    LinkedList.class, Date.class, propertySpec.name(), LinkedList.class)
                    .beginControlFlow("for($T elmt : value.$L())",
                            this.mapperConfig.propertySingleType(propertySpec), propertySpec.name());
        } else {
            method.addStatement("$T elmt = value.$L()",
                    this.mapperConfig.propertySingleType(propertySpec), propertySpec.name());
        }

        if(TypeToken.TZ_DATE_TIME.getImplementationType().equals(propertySpec.typeSpec().typeRef())) {
            method.addStatement("$T $LValue = $T.from(elmt.toInstant())",
                    Date.class, propertySpec.name(), Date.class
            );
        } else if(TypeToken.DATE_TIME.getImplementationType().equals(propertySpec.typeSpec().typeRef())) {
            method.addStatement("$T $LValue = $T.from(elmt.atZone($T.of($S)).toInstant())",
                    Date.class, propertySpec.name(), Date.class, ZoneId.class, "Z"
            );
        } else if(TypeToken.DATE.getImplementationType().equals(propertySpec.typeSpec().typeRef())) {
            method.addStatement("$T $LValue = $T.from(elmt.atStartOfDay().atZone($T.of($S)).toInstant())",
                    Date.class, propertySpec.name(), Date.class, ZoneId.class, "Z"
            );
        } else if(TypeToken.TIME.getImplementationType().equals(propertySpec.typeSpec().typeRef())) {
            throw new RuntimeException("not yet implemented temporal type : " + propertySpec.typeSpec().typeRef());
        } else {
            throw new RuntimeException("unknown temporal type : " + propertySpec.typeSpec().typeRef());
        }

        if(propertySpec.typeSpec().cardinality().isCollection()) {
            method
                    .addStatement("$LValues.add($LValue)",
                            propertySpec.name(), propertySpec.name())
                    .endControlFlow()
                    .addStatement("document.put($S, $LValues)",
                            this.documentProperty(propertySpec), propertySpec.name());
        } else {
            method.addStatement("document.put($S, $LValue)",
                    this.documentProperty(propertySpec), propertySpec.name());
        }

    }

    private String documentProperty(PropertySpec propertySpec) {
        for (String hint : propertySpec.hints("mongo:")) {
            if(hint.startsWith("mongo:field(")) {
                return hint.substring("mongo:field(".length(), hint.length() - 1);
            }
        }
        return propertySpec.name();
    }

    private boolean isObjectId(PropertySpec propertySpec) {
        for (String hint : propertySpec.hints("mongo:")) {
            if(hint.equals("mongo:object-id")) {
                return true;
            }
        }
        return false;
    }

    private boolean isTransient(PropertySpec propertySpec) {
        for (String hint : propertySpec.hints("storage:")) {
            if(hint.equals("storage:transient")) {
                return true;
            }
        }
        return false;
    }


}
