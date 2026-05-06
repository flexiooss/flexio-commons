# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Build entire project (requires Docker for tests)
mvn clean install

# Build without tests
mvn clean install -DskipTests

# Run all tests
mvn test

# Run single test class
mvn test -Dtest=PropertyQuerierTest

# Run single test method
mvn test -Dtest=PropertyQuerierTest#givenSortQuery__thenSortIsReturned

# Build specific module
mvn clean install -pl flexio-mongo-io/flexio-mongo-io-repository
```

**Note**: Tests require Docker for MongoDB and MariaDB containers.

## Project Overview

**flexio-commons** is an infrastructure library providing repository implementations for multiple storage backends (MongoDB, MySQL, Redis) plus utilities for Docker testing, ETag support, and external service clients.

Parent: `org.codingmatters.poom:poom-services`

## Module Structure

| Module | Purpose |
|--------|---------|
| `flexio-mongo-io` | MongoDB repository framework with PropertyQuery DSL (has its own CLAUDE.md) |
| `flexio-docker-client` | Docker SDK wrapper for container lifecycle in tests |
| `flexio-mongo-tests` | JUnit rules for Docker-based MongoDB testing |
| `poom-services-io` | MySQL and Redis repository implementations |
| `poom-etag-support` | ETag handling for REST API conditional requests |
| `flexio-opentsdb-client` | OpenTSDB metrics client |
| `flexio-graylog-client` | Graylog REST client |
| `flexio-svg-generator` | SVG generation utilities |
| `flexio-services-archetype` | Maven archetype for new services |

## Architecture

### Repository Pattern

All repository implementations share the `Repository<V, Q>` interface from `poom-services-domain`:
- `V` = Value type (domain object)
- `Q` = Query type (custom or PropertyQuery)

Operations: CRUD + pagination + filtering/sorting

### Backend Implementations

**MongoDB** (`flexio-mongo-io/flexio-mongo-io-repository`):
```java
MongoCollectionRepository.<MyValue, PropertyQuery>repository("db", "collection")
    .withToDocument(mapper::toDocument)
    .withToValue(mapper::toValue)
    .buildWithPropertyQuery(mongoClient);
```

**MySQL** (`poom-services-io/poom-services-mysql-io`):
- JSON-based storage using Jackson
- `MySQLJsonRepository<V, Q>` implementation

**Redis** (`poom-services-io/poom-services-redis-io`):
- Key-value repository via Jedis
- Caching layer in `poom-services-redis-io-cache`

### Code Generation

YAML specs drive code generation via Maven plugins:
1. `cdm-value-objects-maven-plugin` → Value classes
2. `flexio-mongo-io-maven-plugin` → MongoDB mappers (`*MongoMapper` in `.mongo` subpackage)

Mappers provide `toDocument(V)` and `toValue(Document)` methods.

### PropertyQuery DSL

`PropertyQuerier` translates queries like `"name == 'John' && age > 30"` to BSON:
- `BsonFilterEvents` → MongoDB `Filters.eq()`, `Filters.gt()`, etc.
- `BsonSortEvents` → MongoDB `Sorts.ascending()`, `Sorts.descending()`
- Configure ObjectId-capable fields via `MongoFilterConfig.potentialOids`
- ZonedDateTime fields use `.ts` suffix in queries (e.g., `myField.ts`)

### Optimistic Locking

MongoDB repository supports optimistic locking via `__version` field:
```java
.build(mongoClient, true)  // Enable versioning
```

### Testing Infrastructure

Docker-based test fixtures:
```java
@ClassRule
static DockerResource docker = MongoTest.docker().started().finallyStarted();

@Rule
public MongoResource mongo = MongoTest.mongo(() -> docker)
    .testDB("test-db")
    .importCollectionContent("fixture.json", "test-db", "collection");
```

MariaDB testing via `poom-services-mysql-io-test` module.

### MongoProvider Configuration

Environment variables for MongoDB connections:
```
MONGO_URL                  - Full connection string (preferred)
MONGO_HOST / MONGO_PORT    - Alternative to URL
MONGO_CONNECTION_TIMEOUT   - Connection timeout ms (default: 2000)
MONGO_SELECT_TIMEOUT       - Server selection timeout ms (default: 2000)
```

Supports prefix for multiple connections: `MongoProvider.fromEnv("PREFIX_")`

### ETag Support

Wrap handlers with `ETaggedRead` (GET/HEAD) or `ETaggedChange` (PUT/PATCH):
```java
new ETaggedRead<>(etags, "must-revalidate", getHandler, ResourceGetResponse.class)
```

Calculate ETags with `HashProcessor`:
```java
String etag = new HashProcessor().hash(value.toMap())
```