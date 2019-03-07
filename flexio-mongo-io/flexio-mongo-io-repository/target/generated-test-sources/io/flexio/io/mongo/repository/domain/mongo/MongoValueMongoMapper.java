package io.flexio.io.mongo.repository.domain.mongo;

import io.flexio.io.mongo.repository.domain.MongoValue;
import java.lang.String;
import org.bson.Document;

public class MongoValueMongoMapper {
  public MongoValue toValue(Document document) {
    MongoValue.Builder builder = MongoValue.builder();
    if(document.get("name") != null) {
      builder.name(new String(document.get("name").toString()));
    }
    if(document.get("slug") != null) {
      builder.slug(new String(document.get("slug").toString()));
    }
    return builder.build();
  }

  public Document toDocument(MongoValue value) {
    Document document = new Document();
    if(value.name() != null) {
      document.put("name", value.name());
    } else {
      document.put("name", null);
    }
    if(value.slug() != null) {
      document.put("slug", value.slug());
    } else {
      document.put("slug", null);
    }
    return document;
  }
}
