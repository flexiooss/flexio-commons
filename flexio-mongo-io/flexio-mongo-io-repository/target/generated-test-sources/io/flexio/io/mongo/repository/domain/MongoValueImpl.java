package io.flexio.io.mongo.repository.domain;

import io.flexio.io.mongo.repository.domain.optional.OptionalMongoValue;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.Objects;

class MongoValueImpl implements MongoValue {
  private final String name;

  private final String slug;

  MongoValueImpl(String name, String slug) {
    this.name = name;
    this.slug = slug;
  }

  public String name() {
    return this.name;
  }

  public String slug() {
    return this.slug;
  }

  public MongoValue withName(String value) {
    return MongoValue.from(this).name(value).build();
  }

  public MongoValue withSlug(String value) {
    return MongoValue.from(this).slug(value).build();
  }

  public MongoValue changed(MongoValue.Changer changer) {
    return changer.configure(MongoValue.from(this)).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MongoValueImpl that = (MongoValueImpl) o;
        return Objects.equals(this.name, that.name) && 
        Objects.equals(this.slug, that.slug);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[]{this.name, this.slug});
  }

  @Override
  public String toString() {
    return "MongoValue{" +
        "name=" + Objects.toString(this.name) +
        ", " + "slug=" + Objects.toString(this.slug) +
        '}';
  }

  public OptionalMongoValue opt() {
    return OptionalMongoValue.of(this);
  }
}
