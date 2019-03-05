package io.flexio.io.mongo.repository.domain;

import io.flexio.io.mongo.repository.domain.optional.OptionalMongoValue;
import java.lang.String;

public interface MongoValue {
  static Builder builder() {
    return new MongoValue.Builder();
  }

  static MongoValue.Builder from(MongoValue value) {
    if(value != null) {
      return new MongoValue.Builder()
          .name(value.name())
          .slug(value.slug())
          ;
    }
    else {
      return null;
    }
  }

  String name();

  String slug();

  MongoValue withName(String value);

  MongoValue withSlug(String value);

  int hashCode();

  MongoValue changed(MongoValue.Changer changer);

  OptionalMongoValue opt();

  class Builder {
    private String name;

    private String slug;

    public MongoValue build() {
      return new MongoValueImpl(this.name, this.slug);
    }

    public MongoValue.Builder name(String name) {
      this.name = name;
      return this;
    }

    public MongoValue.Builder slug(String slug) {
      this.slug = slug;
      return this;
    }
  }

  interface Changer {
    MongoValue.Builder configure(MongoValue.Builder builder);
  }
}
