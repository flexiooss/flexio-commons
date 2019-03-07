package io.flexio.io.mongo.repository.domain;

import io.flexio.io.mongo.repository.domain.optional.OptionalMongoQuery;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class MongoQueryImpl implements MongoQuery {
  private final String name;

  MongoQueryImpl(String name) {
    this.name = name;
  }

  public String name() {
    return this.name;
  }

  public MongoQuery withName(String value) {
    return MongoQuery.from(this).name(value).build();
  }

  public MongoQuery changed(MongoQuery.Changer changer) {
    return changer.configure(MongoQuery.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MongoQueryImpl that = (MongoQueryImpl) o;
        return Objects.equals(this.name, that.name);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.name});
  }

  @Override
  public String toString() {
    return "MongoQuery{" +
        "name=" + Objects.toString(this.name) +
        '}';
  }

  public OptionalMongoQuery opt() {
    return OptionalMongoQuery.of(this);
  }
}
