package io.flexio.io.mongo.repository;

import io.flexio.docker.DockerResource;
import io.flexio.services.tests.mongo.MongoResource;
import io.flexio.services.tests.mongo.MongoTest;
import org.codingmatters.generated.QAValue;
import org.codingmatters.generated.mongo.QAValueMongoMapper;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.domain.repositories.RepositoryBaseAcceptanceTest;
import org.junit.ClassRule;
import org.junit.Rule;

public class MongoCollectionRepositoryBaseAcceptanceTest extends RepositoryBaseAcceptanceTest {

    public static final String DB = "test-db";
    public static final String COLLECTION = "test-collection";
    @ClassRule
    static public DockerResource docker = MongoTest.docker()
            .started().finallyStarted();

    @Rule
    public MongoResource mongo = MongoTest.mongo(() -> docker)
            .testDB(DB);

    @Override
    protected Repository<QAValue, Void> createRepository() throws Exception {
        QAValueMongoMapper mapper = new QAValueMongoMapper();
        return MongoCollectionRepository.<QAValue, Void>repository(DB, COLLECTION)
                .withToDocument(value -> mapper.toDocument(value))
                .withToValue(document -> mapper.toValue(document))
                .build(mongo.newClient())
                ;
    }

    @Override
    public void givenEntityStoredWithVersion_andEntityUpdatedFromId__whenFromGratherVersion__thenIdKept_andVersionIncremented_andValueChanged() throws Exception {
    }

    @Override
    public void givenEntityStoredWithVersion_andEntityUpdatedFromId__whenFromLowerVersion__thenIdKept_andVersionIncremented_andValueChanged() throws Exception {
    }

    @Override
    public void givenIdStoredWithId__whenCreatingEntityWithSameIdAndDifferentVersion__thenRepositoryException() throws Exception {
    }

    @Override
    public void whenCreatingWithIdAndVersion__thenEntityStoredWithIdAndVersion() throws Exception {
    }
}
