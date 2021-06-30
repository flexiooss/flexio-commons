package io.flexio.io.mongo.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.flexio.io.mongo.repository.property.query.PropertyQuerier;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.codingmatters.poom.services.domain.exceptions.RepositoryException;
import org.codingmatters.poom.services.domain.property.query.PropertyQuery;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.servives.domain.entities.Entity;
import org.codingmatters.poom.servives.domain.entities.ImmutableEntity;
import org.codingmatters.poom.servives.domain.entities.PagedEntityList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;

public class MongoCollectionRepository<V, Q> implements Repository<V, Q> {

    public static final String VERSION_FIELD = "__version";

    static public <V, Q> MandatoryToDocument<V, Q> repository(String database, String collection) {
        return repository(database, collection, builder -> {});
    }
    static public <V, Q> MandatoryToDocument<V, Q> repository(String database, String collection, Consumer<Collation.Builder> collationConfig) {
        return new Builder<>(database, collection, collationConfig);
    }

    public interface MandatoryToDocument<V, Q> {
        MandatoryToValue<V, Q> withToDocument(Function<V, Document> toDocument);
    }

    public interface MandatoryToValue<V, Q> {
        OptionalFilter<V, Q> withToValue(Function<Document, V> toValue);
    }

    public interface OptionalFilter<V, Q> {
        Builder<V, Q> withCheckedFilter(BsonFromQueryProvider<Q> filter);
        Builder<V, Q> withFilter(Function<Q, Bson> filter);
        Repository<V, Q> build(MongoClient mongoClient);
        Repository<V, PropertyQuery> buildWithPropertyQuery(MongoClient mongoClient);
    }

    static public class Builder<V, Q> implements MandatoryToDocument<V, Q>, MandatoryToValue<V, Q>, OptionalFilter<V, Q> {
        private final String databaseName;
        private final String collectionName;
        private BsonFromQueryProvider<Q> filter = q -> null;
        private BsonFromQueryProvider<Q> sort = q -> null;
        private Function<V, Document> toDocument;
        private Function<Document,V> toValue;
        private Consumer<Collation.Builder> collationConfig;

        public Builder(String databaseName, String collectionName, Consumer<Collation.Builder> collationConfig) {
            this.databaseName = databaseName;
            this.collectionName = collectionName;
            this.collationConfig = collationConfig;
        }

        public Builder<V,Q> withFilter(Function<Q, Bson> filter) {
            return this.withCheckedFilter((BsonFromQueryProvider<Q>) query -> filter.apply(query));
        }

        public Builder<V,Q> withCheckedFilter(BsonFromQueryProvider<Q> filter) {
            this.filter = filter;
            return this;
        }

        public Builder<V,Q> withSort(Function<Q, Bson> sort) {
            return this.withCheckedSort((BsonFromQueryProvider<Q>) query -> sort.apply(query));
        }
        public Builder<V,Q> withCheckedSort(BsonFromQueryProvider<Q> sort) {
            this.sort = sort;
            return this;
        }

        public Builder<V,Q> withToDocument(Function<V, Document> toDocument) {
            this.toDocument = toDocument;
            return this;
        }

        public Builder<V,Q> withCollationConfig(Consumer<Collation.Builder> collationConfig) {
            this.collationConfig = collationConfig;
            return this;
        }

        public Builder<V,Q> withToValue(Function<Document, V> toValue) {
            this.toValue = toValue;
            return this;
        }

        public Repository<V, Q> build(MongoClient mongoClient) {
            return new MongoCollectionRepository<>(
                    mongoClient,
                    this.databaseName,
                    this.collectionName,
                    this.filter,
                    this.sort,
                    this.toDocument,
                    this.toValue,
                    this.collationConfig
                    );
        }

        public Repository<V, PropertyQuery> buildWithPropertyQuery(MongoClient mongoClient) {
            PropertyQuerier querier = new PropertyQuerier();
            return new MongoCollectionRepository<>(
                    mongoClient,
                    this.databaseName,
                    this.collectionName,
                    querier.filterer(),
                    querier.sorter(),
                    this.toDocument,
                    this.toValue,
                    this.collationConfig
                    );
        }
    }

    private Bson filterFrom(Q query) throws RepositoryException {
        try {
            return this.filterProvider.from(query);
        } catch (Exception e) {
            throw new RepositoryException("failed generating filter from query : " + query, e);
        }
    }

    private Bson sortFrom(Q query) throws RepositoryException {
        try {
            return sortProvider.from(query);
        } catch (Exception e) {
            throw new RepositoryException("failed generating sort from query : " + query, e);
        }
    }

    private Document toDocument(V value) {
        return this.toDocument.apply(value);
    }
    private V toValue(Document document) {
        return this.toValue.apply(document);
    }

    private final MongoClient mongoClient;
    private final String databaseName;
    private final String collectionName;
    private final BsonFromQueryProvider<Q> filterProvider;
    private final BsonFromQueryProvider<Q> sortProvider;
    private final Function<V, Document> toDocument;
    private final Function<Document, V> toValue;

    private final Consumer<Collation.Builder> collationConfig;

    private MongoCollectionRepository(MongoClient mongoClient, String databaseName, String collectionName, BsonFromQueryProvider<Q> filterProvider, BsonFromQueryProvider<Q> sortProvider, Function<V, Document> toDocument, Function<Document, V> toValue, Consumer<Collation.Builder> collationConfig) {
        this.mongoClient = mongoClient;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
        this.filterProvider = filterProvider;
        this.sortProvider = sortProvider;
        this.toDocument = toDocument;
        this.toValue = toValue;
        this.collationConfig = collationConfig;
    }

    @Override
    public Entity<V> create(V withValue) throws RepositoryException {
        return this.rawCreate(new ObjectId(), withValue);
    }

    @Override
    public Entity<V> createWithId(String id, V withValue) throws RepositoryException {
        return this.rawCreate(this.mongoIdValue(id), withValue);
    }

    private Entity<V> rawCreate(Object id, V withValue) throws RepositoryException {
        try {
            MongoCollection<Document> collection = this.resourceCollection(this.mongoClient);
            Document doc = this.toDocument(withValue);
            doc.put("_id", id);
            doc.put(VERSION_FIELD, BigInteger.ONE.longValue());

            collection.insertOne(doc);

            return new ImmutableEntity<>(doc.get("_id").toString(), BigInteger.ONE, this.toValue(doc));
        } catch (MongoException e) {
            throw new RepositoryException("mongo exception while creating entity", e);
        }
    }

    @Override
    public Entity<V> retrieve(String id) throws RepositoryException {
        try {
            MongoCollection<Document> collection = this.resourceCollection(this.mongoClient);
            Document doc = collection.find(this.idFilter(id)).limit(1).first();
            if (doc != null) {
                return new ImmutableEntity<>(id, BigInteger.valueOf(documentVersion(doc)), this.toValue(doc));
            } else {
                return null;
            }
        } catch (MongoException e) {
            throw new RepositoryException("mongo exception while retrieving entity", e);
        }
    }

    private Long documentVersion(Document doc) {
        Long version = doc.getLong(VERSION_FIELD);
        return version != null ? version : 1L;
    }

    @Override
    public Entity<V> update(Entity<V> entity, V withValue) throws RepositoryException {
        try {
            Entity<V> stored = this.retrieve(entity.id());
            Document newDoc = this.toDocument(withValue);

            BigInteger newVersion = stored.version().add(BigInteger.ONE);
            newDoc.put(VERSION_FIELD, newVersion.longValue());

            UpdateResult results = this.resourceCollection(this.mongoClient).replaceOne(this.idFilter(entity.id()), newDoc);
            if (results.getModifiedCount() <= 1) {
                return new ImmutableEntity<>(entity.id(), newVersion, withValue);
            } else {
                throw new RepositoryException("failed updating entity " + entity.id() + " (updated count was " + results.getModifiedCount() + ")");
            }
        } catch (MongoException e) {
            throw new RepositoryException("mongo exception while updating entity", e);
        }
    }

    @Override
    public void delete(Entity<V> entity) throws RepositoryException {
        try {
            DeleteResult result = this.resourceCollection(this.mongoClient).deleteOne(this.idFilter(entity.id()));
            if (result.getDeletedCount() != 1) {
                throw new RepositoryException("error deleting entity " + entity.id() + " (deleted count was " + result.getDeletedCount() + ")");
            }
        } catch (MongoException e) {
            throw new RepositoryException("mongo exception while deleting entity", e);
        }
    }

    @Override
    public void deleteFrom( Q query ) throws RepositoryException {
        try {
            Bson filter = query != null ? this.filterFrom(query) : new Document();
            this.resourceCollection(this.mongoClient).deleteMany(filter, new DeleteOptions().collation(this.buildCollation()));
        } catch (MongoException e) {
            throw new RepositoryException("mongo exception while deleting from query", e);
        }
    }

    @Override
    public PagedEntityList<V> all(long startIndex, long endIndex) throws RepositoryException {
        return this.filteredQuery(null, startIndex, endIndex, this.mongoClient);
    }

    @Override
    public PagedEntityList<V> search(Q query, long startIndex, long endIndex) throws RepositoryException {
        return this.filteredQuery(query, startIndex, endIndex, this.mongoClient);
    }


    private PagedEntityList<V> filteredQuery(Q query, long startIndex, long endIndex, MongoClient mongoClient) throws RepositoryException {
        try {
            Bson filter = query != null ? this.filterFrom(query) : null;
            Bson sort = query != null ? this.sortFrom(query) : null;

            MongoCollection<Document> collection = this.resourceCollection(mongoClient);

            long totalCount = filter != null ? collection.countDocuments(filter) : collection.estimatedDocumentCount();
            if (startIndex >= totalCount) {
                return new PagedEntityList.DefaultPagedEntityList<>(0L, 0L, totalCount, new ArrayList<>());
            }

            FindIterable<Document> result = (filter != null ? collection.find(filter) : collection.find())
                    .collation(this.buildCollation())
                    .sort(sort)
                    .skip((int) startIndex)
                    .limit((int) (endIndex - startIndex + 1));

            Collection<Entity<V>> found = new LinkedList<>();
            for (Document document : result) {
                found.add(new ImmutableEntity<>(this.documentId(document), BigInteger.ONE, this.toValue(document)));
            }

            return new PagedEntityList.DefaultPagedEntityList<>(startIndex, startIndex + found.size() - 1, totalCount, found);
        } catch (MongoException e) {
            throw new RepositoryException("mongo exception while querying entities", e);
        }
    }

    private Collation buildCollation() {
        Collation.Builder collation = Collation.builder()
                .locale("simple")
                ;

        this.collationConfig.accept(collation);
        return collation.build();
    }

    private Bson idFilter(String id) {
        return Filters.eq("_id", this.mongoIdValue(id));
    }

    private Comparable<? extends Comparable<?>> mongoIdValue(String id) {
        return ObjectId.isValid(id) ? new ObjectId(id) : id;
    }

    private String documentId(Document document) {
        return document.get("_id").toString();
    }

    private MongoCollection<Document> resourceCollection(MongoClient mongoClient) {
        return mongoClient.getDatabase(this.databaseName).getCollection(this.collectionName).withWriteConcern(WriteConcern.ACKNOWLEDGED);
    }

}
