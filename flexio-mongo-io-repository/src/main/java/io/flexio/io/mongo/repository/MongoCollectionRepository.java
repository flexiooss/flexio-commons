package io.flexio.io.mongo.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.codingmatters.poom.services.domain.exceptions.RepositoryException;
import org.codingmatters.poom.services.domain.repositories.Repository;
import org.codingmatters.poom.servives.domain.entities.Entity;
import org.codingmatters.poom.servives.domain.entities.ImmutableEntity;
import org.codingmatters.poom.servives.domain.entities.PagedEntityList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;

public class MongoCollectionRepository<V, Q> implements Repository<V, Q> {

    static public <V, Q> MandatoryToDocument<V, Q> repository(String database, String collection) {
        return new Builder<>(database, collection);
    }

    public interface MandatoryToDocument<V, Q> {
        MandatoryToValue<V, Q> withToDocument(Function<V, Document> toDocument);
    }

    public interface MandatoryToValue<V, Q> {
        OptionalFilter<V, Q> withToValue(Function<Document, V> toValue);
    }

    public interface OptionalFilter<V, Q> {
        Builder<V, Q> withFilter(Function<Q, Bson> filter);
        Repository<V, Q> build(MongoClient mongoClient);
    }

    static public class Builder<V, Q> implements MandatoryToDocument<V, Q>, MandatoryToValue<V, Q>, OptionalFilter<V, Q> {
        private final String databaseName;
        private final String collectionName;
        private Function<Q, Bson> filter = q -> null;
        private Function<Q, Bson> sort = q -> null;
        private Function<V, Document> toDocument;
        private Function<Document,V> toValue;

        public Builder(String databaseName, String collectionName) {
            this.databaseName = databaseName;
            this.collectionName = collectionName;
        }

        public Builder withFilter(Function<Q, Bson> filter) {
            this.filter = filter;
            return this;
        }

        public Builder withSort(Function<Q, Bson> sort) {
            this.sort = sort;
            return this;
        }

        public Builder withToDocument(Function<V, Document> toDocument) {
            this.toDocument = toDocument;
            return this;
        }

        public Builder withToValue(Function<Document, V> toValue) {
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
                    this.toValue);
        }
    }

    private Bson filterFrom(Q query) {
        return this.filter.apply(query);
    }

    private Bson sortFrom(Q query) {
        return sort.apply(query);
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
    private final Function<Q, Bson> filter;
    private final Function<Q, Bson> sort;
    private final Function<V, Document> toDocument;
    private final Function<Document, V> toValue;

    public MongoCollectionRepository(MongoClient mongoClient, String databaseName, String collectionName, Function<Q, Bson> filter, Function<Q, Bson> sort, Function<V, Document> toDocument, Function<Document, V> toValue) {
        this.mongoClient = mongoClient;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
        this.filter = filter;
        this.sort = sort;
        this.toDocument = toDocument;
        this.toValue = toValue;
    }

    @Override
    public Entity<V> create(V withValue) throws RepositoryException {
        try {
            MongoCollection<Document> collection = this.resourceCollection(this.mongoClient);
            Document doc = this.toDocument(withValue);
            doc.put("_id", new ObjectId());
            collection.insertOne(doc);

            return new ImmutableEntity<>(doc.get("_id").toString(), BigInteger.ONE, this.toValue(doc));
        } catch(MongoException e) {
            throw new RepositoryException("error creating entity for " + withValue, e);
        }
    }

    @Override
    public Entity<V> retrieve(String id) throws RepositoryException {
        MongoCollection<Document> collection = this.resourceCollection(this.mongoClient);
        Document doc = collection.find(this.idFilter(id)).limit(1).first();
        if (doc != null) {
            return new ImmutableEntity<>(id, BigInteger.ONE, this.toValue(doc));
        } else {
            return null;
        }
    }

    @Override
    public Entity<V> update(Entity<V> entity, V withValue) throws RepositoryException {
        Document newDoc = this.toDocument(withValue);
        UpdateResult results = this.resourceCollection(this.mongoClient).replaceOne(this.idFilter(entity.id()), newDoc);
        if(results.getModifiedCount() <= 1) {
            return new ImmutableEntity<>(entity.id(), BigInteger.ONE, withValue);
        } else {
            throw new RepositoryException("failed updating entity " + entity.id() + " (updated count was " + results.getModifiedCount() + ")");
        }
    }

    @Override
    public void delete(Entity<V> entity) throws RepositoryException {
        DeleteResult result = this.resourceCollection(this.mongoClient).deleteOne(this.idFilter(entity.id()));
        if(result.getDeletedCount() != 1) {
            throw new RepositoryException("error deleting entity " + entity.id() + " (deleted count was " + result.getDeletedCount() + ")");
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


    private PagedEntityList<V> filteredQuery(Q query, long startIndex, long endIndex, MongoClient mongoClient) {
        Bson filter = query != null ? this.filterFrom(query) : null;
        Bson sort = query != null ? this.sortFrom(query) : null;

        MongoCollection<Document> collection = this.resourceCollection(mongoClient);

        long totalCount = filter != null ? collection.count(filter) : collection.count();
        if(startIndex >= totalCount) {
            return new PagedEntityList.DefaultPagedEntityList<>(0L, 0L, totalCount, new ArrayList<>());
        }

        FindIterable<Document> result = (filter != null ? collection.find(filter) : collection.find())
                .sort(sort)
                .skip((int) startIndex)
                .limit((int) (endIndex - startIndex + 1));

        Collection<Entity<V>> found = new LinkedList<>();
        for (Document document : result) {
            found.add(new ImmutableEntity<>(this.documentId(document), BigInteger.ONE, this.toValue(document)));
        }

        return new PagedEntityList.DefaultPagedEntityList<>(startIndex, startIndex + found.size() - 1, totalCount, found);
    }

    private Bson idFilter(String id) {
        return Filters.eq("_id", ObjectId.isValid(id) ? new ObjectId(id) : id);
    }

    private String documentId(Document document) {
        return document.get("_id").toString();
    }

    private MongoCollection<Document> resourceCollection(MongoClient mongoClient) {
        return mongoClient.getDatabase(this.databaseName).getCollection(this.collectionName).withWriteConcern(WriteConcern.ACKNOWLEDGED);
    }

}
