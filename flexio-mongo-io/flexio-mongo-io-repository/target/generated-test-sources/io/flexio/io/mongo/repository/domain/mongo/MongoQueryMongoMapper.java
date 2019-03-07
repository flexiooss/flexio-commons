package io.flexio.io.mongo.repository.domain.mongo;

import io.flexio.io.mongo.repository.domain.MongoQuery;
import java.lang.String;
import org.bson.Document;

public class MongoQueryMongoMapper {
  public MongoQuery toValue(Document document) {
    MongoQuery.Builder builder = MongoQuery.builder();
    if(document.get("name") != null) {
      builder.name(new String(document.get("name").toString()));
    }
    return builder.build();
  }

  public Document toDocument(MongoQuery value) {
    Document document = new Document();
    if(value.name() != null) {
      document.put("name", value.name());
    } else {
      document.put("name", null);
    }
    return document;
  }
}
