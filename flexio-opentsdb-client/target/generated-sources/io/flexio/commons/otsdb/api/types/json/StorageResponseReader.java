package io.flexio.commons.otsdb.api.types.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.flexio.commons.otsdb.api.types.StorageResponse;
import io.flexio.commons.otsdb.api.types.StorageResponse.Builder;
import java.io.IOException;
import java.lang.FunctionalInterface;
import java.lang.String;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StorageResponseReader {
  private static Set SUCCESS_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_NUMBER_INT));

  private static Set FAILED_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_NUMBER_INT));

  public StorageResponse read(JsonParser parser) throws IOException {
    if(parser.getCurrentToken() == null) {
      parser.nextToken();
    }
    if(parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if(parser.currentToken() != JsonToken.START_OBJECT) {
      throw new IOException(
                  String.format("reading a %s object, was expecting %s, but was %s",
                          StorageResponse.class.getName(), JsonToken.START_OBJECT, parser.currentToken()
                  )
          );
    }
    StorageResponse.Builder builder = StorageResponse.builder();
    while (parser.nextToken() != JsonToken.END_OBJECT) {
      Token token = Token.from(parser.getCurrentName());
      if(token != null) {
        switch (token) {
          case SUCCESS: {
            Set<JsonToken> expectedTokens = SUCCESS_EXPECTEDTOKENS;
            builder.success(this.readValue(parser, jsonParser -> jsonParser.getLongValue(), "success", expectedTokens));
            break;
          }
          case FAILED: {
            Set<JsonToken> expectedTokens = FAILED_EXPECTEDTOKENS;
            builder.failed(this.readValue(parser, jsonParser -> jsonParser.getLongValue(), "failed", expectedTokens));
            break;
          }
          case ERRORS: {
            DataPointErrorReader reader = new DataPointErrorReader();
            builder.errors(this.readListValue(parser, jsonParser -> reader.read(jsonParser), "errors"));
            break;
          }
          default: {
            this.consumeUnexpectedProperty(parser);
          }
        }
      } else {
        this.consumeUnexpectedProperty(parser);
      }
    }
    return builder.build();
  }

  public StorageResponse[] readArray(JsonParser parser) throws IOException {
    parser.nextToken();
    if (parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if (parser.currentToken() == JsonToken.START_ARRAY) {
      LinkedList<StorageResponse> listValue = new LinkedList<>();
      while (parser.nextToken() != JsonToken.END_ARRAY) {
        if(parser.currentToken() == JsonToken.VALUE_NULL) {
          listValue.add(null);
        } else {
          listValue.add(this.read(parser));
        }
      }
      return listValue.toArray(new StorageResponse[listValue.size()]);
    }
    throw new IOException(String.format("failed reading io.flexio.commons.otsdb.api.types.StorageResponse array, current token was %s", parser.currentToken()));
  }

  private <T> T readValue(JsonParser parser, Reader<T> reader, String propertyName, Set<JsonToken> expectedTokens) throws IOException {
    parser.nextToken();
    if (parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if (expectedTokens.contains(parser.currentToken())) return reader.read(parser);
    throw new IOException(
            String.format("reading property %s, was expecting %s, but was %s",
                propertyName, expectedTokens, parser.currentToken()
            )
        );
  }

  private <T> List<T> readListValue(JsonParser parser, Reader<T> reader, String propertyName) throws IOException {
    parser.nextToken();
    if (parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if (parser.currentToken() == JsonToken.START_ARRAY) {
      LinkedList<T> listValue = new LinkedList<>();
      while (parser.nextToken() != JsonToken.END_ARRAY) {
        if(parser.currentToken() == JsonToken.VALUE_NULL) {
          listValue.add(null);
        } else {
          listValue.add(reader.read(parser));
        }
      }
      return listValue;
    }
    throw new IOException(
                String.format("reading property %s, was expecting %s, but was %s",
                        propertyName, JsonToken.START_ARRAY, parser.currentToken()
                )
        );
  }

  private void consumeUnexpectedProperty(JsonParser parser) throws IOException {
    parser.nextToken();
    if(parser.currentToken().isStructStart()) {
      int level = 1;
      do {
        parser.nextToken();
        if (parser.currentToken().isStructStart()) {
          level++;
        } if (parser.currentToken().isStructEnd()) {
          level--;
        }
      } while(level > 0);
    }
  }

  @FunctionalInterface
  private interface Reader<T> {
    T read(JsonParser parser) throws IOException;
  }

  enum Token {
    __UNKNOWN__("__UNKNOWN__", "__UNKNOWN__"),

    SUCCESS("success", "success"),

    FAILED("failed", "failed"),

    ERRORS("errors", "errors");

    private final String name;

    private final String rawName;

    Token(String name, String rawName) {
      this.name = name;
      this.rawName = rawName;
    }

    private static String normalizeFieldName(String fieldName) {
      if(fieldName == null) return null;
      if(fieldName.trim().equals("")) return "";
      fieldName = Arrays.stream(fieldName.split("(\\s|-)+")).map(s -> s.substring(0, 1).toUpperCase() + s.substring(1)).collect(Collectors.joining());
      fieldName =  fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
      return fieldName;
    }

    public static Token from(String str) {
      for(Token token : Token.values()) {
        if(token.name.equals(str)) {
          return token;
        } else if(token.rawName.equals(str)) {
          return token;
        } else if(token.name.equals(normalizeFieldName(str))) {
          return token;
        } else if(token.rawName.equals(normalizeFieldName(str))) {
          return token;
        }
      }
      return __UNKNOWN__;
    }
  }
}
