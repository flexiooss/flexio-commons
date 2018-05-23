package io.flexio.io.mongo.plugin;

import io.flexio.io.mongo.gen.MongoMapperGenerator;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codingmatters.rest.api.generator.ApiTypesGenerator;
import org.codingmatters.rest.api.generator.exception.RamlSpecException;
import org.codingmatters.rest.maven.plugin.raml.RamlFileCollector;
import org.codingmatters.value.objects.exception.LowLevelSyntaxException;
import org.codingmatters.value.objects.exception.SpecSyntaxException;
import org.codingmatters.value.objects.reader.SpecReader;
import org.codingmatters.value.objects.spec.Spec;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.common.ValidationResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;

@Mojo(name = "mongo-mapping")
public class MongoIoGenerationMojo extends AbstractMojo {

    @Parameter(required = false, alias = "destination-package")
    private String destinationPackage;

    @Parameter(required = false, alias = "input-spec")
    private File inputSpecification;

    @Parameter(required = false, alias = "input-spec-resource")
    private String inputSpecificationResource;

    @Parameter(required = false, alias = "api-spec-resource")
    private String apiSpecResource;

    @Parameter(defaultValue = "${basedir}/target/generated-sources/", alias="output-dir")
    private File outputDirectory;

    @Parameter( defaultValue = "${plugin}", readonly = true )
    private PluginDescriptor plugin;

    public File getInputSpecification() {
        return inputSpecification;
    }

    public String getInputSpecificationResource() {
        return inputSpecificationResource;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public String getDestinationPackage() {
        return this.destinationPackage;
    }

    public String getApiSpecResource() {
        return apiSpecResource;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        this.getLog().info("generating mongo mapping for value object with configuration:");
        this.getLog().info("\t- destination package :" + this.getDestinationPackage());
        if(this.getInputSpecification() != null) {
            this.getLog().info("\t- specification file  :" + this.getInputSpecification().getAbsolutePath());
        } else {
            this.getLog().info("\t- specification resource  :" + this.getInputSpecificationResource());
        }
        this.getLog().info("\t- to output directory : " + this.getOutputDirectory().getAbsolutePath());

        MongoMapperGenerator gen = new MongoMapperGenerator(
                this.buildSpec(),
                this.getDestinationPackage() + ".mongo",
                this.getDestinationPackage(),
                this.getOutputDirectory()
        );

        try {
            gen.generate();
        } catch (IOException e) {
            throw new MojoExecutionException("failed ", e);
        }
    }

    private Spec buildSpec() throws MojoFailureException, MojoExecutionException {
        if(this.getInputSpecification() != null || this.getInputSpecificationResource() != null) {
            SpecReader reader = new SpecReader();
            try {
                try (InputStream in = this.specStream()) {
                    return reader.read(in);
                } catch (SpecSyntaxException | LowLevelSyntaxException e) {
                    throw new MojoExecutionException("bad syntax file", e);
                }
            } catch (IOException e) {
                throw new MojoFailureException("failure reading spec", e);
            }
        } else if(this.getApiSpecResource() != null) {
            return this.loadSpecFromApi();
        } else {
            throw new MojoFailureException("must provide either an input-spec file path,  a input-spec-resource resource name or a api-spec-resource RAML API spec for loading spec");
        }
    }

    private InputStream specStream() throws IOException {
        if(this.getInputSpecification() != null) {
            return new FileInputStream(this.getInputSpecification());
        } else if(this.getInputSpecificationResource() != null) {
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(this.getInputSpecificationResource());
        } else {
            throw new IOException("must provide either an input-spec file path or a input-spec-resource resource name for loading spec");
        }
    }

    private Spec loadSpecFromApi() throws MojoFailureException, MojoExecutionException{
        try {
            return new ApiTypesGenerator().generate(this.resolveRamlModel());
        } catch (RamlSpecException e) {
            throw new MojoFailureException("error resolving RAML model", e);
        }
    }


    protected RamlModelResult resolveRamlModel() throws MojoFailureException, MojoExecutionException {
        String resource = this.apiSpecResource;

        RamlFileCollector.Builder builder = RamlFileCollector.spec(resource);
        try {
            this.appendArtifactsJars(builder);
        } catch (IOException e) {
            throw new MojoFailureException("error crawling project artifacts", e);
        }
        try(RamlFileCollector collector = builder.build()) {
            File apiFile = collector.specFile();
            this.getLog().info("API : " + apiFile.getAbsolutePath());
            return this.buildRamlModel(apiFile);
        } catch (Exception e) {
            throw new MojoFailureException("error resolving RAML model files", e);
        }
    }

    private RamlModelResult buildRamlModel(File apiFile) throws MojoExecutionException {
        RamlModelResult ramlModel;
        ramlModel = new RamlModelBuilder().buildApi(apiFile);
        if(ramlModel.hasErrors()) {
            for (ValidationResult validationResult : ramlModel.getValidationResults()) {
                this.getLog().error(validationResult.getMessage());
            }
            throw new MojoExecutionException("failed parsing raml api, see logs");
        }
        return ramlModel;
    }


    private void appendArtifactsJars(RamlFileCollector.Builder builder) throws IOException {
        for (Artifact anArtifact : this.plugin.getArtifacts()) {
            if (anArtifact.getFile().isFile() && anArtifact.getFile().getName().endsWith(".jar")) {
                JarFile jar = new JarFile(anArtifact.getFile().getAbsolutePath());
                builder.classpathJar(jar);
            }
        }
    }
}
