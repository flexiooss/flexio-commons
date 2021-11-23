package io.flexio.services.tests.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.flexio.services.tests.mongo.dump.DatabaseRestorer;
import io.flexio.services.tests.mongo.dump.DumpReader;
import io.flexio.services.tests.mongo.dump.ResourceDumpReader;
import org.bson.Document;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class MongoResource extends ExternalResource {

    static private final Logger log = LoggerFactory.getLogger(MongoResource.class);

    private final Supplier<String> hostProvider;
    private final int port;

    private final LinkedList<MongoClient> clients = new LinkedList<>();
    private final HashSet<String> testDBs = new HashSet<>();
    private final HashSet<DbCollection> testCollections = new HashSet<>();
    private final HashSet<CollectionImport> collectionImports = new HashSet<>();
    private final HashSet<DBImport> dbImports = new HashSet<>();


    public MongoResource(Supplier<String> host, int port) {
        this.hostProvider = host;
        this.port = port;
    }

    public String host() {
        return this.hostProvider.get();
    }

    public int port() {
        return this.port;
    }

    public MongoClient newClient() {
        MongoClient mongoClient = MongoClients.create(this.mongoUrl());
        this.clients.add(mongoClient);
        return mongoClient;
    }

    public String mongoUrl() {
        return String.format("mongodb://%s:%s", this.hostProvider.get(), this.port);
    }

    public MongoResource testDB(String dbname) {
        this.testDBs.add(dbname);
        return this;
    }

    public MongoResource testDBFromDump(String dbname, String dumpResourceBase) {
        this.testDB(dbname);
        this.dbImports.add(new DBImport(new ResourceDumpReader(dumpResourceBase), dbname));
        return this;
    }

    public MongoResource testCollection(String dbname, String collectionName) {
        this.testCollections.add(new DbCollection(dbname, collectionName));
        return this;
    }

    public void reset() throws Exception {
        this.after();
        try {
            this.before();
        } catch (Throwable throwable) {
            throw new AssertionError("failed resetting mongo state");
        }
    }

    @Override
    protected void before() throws Throwable {
        try (MongoClient client = this.newClient()) {
            for (CollectionImport anImport : this.collectionImports) {
                this.doImport(anImport, client);
            }
            for (DBImport dbImport : this.dbImports) {
                dbImport.doImport(client);
            }
        }
    }

    private void doImport(CollectionImport anImport, MongoClient client) throws Exception {
        List<Document> docs = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(anImport.mongoExportResource)))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.trim().startsWith("{")) {
                    docs.add(Document.parse(line.trim()));
                }
            }
        }
        log.debug("importing {}/{} contents from {}...", anImport.db, anImport.collection, anImport.mongoExportResource);
        client.getDatabase(anImport.db).getCollection(anImport.collection).insertMany(docs);
        log.debug("done importing {} documents.", docs.size());
    }

    @Override
    protected void after() {
        MongoClient c = this.newClient();
        for (DbCollection dbCollection : this.testCollections) {
            if (contains(c.listDatabaseNames(), dbCollection.db) && contains(c.getDatabase(dbCollection.db).listCollectionNames(), dbCollection.collection)) {
                c.getDatabase(dbCollection.db).getCollection(dbCollection.collection).drop();
                log.debug("dropped collection {}", dbCollection);
            }
        }
        for (String db : this.testDBs) {
            if (contains(c.listDatabaseNames(), db)) {
                c.getDatabase(db).drop();
                log.debug("dropped db {}", db);
            }
        }

        for (MongoClient client : this.clients) {
            client.close();
        }
    }

    private static <T> boolean contains(Iterable<T> iterable, T elmt) {
        for (T v : iterable) {
            if (v.equals(elmt)) return true;
        }
        return false;
    }

    public MongoResource importCollectionContent(String exportResource, String db, String collection) {
        this.collectionImports.add(new CollectionImport(db, collection, exportResource));
        return this;
    }


    class DbCollection {
        public final String db;
        public final String collection;

        public DbCollection(String db, String collection) {
            this.db = db;
            this.collection = collection;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DbCollection that = (DbCollection) o;

            if (db != null ? !db.equals(that.db) : that.db != null) return false;
            return collection != null ? collection.equals(that.collection) : that.collection == null;
        }

        @Override
        public int hashCode() {
            int result = db != null ? db.hashCode() : 0;
            result = 31 * result + (collection != null ? collection.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "DbCollection{" +
                    "db='" + db + '\'' +
                    ", collection='" + collection + '\'' +
                    '}';
        }
    }

    class CollectionImport {
        public final String db;
        public final String collection;
        public final String mongoExportResource;

        public CollectionImport(String db, String collection, String mongoExportResource) {
            this.db = db;
            this.collection = collection;
            this.mongoExportResource = mongoExportResource;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CollectionImport that = (CollectionImport) o;

            if (db != null ? !db.equals(that.db) : that.db != null) return false;
            if (collection != null ? !collection.equals(that.collection) : that.collection != null) return false;
            return mongoExportResource != null ? mongoExportResource.equals(that.mongoExportResource) : that.mongoExportResource == null;
        }

        @Override
        public int hashCode() {
            int result = db != null ? db.hashCode() : 0;
            result = 31 * result + (collection != null ? collection.hashCode() : 0);
            result = 31 * result + (mongoExportResource != null ? mongoExportResource.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "CollectionImport{" +
                    "db='" + db + '\'' +
                    ", collection='" + collection + '\'' +
                    ", mongoExportResource='" + mongoExportResource + '\'' +
                    '}';
        }
    }

    class DBImport {

        private final DumpReader dumpReader;
        private final String dbName;

        public DBImport(DumpReader dumpReader, String dbName) {
            this.dumpReader = dumpReader;
            this.dbName = dbName;
        }

        public void doImport(MongoClient client) throws IOException {
            new DatabaseRestorer(client, this.dumpReader).restoreTo(this.dbName);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DBImport dbImport = (DBImport) o;
            return Objects.equals(dumpReader, dbImport.dumpReader) &&
                    Objects.equals(dbName, dbImport.dbName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dumpReader, dbName);
        }

        @Override
        public String toString() {
            return "DBImport{" +
                    "dumpReader=" + dumpReader +
                    ", dbName='" + dbName + '\'' +
                    '}';
        }
    }
}
