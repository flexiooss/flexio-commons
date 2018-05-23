package io.flexio.io.mongo.gen;

import com.squareup.javapoet.ClassName;
import org.codingmatters.value.objects.generation.ValueConfiguration;
import org.codingmatters.value.objects.spec.PropertySpec;
import org.codingmatters.value.objects.spec.TypeKind;
import org.codingmatters.value.objects.spec.TypeToken;
import org.codingmatters.value.objects.spec.ValueSpec;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MapperConfiguration extends ValueConfiguration {

    private final String mapperRootPackage;

    public MapperConfiguration(String mapperRootPackage, String valueRootPackage, String packageName, ValueSpec valueSpec) {
        super(valueRootPackage, packageName, valueSpec);
        this.mapperRootPackage = mapperRootPackage;
    }

    public ClassName mapperName() {
        return ClassName.get(this.mapperRootPackage, this.valueType().simpleName() + "MongoMapper");
    }

    public ClassName mongoMapperClassName(PropertySpec propertySpec) {
        return ClassName.get(
                this.valueObjectSingleType(propertySpec).packageName() + ".mongo" ,
                this.valueObjectSingleType(propertySpec).simpleName() + "MongoMapper");
    }

    static private Set<String> TEMPORAL_TYPES = new HashSet<>(Arrays.asList(
            TypeToken.DATE.getImplementationType(),
            TypeToken.TIME.getImplementationType(),
            TypeToken.DATE_TIME.getImplementationType(),
            TypeToken.TZ_DATE_TIME.getImplementationType()
    ));

    public boolean isTemporalProperty(PropertySpec propertySpec) {
        return propertySpec.typeSpec().typeKind().equals(TypeKind.JAVA_TYPE) &&
                TEMPORAL_TYPES.contains(propertySpec.typeSpec().typeRef()) ;
    }
}
