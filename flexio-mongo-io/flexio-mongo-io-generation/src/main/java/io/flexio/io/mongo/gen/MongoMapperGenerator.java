package io.flexio.io.mongo.gen;

import com.squareup.javapoet.TypeSpec;
import org.codingmatters.value.objects.generation.preprocessor.PackagedValueSpec;
import org.codingmatters.value.objects.generation.preprocessor.SpecPreprocessor;
import org.codingmatters.value.objects.spec.Spec;

import java.io.File;
import java.io.IOException;

import static org.codingmatters.value.objects.generation.GenerationUtils.packageDir;
import static org.codingmatters.value.objects.generation.GenerationUtils.writeJavaFile;

public class MongoMapperGenerator {

    private final Spec spec;
    private final String rootPackage;
    private final String valueRootPackage;
    private final File rootDirectory;

    public MongoMapperGenerator(Spec spec, String rootPackage, String valueRootPackage, File rootDirectory) {
        this.spec = spec;
        this.rootPackage = rootPackage;
        this.valueRootPackage = valueRootPackage;
        this.rootDirectory = rootDirectory;
    }

    public void generate() throws IOException {
        for (PackagedValueSpec valueSpec : new SpecPreprocessor(this.spec, this.valueRootPackage).packagedValueSpec()) {
            this.generateValueMapper(valueSpec);
        }
    }

    private void generateValueMapper(PackagedValueSpec packagedValueSpec) throws IOException {
        String mongoPackage = packagedValueSpec.packagename() + ".mongo";
        File packageDestination = packageDir(this.rootDirectory, mongoPackage);
        packageDestination.mkdirs();

        MapperConfiguration valueConfig = new MapperConfiguration(mongoPackage, this.valueRootPackage, packagedValueSpec.packagename(), packagedValueSpec.valueSpec());

        TypeSpec type = new ValueMongoMapper(valueConfig).typeSpec();
        writeJavaFile(packageDestination, mongoPackage, type);
    }
}
