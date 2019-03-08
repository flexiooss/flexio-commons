package io.flexio.commons.graylog.api.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.flexio.commons.graylog.api.RelativeGetRequest;
import io.flexio.commons.graylog.api.RelativeGetRequest.Builder;
import java.io.IOException;
import java.lang.FunctionalInterface;
import java.lang.String;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RelativeGetRequestReader {
  private static Set QUERY_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set RANGE_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set LIMIT_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_NUMBER_INT));

  private static Set OFFSET_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_NUMBER_INT));

  private static Set FILTER_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set SORT_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set DECORATE_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_TRUE, JsonToken.VALUE_FALSE));

  private static Set ACCEPT_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set AUTHORIZATION_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  public RelativeGetRequest read(JsonParser parser) throws IOException {
    if(parser.getCurrentToken() == null) {
      parser.nextToken();
    }
    if(parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if(parser.currentToken() != JsonToken.START_OBJECT) {
      throw new IOException(
                  String.format("reading a %s object, was expecting %s, but was %s",
                          RelativeGetRequest.class.getName(), JsonToken.START_OBJECT, parser.currentToken()
                  )
          );
    }
    RelativeGetRequest.Builder builder = RelativeGetRequest.builder();
    while (parser.nextToken() != JsonToken.END_OBJECT) {
      Token token = Token.from(parser.getCurrentName());
      if(token != null) {
        switch (token) {
          case QUERY: {
            Set<JsonToken> expectedTokens = QUERY_EXPECTEDTOKENS;
            builder.query(this.readValue(parser, jsonParser -> jsonParser.getText(), "query", expectedTokens));
            break;
          }
          case RANGE: {
            Set<JsonToken> expectedTokens = RANGE_EXPECTEDTOKENS;
            builder.range(this.readValue(parser, jsonParser -> jsonParser.getText(), "range", expectedTokens));
            break;
          }
          case LIMIT: {
            Set<JsonToken> expectedTokens = LIMIT_EXPECTEDTOKENS;
            builder.limit(this.readValue(parser, jsonParser -> jsonParser.getLongValue(), "limit", expectedTokens));
            break;
          }
          case OFFSET: {
            Set<JsonToken> expectedTokens = OFFSET_EXPECTEDTOKENS;
            builder.offset(this.readValue(parser, jsonParser -> jsonParser.getLongValue(), "offset", expectedTokens));
            break;
          }
          case FILTER: {
            Set<JsonToken> expectedTokens = FILTER_EXPECTEDTOKENS;
            builder.filter(this.readValue(parser, jsonParser -> jsonParser.getText(), "filter", expectedTokens));
            break;
          }
          case FIELDS: {
            builder.fields(this.readListValue(parser, jsonParser -> jsonParser.getText(), "fields"));
            break;
          }
          case SORT: {
            Set<JsonToken> expectedTokens = SORT_EXPECTEDTOKENS;
            builder.sort(this.readValue(parser, jsonParser -> jsonParser.getText(), "sort", expectedTokens));
            break;
          }
          case DECORATE: {
            Set<JsonToken> expectedTokens = DECORATE_EXPECTEDTOKENS;
            builder.decorate(this.readValue(parser, jsonParser -> jsonParser.getBooleanValue(), "decorate", expectedTokens));
            break;
          }
          case ACCEPT: {
            Set<JsonToken> expectedTokens = ACCEPT_EXPECTEDTOKENS;
            builder.accept(this.readValue(parser, jsonParser -> jsonParser.getText(), "accept", expectedTokens));
            break;
          }
          case AUTHORIZATION: {
            Set<JsonToken> expectedTokens = AUTHORIZATION_EXPECTEDTOKENS;
            builder.authorization(this.readValue(parser, jsonParser -> jsonParser.getText(), "authorization", expectedTokens));
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

  public RelativeGetRequest[] readArray(JsonParser parser) throws IOException {
    parser.nextToken();
    if (parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if (parser.currentToken() == JsonToken.START_ARRAY) {
      LinkedList<RelativeGetRequest> listValue = new LinkedList<>();
      while (parser.nextToken() != JsonToken.END_ARRAY) {
        if(parser.currentToken() == JsonToken.VALUE_NULL) {
          listValue.add(null);
        } else {
          listValue.add(this.read(parser));
        }
      }
      return listValue.toArray(new RelativeGetRequest[listValue.size()]);
    }
    throw new IOException(String.format("failed reading io.flexio.commons.graylog.api.RelativeGetRequest array, current token was %s", parser.currentToken()));
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

    QUERY("query", "query"),

    RANGE("range", "range"),

    LIMIT("limit", "limit"),

    OFFSET("offset", "offset"),

    FILTER("filter", "filter"),

    FIELDS("fields", "fields"),

    SORT("sort", "sort"),

    DECORATE("decorate", "decorate"),

    ACCEPT("accept", "accept"),

    AUTHORIZATION("authorization", "authorization");

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
