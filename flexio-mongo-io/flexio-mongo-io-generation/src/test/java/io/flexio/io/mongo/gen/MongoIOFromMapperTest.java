package io.flexio.io.mongo.gen;

import com.mongodb.MongoClient;
import io.flexio.docker.DockerResource;
import io.flexio.services.tests.mongo.MongoResource;
import io.flexio.services.tests.mongo.MongoTest;
import org.bson.Document;
import org.bson.types.Decimal128;
import org.codingmatters.tests.compile.CompiledCode;
import org.codingmatters.tests.compile.FileHelper;
import org.codingmatters.tests.compile.helpers.ClassLoaderHelper;
import org.codingmatters.tests.compile.helpers.helpers.ObjectHelper;
import org.codingmatters.value.objects.generation.SpecCodeGenerator;
import org.codingmatters.value.objects.spec.PropertyCardinality;
import org.codingmatters.value.objects.spec.Spec;
import org.codingmatters.value.objects.spec.TypeKind;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static io.flexio.services.tests.mongo.MongoTest.MONGO;
import static org.codingmatters.value.objects.spec.AnonymousValueSpec.anonymousValueSpec;
import static org.codingmatters.value.objects.spec.PropertySpec.property;
import static org.codingmatters.value.objects.spec.PropertyTypeSpec.type;
import static org.codingmatters.value.objects.spec.ValueSpec.valueSpec;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MongoIOFromMapperTest {

    @Rule
    public TemporaryFolder dir = new TemporaryFolder();

    @Rule
    public FileHelper fileHelper = new FileHelper();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @ClassRule
    static public DockerResource docker = MongoTest.docker()
            .started().finallyStarted();

    @Rule
    public MongoResource mongo = new MongoResource(
            () -> docker.containerInfo(MONGO).networkSettings().iPAddress()
                    .orElseThrow(() -> new AssertionError("no ip address for container")),
            27017
    ).testDB("test");

    private ClassLoaderHelper classes;

    @Before
    public void setUp() throws Exception {
        this.classes = this.generate(Spec.spec()
                .addValue(valueSpec().name("Test")
                        .addProperty(property().name("p1").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName())))
                        .addProperty(property().name("p2").type(type().cardinality(PropertyCardinality.LIST).typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName())))
                        .addProperty(property().name("pI").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(Integer.class.getName())))
                        .addProperty(property().name("pL").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(Long.class.getName())))
                        // FLOAT not supported
                        .addProperty(property().name("pF").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(Float.class.getName())))
                        .addProperty(property().name("pD").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(Double.class.getName())))
                        .addProperty(property().name("pB").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(Boolean.class.getName())))

                        .addProperty(property().name("pEmb").type(type().typeKind(TypeKind.EMBEDDED)
                                .embeddedValueSpec(anonymousValueSpec()
                                        .addProperty(property().name("p").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName()))))
                        ))
                        .addProperty(property().name("pEmbList").type(type().typeKind(TypeKind.EMBEDDED).cardinality(PropertyCardinality.LIST)
                                .embeddedValueSpec(anonymousValueSpec()
                                        .addProperty(property().name("p").type(type().typeKind(TypeKind.JAVA_TYPE).typeRef(String.class.getName()))))
                        ))
                ).build());
    }

    @Test
    public void insert() throws Exception {
        ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

        Object value = classes.get("org.generated.Test").call("builder")
                .call("p1", String.class).with("v1")
                .call("p2", String[].class).with(new Object[] {new String [] {"v1", "v2"}})
                .call("pI", Integer.class).with(12)
                .call("pL", Long.class).with(12L)
                .call("pF", Float.class).with(12.0f)
                .call("pD", Double.class).with(12.0d)
                .call("pB", Boolean.class).with(false)

                .call("pEmb", classes.get("org.generated.test.PEmb").get()).with(
                        classes.get("org.generated.test.PEmb").call("builder")
                                .call("p", String.class).with("value")
                                .call("build").get()
                )
                .call("pEmbList", classes.get("org.generated.test.PEmbList").array().get()).with(
                        classes.get("org.generated.test.PEmbList").array().newArray(
                                classes.get("org.generated.test.PEmbList").call("builder")
                                        .call("p", String.class).with("v1")
                                        .call("build").get(),
                                classes.get("org.generated.test.PEmbList").call("builder")
                                        .call("p", String.class).with("v2")
                                        .call("build").get()
                        ).get()
                )

                .call("build").get();
        Document doc = (Document) mapper.call("toDocument", classes.get("org.generated.Test").get()).with(value).get();

        try(MongoClient client = this.mongo.newClient()) {
            client.getDatabase("test").getCollection("test").insertOne(doc);

            Document document = client.getDatabase("test").getCollection("test").find().first();
            assertThat(document.get("p1"), is("v1"));
            assertThat(((List<String>) document.get("p2")), contains("v1", "v2"));
            assertThat(document.get("pI"), is(12));
            assertThat(document.get("pL"), is(12L));
            assertThat(document.get("pF"), is(new Decimal128(new BigDecimal("12.0"))));
            System.out.println(document.get("pD").getClass());
            assertThat(document.get("pD"), is(new Decimal128(new BigDecimal("12.0"))));
            assertThat(document.get("pB"), is(false));

            assertThat(document.get("pEmb"), is(Document.parse("{\"p\": \"value\"}")));
            assertThat(document.get("pEmbList"), is(Arrays.asList(Document.parse("{\"p\": \"v1\"}"), Document.parse("{\"p\": \"v2\"}"))));
        }
    }

    @Test
    public void find() throws Exception {
        try(MongoClient client = this.mongo.newClient()) {
            Document doc = new Document();
            doc.put("p1", "v1");
            doc.put("p2", Arrays.asList("v1", "v2"));
            doc.put("pI", 12);
            doc.put("pL", 12L);
            //doc.put("pF", 12.0);
            doc.put("pD", 12.0d);
            doc.put("pB", false);
            doc.put("pEmb", Document.parse("{\"p\": \"value\"}"));
            doc.put("pEmbList", Arrays.asList(Document.parse("{\"p\": \"v1\"}"), Document.parse("{\"p\": \"v2\"}")));

            client.getDatabase("test").getCollection("test").insertOne(doc);
        }

        try(MongoClient client = this.mongo.newClient()) {
            Document document = client.getDatabase("test").getCollection("test").find().first();
            ObjectHelper mapper = classes.get("org.generated.mongo.TestMongoMapper").newInstance();

            Object value = mapper.call("toValue", Document.class).with(document).get();

            assertThat(value, is(
                    classes.get("org.generated.Test").call("builder")
                            .call("p1", String.class).with("v1")
                            .call("p2", String[].class).with(new Object[] {new String [] {"v1", "v2"}})
                            .call("pI", Integer.class).with(12)
                            .call("pL", Long.class).with(12L)
                            //.call("pF", Float.class).with(12.0f)
                            .call("pD", Double.class).with(12.0d)
                            .call("pB", Boolean.class).with(false)

                            .call("pEmb", classes.get("org.generated.test.PEmb").get()).with(
                                    classes.get("org.generated.test.PEmb").call("builder")
                                            .call("p", String.class).with("value")
                                            .call("build").get()
                            )
                            .call("pEmbList", classes.get("org.generated.test.PEmbList").array().get()).with(
                                    classes.get("org.generated.test.PEmbList").array().newArray(
                                            classes.get("org.generated.test.PEmbList").call("builder")
                                                    .call("p", String.class).with("v1")
                                                    .call("build").get(),
                                            classes.get("org.generated.test.PEmbList").call("builder")
                                                    .call("p", String.class).with("v2")
                                                    .call("build").get()
                                    ).get()
                            )

                            .call("build").get()
            ));
        }
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
        this.fileHelper.printFile(this.dir.getRoot(), "TestMongoMapper.java");
        this.fileHelper.printFile(this.dir.getRoot(), "ValueList.java");

        return CompiledCode.builder()
                .source(this.dir.getRoot())
                .classpath(CompiledCode.findLibraryInClasspath("bson"))
                .compile()
                .classLoader();
    }
}
