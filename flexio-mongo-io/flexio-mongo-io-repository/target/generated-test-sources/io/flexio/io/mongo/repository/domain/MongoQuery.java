package io.flexio.io.mongo.repository.domain;

import io.flexio.io.mongo.repository.domain.optional.OptionalMongoQuery;
import java.lang.String;

public interface MongoQuery {
  static Builder builder() {
    return new MongoQuery.Builder();
  }

  static MongoQuery.Builder from(MongoQuery value) {
    if(value != null) {
      return new MongoQuery.Builder()
          .name(value.name())
          ;
    }
    else {
      return null;
    }
  }

  String name();

  MongoQuery withName(String value);

  int hashCode();

  MongoQuery changed(MongoQuery.Changer changer);

  OptionalMongoQuery opt();

  class Builder {
    private String name;

    public MongoQuery build() {
      return new MongoQueryImpl(this.name);
    }

    public MongoQuery.Builder name(String name) {
      this.name = name;
      return this;
    }
  }

  interface Changer {
    MongoQuery.Builder configure(MongoQuery.Builder builder);
  }
}
