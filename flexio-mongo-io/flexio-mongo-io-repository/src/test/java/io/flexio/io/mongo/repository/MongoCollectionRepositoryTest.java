package io.flexio.io.mongo.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import io.flexio.docker.DockerResource;
import io.flexio.io.mongo.repository.domain.MongoQuery;
import io.flexio.io.mongo.repository.domain.MongoValue;
import io.flexio.io.mongo.repository.domain.mongo.MongoValueMongoMapper;
import io.flexio.services.tests.mongo.MongoResource;
import io.flexio.services.tests.mongo.MongoTest;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.codingmatters.poom.services.domain.exceptions.AlreadyExistsException;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.services.domain.entities.Entity;
import org.codingmatters.poom.services.domain.entities.ImmutableEntity;
import org.codingmatters.poom.services.domain.entities.PagedEntityList;
import org.junit.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

public class MongoCollectionRepositoryTest {

    public static final String DB = "test-db";
    public static final String COLLECTION = "test-collection";
    @ClassRule
    static public DockerResource docker = MongoTest.docker()
            .started().finallyStarted();

    @Rule
    public MongoResource mongo = MongoTest.mongo(() -> docker)
            .testDB(DB)
            .importCollectionContent("test-collection.json", DB, COLLECTION);

    private Repository<MongoValue, MongoQuery> repository;

    private final AtomicInteger collationConfigCalled = new AtomicInteger(0);
    private MongoClient mongoClient;

    @Before
    public void setUp() throws Exception {
        mongoClient = mongo.newClient();
        this.repository = MongoCollectionRepository.<MongoValue, MongoQuery>repository(DB, COLLECTION)
                .withToDocument(value -> new MongoValueMongoMapper().toDocument(value))
                .withToValue(document -> new MongoValueMongoMapper().toValue(document))
                .withFilter(query -> query.opt().name().isPresent() ? Filters.eq("name", query.name()) : null)
                .withCollationConfig(builder -> collationConfigCalled.incrementAndGet())
                .build(mongoClient);
    }
//
//    @After
//    public void endWith (){
//        mongoClient.getDatabase(DB).getCollection(COLLECTION).drop();
//    }


    @Test
    public void retrieveARowWithObjectIdId() throws Exception {
        Entity<MongoValue> entity = this.repository.retrieve("5a0188c7d5c64000165d50bd");

        assertThat(entity, is(notNullValue()));
        assertThat(entity.id(), is("5a0188c7d5c64000165d50bd"));
    }

    @Test
    public void retrieveARowWithStringId() throws Exception {
        Entity<MongoValue> entity = this.repository.retrieve("-FlexLogs-59fc2c5e86ee7");

        assertThat(entity, is(notNullValue()));
        assertThat(entity.id(), is("-FlexLogs-59fc2c5e86ee7"));
    }

    @Test
    public void unexistingRowIsRetrievedAsNull() throws Exception {
        Entity<MongoValue> entity = this.repository.retrieve("nothing here");

        assertThat(entity, is(nullValue()));
    }

    @Test
    public void update() throws Exception {
        Entity<MongoValue> entity = this.repository.retrieve("5a0188c7d5c64000165d50bd");
        assertThat(entity.value().name(), is("Commentaire"));

        entity = this.repository.update(entity, MongoValue.builder().name("updated").build());

        assertThat(entity.id(), is("5a0188c7d5c64000165d50bd"));
        assertThat(entity.value(), is(MongoValue.builder().name("updated").build()));
    }

    @Test
    public void updateWhenIdNotSet() throws Exception {
        String id;
        try(MongoClient client = this.mongo.newClient()) {
            Document doc = new MongoValueMongoMapper().toDocument(MongoValue.builder().name("init").build());
            ObjectId objectId = new ObjectId();
            doc.put("_id", objectId);
            id = objectId.toString();
            client.getDatabase(DB).getCollection(COLLECTION).insertOne(doc).getInsertedId();
        }

        Entity<MongoValue> entity = this.repository.retrieve(id);
        assertThat(entity.value().name(), is("init"));
        assertThat(entity.version(), is(BigInteger.ONE));

        Entity<MongoValue> updated = this.repository.update(entity, MongoValue.builder().name("changed").build());
        assertThat(updated.version(), is(BigInteger.TWO));

        entity = this.repository.retrieve(id);
        assertThat(entity.value().name(), is("changed"));
        assertThat(entity.version(), is(BigInteger.TWO));
    }

    @Test
    public void create() throws Exception {
        Entity<MongoValue> entity = this.repository.create(MongoValue.builder().name("created").build());

        assertThat(entity.id(), is(notNullValue()));

        try(MongoClient client = this.mongo.newClient()) {
            Document doc = client.getDatabase(DB).getCollection(COLLECTION).find(Filters.eq("_id", new ObjectId(entity.id()))).first();
            assertThat(doc.get("name"), is("created"));
        }
    }

    @Test
    public void createMany() throws Exception {
        List<String> entityIDs = this.repository.createMany(MongoValue.builder().name("created").build(),
                MongoValue.builder().name("created").build(),
                MongoValue.builder().name("created").build(),
                MongoValue.builder().name("created").build());
        for (String id : entityIDs) {
            assertThat(id, is(notNullValue()));
            try(MongoClient client = this.mongo.newClient()) {
                Document doc = client.getDatabase(DB).getCollection(COLLECTION).find(Filters.eq("_id", new ObjectId(id))).first();
                assertThat(doc.get("name"), is("created"));
            }
        }
    }

    @Test
    public void createWithId() throws Exception {
        Entity<MongoValue> entity = this.repository.createWithId("toto", MongoValue.builder().name("created").build());

        assertThat(entity.id(), is(notNullValue()));

        try(MongoClient client = this.mongo.newClient()) {
            Document doc = client.getDatabase(DB).getCollection(COLLECTION).find(Filters.eq("_id", "toto")).first();
            assertThat(doc.get("name"), is("created"));
        }
    }

    @Test
    public void createWithExistingId() throws Exception {
        Entity<MongoValue> entity = this.repository.createWithId("toto", MongoValue.builder().name("created").build());

        assertThrows(AlreadyExistsException.class, () -> this.repository.createWithId("toto", MongoValue.builder().name("changed").build()));
    }

    @Test
    public void delete() throws Exception {
        try(MongoClient client = this.mongo.newClient()) {
            FindIterable<Document> docs = client.getDatabase(DB).getCollection(COLLECTION).find(Filters.eq("_id", new ObjectId("5a0188c7d5c64000165d50bd")));
            assertThat(docs.first(), is(notNullValue()));
        }

        this.repository.delete(new ImmutableEntity<>("5a0188c7d5c64000165d50bd", BigInteger.ONE, null));

        try(MongoClient client = this.mongo.newClient()) {
            FindIterable<Document> docs = client.getDatabase(DB).getCollection(COLLECTION).find(Filters.eq("_id", new ObjectId("5a0188c7d5c64000165d50bd")));
            assertThat(docs.first(), is(nullValue()));
        }
    }

    @Test
    public void deleteFromQuery() throws Exception {
        try( MongoClient client = this.mongo.newClient() ){
            FindIterable<Document> docs = client.getDatabase( DB ).getCollection( COLLECTION ).find( Filters.eq( "_id", new ObjectId( "5a0188c7d5c64000165d50bd" ) ) );
            assertThat( docs.first(), is( notNullValue() ) );
        }

        this.repository.deleteFrom( MongoQuery.builder().name( "Blup" ).build() );

        try( MongoClient client = this.mongo.newClient() ){
            String[] deletedIds = new String[]{
                    "5a1e6db99c3e84001528aaaa",
                    "5a1e6db99c3e84001528aaab",
                    "5a1e6db99c3e84001528aaac",
                    "5a1e6db99c3e84001528aaad",
                    "5a1e6db99c3e84001528aaae",
                    "5a1e6db99c3e84001528aaaf",
                    "5a1e6db99c3e84001528aaba",
                    "5a1e6db99c3e84001528aabb",
                    "5a1e6db99c3e84001528aabc",
                    "5a1e6db99c3e84001528aabd",
                    "5a1e6db99c3e84001528aabe"
            };
            for( String id : deletedIds ){
                FindIterable<Document> docs = client.getDatabase( DB ).getCollection( COLLECTION ).find( Filters.eq( "_id", new ObjectId( id ) ) );
                assertThat( docs.first(), is( nullValue() ) );
            }
        }
    }

    @Test
    public void deleteAllFromNullQuery() throws Exception {
        try( MongoClient client = this.mongo.newClient() ){
            FindIterable<Document> docs = client.getDatabase( DB ).getCollection( COLLECTION ).find( Filters.eq( "_id", new ObjectId( "5a0188c7d5c64000165d50bd" ) ) );
            assertThat( docs.first(), is( notNullValue() ) );
        }

        this.repository.deleteFrom( null );

        try( MongoClient client = this.mongo.newClient() ){
            long total = client.getDatabase( DB ).getCollection( COLLECTION ).countDocuments();
            assertThat( total, is( 0L ) );
        }
    }

    @Test
    public void all() throws Exception {
        PagedEntityList<MongoValue> results = this.repository.all(0, 1000);

        System.out.println(results);
        assertThat(results.total(), is(22L));
        assertThat(results.size(), is(22));
        assertThat(results.startIndex(), is(0L));
        assertThat(results.endIndex(), is(21L));
    }

    @Test
    public void all_firstPage() throws Exception {
        PagedEntityList<MongoValue> results = this.repository.all(0, 4);
        assertThat(results.total(), is(22L));
        assertThat(results.size(), is(5));
        assertThat(results.startIndex(), is(0L));
        assertThat(results.endIndex(), is(4L));
    }

    @Test
    public void all_secondPage() throws Exception {
        PagedEntityList<MongoValue> results = this.repository.all(5, 9);
        assertThat(results.total(), is(22L));
        assertThat(results.size(), is(5));
        assertThat(results.startIndex(), is(5L));
        assertThat(results.endIndex(), is(9L));
    }

    @Test
    public void all_lastPartialPage() throws Exception {
        PagedEntityList<MongoValue> results = this.repository.all(20, 24);
        assertThat(results.total(), is(22L));
        assertThat(results.size(), is(2));
        assertThat(results.startIndex(), is(20L));
        assertThat(results.endIndex(), is(21L));
    }

    @Test
    public void all_offRangePage() throws Exception {
        PagedEntityList<MongoValue> results = this.repository.all(25, 30);
        assertThat(results.total(), is(22L));
        assertThat(results.size(), is(0));
        assertThat(results.startIndex(), is(0L));
        assertThat(results.endIndex(), is(0L));
    }



    @Test
    public void search() throws Exception {
        PagedEntityList<MongoValue> results = this.repository.search(
                MongoQuery.builder().name("Blup").build(),
                0, 1000);

        System.out.println(results);
        assertThat(results.total(), is(11L));
        assertThat(results.size(), is(11));
        assertThat(results.startIndex(), is(0L));
        assertThat(results.endIndex(), is(10L));
    }

    @Test
    public void search_firstPage() throws Exception {
        PagedEntityList<MongoValue> results = this.repository.search(
                MongoQuery.builder().name("Blup").build(),
                0, 4);
        assertThat(results.total(), is(11L));
        assertThat(results.size(), is(5));
        assertThat(results.startIndex(), is(0L));
        assertThat(results.endIndex(), is(4L));
    }

    @Test
    public void search_secondPage() throws Exception {
        PagedEntityList<MongoValue> results = this.repository.search(
                MongoQuery.builder().name("Blup").build(),
                5, 9);
        assertThat(results.total(), is(11L));
        assertThat(results.size(), is(5));
        assertThat(results.startIndex(), is(5L));
        assertThat(results.endIndex(), is(9L));
    }

    @Test
    public void search_lastPartialPage() throws Exception {
        PagedEntityList<MongoValue> results = this.repository.search(
                MongoQuery.builder().name("Blup").build(),
                10, 14);
        assertThat(results.total(), is(11L));
        assertThat(results.size(), is(1));
        assertThat(results.startIndex(), is(10L));
        assertThat(results.endIndex(), is(10L));
    }

    @Test
    public void search_offRangePage() throws Exception {
        PagedEntityList<MongoValue> results = this.repository.search(
                MongoQuery.builder().name("Blup").build(),
                20, 30);
        assertThat(results.total(), is(11L));
        assertThat(results.size(), is(0));
        assertThat(results.startIndex(), is(0L));
        assertThat(results.endIndex(), is(0L));
    }

    @Test
    public void givenCreateWithId__whenNotAnObjectId__thenEntityIsRetrievedFromStringId() throws Exception {
        String notAnObjectId = "yopyop tagada";
        assertThat(ObjectId.isValid(notAnObjectId), is(false));

        Entity<MongoValue> expected = this.repository.createWithId(notAnObjectId, MongoValue.builder().name("blu").build());
        Entity<MongoValue> actual = this.repository.retrieve(notAnObjectId);

        assertThat(actual, is(expected));
    }

    @Test
    public void givenCreateWithId__whenAnObjectId__thenEntityIsRetrievedFromObjectId() throws Exception {
        String notAnObjectId = new ObjectId().toHexString();
        assertThat(ObjectId.isValid(notAnObjectId), is(true));

        Entity<MongoValue> expected = this.repository.createWithId(notAnObjectId, MongoValue.builder().name("blu").build());
        Entity<MongoValue> actual = this.repository.retrieve(notAnObjectId);

        assertThat(actual, is(expected));
    }

    @Test
    public void whenAll__thenCollationConfigCalled() throws Exception {
        this.repository.all(0, 0);
        assertThat(this.collationConfigCalled.get(), is(1));
    }

    @Test
    public void whenSearch__thenCollationConfigCalled() throws Exception {
        this.repository.search(MongoQuery.builder().build(), 0, 0);
        assertThat(this.collationConfigCalled.get(), is(1));
    }

    @Test
    public void whenDeleteFrom__thenCollationConfigCalled() throws Exception {
        this.repository.deleteFrom(MongoQuery.builder().name("Blup").build());
        assertThat(this.collationConfigCalled.get(), is(1));
    }

    @Test
    public void whenDelete__thenCollationConfigIsNotCalled() throws Exception {
        Entity<MongoValue> e = this.repository.create(MongoValue.builder().build());
        this.repository.delete(e);
        assertThat(this.collationConfigCalled.get(), is(0));
    }

    @Test
    public void whenRetrieve__thenCollationConfigIsNotCalled() throws Exception {
        this.repository.retrieve("42");
        assertThat(this.collationConfigCalled.get(), is(0));
    }
}
