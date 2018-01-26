package io.flexio.io.mongo.gen;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.codingmatters.tests.compile.CompiledCode;
import org.codingmatters.tests.compile.FileHelper;
import org.codingmatters.tests.compile.helpers.ClassLoaderHelper;
import org.codingmatters.tests.compile.helpers.helpers.ObjectHelper;
import org.codingmatters.value.objects.generation.SpecCodeGenerator;
import org.codingmatters.value.objects.spec.PropertyCardinality;
import org.codingmatters.value.objects.spec.Spec;
import org.codingmatters.value.objects.spec.TypeKind;
import org.codingmatters.value.objects.spec.TypeToken;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;

import static org.codingmatters.tests.reflect.ReflectMatchers.aPublic;
import static org.codingmatters.value.objects.spec.AnonymousValueSpec.anonymousValueSpec;
import static org.codingmatters.value.objects.spec.PropertySpec.property;
import static org.codingmatters.value.objects.spec.PropertyTypeSpec.type;
import static org.codingmatters.value.objects.spec.ValueSpec.valueSpec;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MongoMapperGeneratorTest {

    @Rule
    public TemporaryFolder dir = new TemporaryFolder();

    @Rule
    public FileHelper fileHelper = new FileHelper();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void mapperSignature() throws Exception {
        ClassLoaderHelper classes = this.generate(
                Spec.spec().addValue(valueSpec().name("Test").build()).build()
        );

        assertThat(
                classes.get("org.generated.mongo.TestMongoMapper").get(),
                is(aPublic().class_()
                        .with(aPublic().method().named("toValue")
                                .withParameters(Document.class)
                                .returning(classes.get("org.generated.Test").get())
                        )
                        .with(aPublic().method().named("toDocument")
                                .withParameters(classes.get("org.generated.Test").get())
                                .returning(Document.class)
                        )
                )
        );
    }

    @Test
    public void noProperty() throws Exception {
        ClassLoaderHelper classes = this.generate(
                Spec.spec().addValue(valueSpec().name("Test").build()).build()
        );
        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        Object value = classes.get("org.generated.Test").call("builder").call("build").get();
        Document doc = (Document) mapper.call("toDocument", classes.get("org.generated.Test").get()).with(value).get();
        assertThat(
                doc,
                is(new Document())
        );

        assertThat(
                mapper.call("toValue", Document.class).with(doc).get(),
                is(value)
        );

    }

    @Test
    public void stringProperty() throws Exception {
        ClassLoaderHelper classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("p").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName()))))
                .build());

        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        Object value = classes.get("org.generated.Test").call("builder")
                .call("p", String.class).with("value")
                .call("build").get();
        System.out.println("value=" + value);
        Document doc = (Document) mapper.call("toDocument", classes.get("org.generated.Test").get()).with(value).get();
        assertThat(
                doc,
                is(Document.parse("{\"p\": \"value\"}"))
        );

        assertThat(
                mapper.call("toValue", Document.class).with(doc).get(),
                is(value)
        );
    }

    @Test
    public void automaticCastingOfSimpleTypes() throws Exception {
        ClassLoaderHelper classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("p").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName()))))
                .build());

        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        Document doc = new Document();
        doc.put("p", new Long(12));


        assertThat(mapper.call("toValue", Document.class).with(doc).as("org.generated.Test").call("p").get(), is("12"));
    }


    @Test
    public void stringArrayProperty() throws Exception {
        ClassLoaderHelper classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("p").type(type().cardinality(PropertyCardinality.LIST).typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName()))))
                .build());

        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        Object value = classes.get("org.generated.Test").call("builder")
                .call("p", String[].class).with(new Object[] {new String [] {"v1", "v2"}})
                .call("build").get();
        Document doc = (Document) mapper.call("toDocument", classes.get("org.generated.Test").get()).with(value).get();
        assertThat(
                doc,
                is(Document.parse("{\"p\": [\"v1\", \"v2\"]}"))
        );

        assertThat(
                mapper.call("toValue", Document.class).with(doc).get(),
                is(value)
        );
    }

    @Test
    public void stringArrayProperty_whenDocumentPropertyIsSingle() throws Exception {
        ClassLoaderHelper classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("p").type(type().cardinality(PropertyCardinality.LIST).typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName()))))
                .build());

        this.fileHelper.printFile(this.dir.getRoot(), "TestMongoMapper.java");

        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        Object value = classes.get("org.generated.Test").call("builder")
                .call("p", String[].class).with(new Object[] {new String [] {"v1"}})
                .call("build").get();

        assertThat(
                mapper.call("toValue", Document.class).with(Document.parse("{\"p\": \"v1\"}")).get(),
                is(value)
        );
    }


    @Test
    public void embeddedProperty() throws Exception {
        ClassLoaderHelper classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("p").type(type().typeKind(TypeKind.EMBEDDED)
                                .embeddedValueSpec(anonymousValueSpec()
                                        .addProperty(property().name("p").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName()))))
                        )))
                .build());

        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        Object pValue = classes.get("org.generated.test.P").call("builder")
                .call("p", String.class).with("value")
                .call("build").get();

        Object value = classes.get("org.generated.Test").call("builder")
                .call("p", classes.get("org.generated.test.P").get()).with(pValue)
                .call("build").get();
        System.out.println("value=" + value);
        Document doc = (Document) mapper.call("toDocument", classes.get("org.generated.Test").get()).with(value).get();
        assertThat(
                doc,
                is(Document.parse("{\"p\": {\"p\": \"value\"}}"))
        );

        assertThat(
                mapper.call("toValue", Document.class).with(doc).get(),
                is(value)
        );
    }

    @Test
    public void embeddedListProperty() throws Exception {
        ClassLoaderHelper classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("p").type(type().typeKind(TypeKind.EMBEDDED).cardinality(PropertyCardinality.LIST)
                                .embeddedValueSpec(anonymousValueSpec()
                                        .addProperty(property().name("p").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName()))))
                        )))
                .build());
        this.fileHelper.printFile(this.dir.getRoot(), "TestMongoMapper.java");

        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        Object pValue1 = classes.get("org.generated.test.P").call("builder")
                .call("p", String.class).with("v1")
                .call("build").get();
        Object pValue2 = classes.get("org.generated.test.P").call("builder")
                .call("p", String.class).with("v2")
                .call("build").get();

        Object value = classes.get("org.generated.Test").call("builder")
                .call("p", classes.get("org.generated.test.P").array().get())
                    .with(classes.get("org.generated.test.P").array().newArray(pValue1, pValue2).get())
                .call("build").get();
        System.out.println("value=" + value);

        Document doc = (Document) mapper.call("toDocument", classes.get("org.generated.Test").get()).with(value).get();
        assertThat(
                doc,
                is(Document.parse("{\"p\": [{\"p\": \"v1\"}, {\"p\": \"v2\"}]}"))
        );

        assertThat(
                mapper.call("toValue", Document.class).with(doc).get(),
                is(value)
        );
    }


    @Test
    public void inSpecValueObjectProperty() throws Exception {
        ClassLoaderHelper classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Ref")
                        .addProperty(property().name("p").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName()))))
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("p").type(type().typeKind(TypeKind.IN_SPEC_VALUE_OBJECT).typeRef("Ref")
                        )))
                .build());

        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        Object pValue = classes.get("org.generated.Ref").call("builder")
                .call("p", String.class).with("value")
                .call("build").get();

        Object value = classes.get("org.generated.Test").call("builder")
                .call("p", classes.get("org.generated.Ref").get()).with(pValue)
                .call("build").get();
        System.out.println("value=" + value);
        Document doc = (Document) mapper.call("toDocument", classes.get("org.generated.Test").get()).with(value).get();
        assertThat(
                doc,
                is(Document.parse("{\"p\": {\"p\": \"value\"}}"))
        );

        assertThat(
                mapper.call("toValue", Document.class).with(doc).get(),
                is(value)
        );
    }

    @Test
    public void mongoFieldHint() throws Exception {
        ClassLoaderHelper classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("p")
                                .hints(new HashSet<>(Arrays.asList("mongo:field(prop)")))
                                .type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName()))))
                .build());

        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        Object value = classes.get("org.generated.Test").call("builder")
                .call("p", String.class).with("value")
                .call("build").get();
        System.out.println("value=" + value);
        Document doc = (Document) mapper.call("toDocument", classes.get("org.generated.Test").get()).with(value).get();
        assertThat(
                doc,
                is(Document.parse("{\"prop\": \"value\"}"))
        );

        assertThat(
                mapper.call("toValue", Document.class).with(doc).get(),
                is(value)
        );
    }


    @Test
    public void dateProperty() throws Exception {
        ClassLoaderHelper classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("p").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(TypeToken.DATE_TIME.getImplementationType()))))
                .build());
        this.fileHelper.printFile(this.dir.getRoot(), "TestMongoMapper.java");

        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        Object value = classes.get("org.generated.Test").call("builder")
                .call("p", LocalDateTime.class)
                .with(LocalDateTime.parse("2017-11-27T14:08:13.000Z", DateTimeFormatter.ISO_DATE_TIME))
                .call("build").get();
        System.out.println("value=" + value);
        Document doc = (Document) mapper.call("toDocument", classes.get("org.generated.Test").get()).with(value).get();
        assertThat(
                doc,
                is(Document.parse("{\"p\": {\"$date\":\"2017-11-27T14:08:13.000Z\"}}"))
        );

        assertThat(
                mapper.call("toValue", Document.class).with(doc).get(),
                is(value)
        );
    }

    @Test
    public void dateListProperty() throws Exception {
        ClassLoaderHelper classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("p").type(type().cardinality(PropertyCardinality.LIST).typeKind(TypeKind.JAVA_TYPE).typeRef(TypeToken.DATE_TIME.getImplementationType()))))
                .build());
        this.fileHelper.printFile(this.dir.getRoot(), "TestMongoMapper.java");

        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        Object value = classes.get("org.generated.Test").call("builder")
                .call("p", LocalDateTime[].class)
                .with(new Object[] {new LocalDateTime[] {LocalDateTime.parse("2017-11-27T14:08:13.000Z", DateTimeFormatter.ISO_DATE_TIME)}})
                .call("build").get();
        System.out.println("value=" + value);
        Document doc = (Document) mapper.call("toDocument", classes.get("org.generated.Test").get()).with(value).get();
        assertThat(
                doc,
                is(Document.parse("{\"p\": [{\"$date\":\"2017-11-27T14:08:13.000Z\"}]}"))
        );

        assertThat(
                mapper.call("toValue", Document.class).with(doc).get(),
                is(value)
        );
    }

    @Test
    public void objectIdProperty() throws Exception {
        ClassLoaderHelper classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("p")
                                .hints(new HashSet<>(Arrays.asList("mongo:object-id")))
                                .type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName()))))
                .build());
        this.fileHelper.printFile(this.dir.getRoot(), "TestMongoMapper.java");


        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        ObjectId oid = new ObjectId();

        Object value = classes.get("org.generated.Test").call("builder")
                .call("p", String.class).with(oid.toString())
                .call("build").get();
        System.out.println("value=" + value);
        Document doc = (Document) mapper.call("toDocument", classes.get("org.generated.Test").get()).with(value).get();
        assertThat(
                doc,
                is(Document.parse("{\"p\": {\"$oid\":\"" + oid.toString() + "\"}}"))
        );

        assertThat(
                mapper.call("toValue", Document.class).with(doc).get(),
                is(value)
        );
    }

    @Test
    public void objectIdListProperty() throws Exception {
        ClassLoaderHelper classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("p")
                                .hints(new HashSet<>(Arrays.asList("mongo:object-id")))
                                .type(type().cardinality(PropertyCardinality.LIST).typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName()))))
                .build());


        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        ObjectId oid = new ObjectId();

        Object value = classes.get("org.generated.Test").call("builder")
                .call("p", String[].class).with(new Object[] {new String[] {oid.toString()}})
                .call("build").get();
        System.out.println("value=" + value);
        Document doc = (Document) mapper.call("toDocument", classes.get("org.generated.Test").get()).with(value).get();
        assertThat(
                doc,
                is(Document.parse("{\"p\": [{\"$oid\":\"" + oid.toString() + "\"}]}"))
        );

        Object actualValue = mapper.call("toValue", Document.class).with(doc).get();
        assertThat(
                actualValue,
                is(value)
        );
    }

    @Test
    public void fieldWithTransientHitIsIgnored() throws Exception {
        ClassLoaderHelper classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("stored").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName())))
                        .addProperty(property().name("notStored")
                                .hints(new HashSet<>(Arrays.asList("storage:transient")))
                                .type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName())))
                ).build());

        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        Object value = classes.get("org.generated.Test").call("builder")
                .call("stored", String.class).with("value")
                .call("notStored", String.class).with("value")
                .call("build").get();

        Document doc = (Document) mapper.call("toDocument", classes.get("org.generated.Test").get()).with(value).get();
        assertThat(
                doc,
                is(Document.parse("{\"stored\": \"value\"}"))
        );

        assertThat(
                mapper.call("toValue", Document.class).with(doc).get(),
                is(classes.get("org.generated.Test").call("builder")
                        .call("stored", String.class).with("value")
                        .call("build").get())
        );
    }

    private ClassLoaderHelper generate(Spec spec) throws Exception {
        new SpecCodeGenerator(spec, "org.generated", this.dir.getRoot()).generate();
        new MongoMapperGenerator(
                spec,
                "org.generated.mongo",
                "org.generated",
                this.dir.getRoot()
        ).generate();

        this.fileHelper.printJavaContent("", this.dir.getRoot());

        return CompiledCode.builder()
                .source(this.dir.getRoot())
                .classpath(CompiledCode.findLibraryInClasspath("bson"))
                .compile()
                .classLoader();
    }

}