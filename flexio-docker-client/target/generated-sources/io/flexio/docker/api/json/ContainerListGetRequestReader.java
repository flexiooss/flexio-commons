package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.flexio.docker.api.ContainerListGetRequest;
import io.flexio.docker.api.ContainerListGetRequest.Builder;
import java.io.IOException;
import java.lang.FunctionalInterface;
import java.lang.String;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ContainerListGetRequestReader {
  private static Set ALL_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_TRUE, JsonToken.VALUE_FALSE));

  private static Set LIMIT_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_NUMBER_INT));

  private static Set SIZE_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_NUMBER_INT));

  private static Set FILTERS_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  public ContainerListGetRequest read(JsonParser parser) throws IOException {
    if(parser.getCurrentToken() == null) {
      parser.nextToken();
    }
    if(parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if(parser.currentToken() != JsonToken.START_OBJECT) {
      throw new IOException(
                  String.format("reading a %s object, was expecting %s, but was %s",
                          ContainerListGetRequest.class.getName(), JsonToken.START_OBJECT, parser.currentToken()
                  )
          );
    }
    ContainerListGetRequest.Builder builder = ContainerListGetRequest.builder();
    while (parser.nextToken() != JsonToken.END_OBJECT) {
      Token token = Token.from(parser.getCurrentName());
      if(token != null) {
        switch (token) {
          case ALL: {
            Set<JsonToken> expectedTokens = ALL_EXPECTEDTOKENS;
            builder.all(this.readValue(parser, jsonParser -> jsonParser.getBooleanValue(), "all", expectedTokens));
            break;
          }
          case LIMIT: {
            Set<JsonToken> expectedTokens = LIMIT_EXPECTEDTOKENS;
            builder.limit(this.readValue(parser, jsonParser -> jsonParser.getLongValue(), "limit", expectedTokens));
            break;
          }
          case SIZE: {
            Set<JsonToken> expectedTokens = SIZE_EXPECTEDTOKENS;
            builder.size(this.readValue(parser, jsonParser -> jsonParser.getLongValue(), "size", expectedTokens));
            break;
          }
          case FILTERS: {
            Set<JsonToken> expectedTokens = FILTERS_EXPECTEDTOKENS;
            builder.filters(this.readValue(parser, jsonParser -> jsonParser.getText(), "filters", expectedTokens));
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

  public ContainerListGetRequest[] readArray(JsonParser parser) throws IOException {
    parser.nextToken();
    if (parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if (parser.currentToken() == JsonToken.START_ARRAY) {
      LinkedList<ContainerListGetRequest> listValue = new LinkedList<>();
      while (parser.nextToken() != JsonToken.END_ARRAY) {
        if(parser.currentToken() == JsonToken.VALUE_NULL) {
          listValue.add(null);
        } else {
          listValue.add(this.read(parser));
        }
      }
      return listValue.toArray(new ContainerListGetRequest[listValue.size()]);
    }
    throw new IOException(String.format("failed reading io.flexio.docker.api.ContainerListGetRequest array, current token was %s", parser.currentToken()));
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

    ALL("all", "all"),

    LIMIT("limit", "limit"),

    SIZE("size", "size"),

    FILTERS("filters", "filters");

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
