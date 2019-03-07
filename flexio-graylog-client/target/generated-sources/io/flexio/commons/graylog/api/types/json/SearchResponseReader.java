package io.flexio.commons.graylog.api.types.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.flexio.commons.graylog.api.types.SearchResponse;
import io.flexio.commons.graylog.api.types.SearchResponse.Builder;
import java.io.IOException;
import java.lang.FunctionalInterface;
import java.lang.String;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.codingmatters.value.objects.values.json.ObjectValueReader;

public class SearchResponseReader {
  private static Set FROM_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set TO_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set TIME_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_NUMBER_INT));

  private static Set BUILT_QUERY_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set TOTAL_RESULTS_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_NUMBER_INT));

  private static Set QUERY_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  public SearchResponse read(JsonParser parser) throws IOException {
    if(parser.getCurrentToken() == null) {
      parser.nextToken();
    }
    if(parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if(parser.currentToken() != JsonToken.START_OBJECT) {
      throw new IOException(
                  String.format("reading a %s object, was expecting %s, but was %s",
                          SearchResponse.class.getName(), JsonToken.START_OBJECT, parser.currentToken()
                  )
          );
    }
    SearchResponse.Builder builder = SearchResponse.builder();
    while (parser.nextToken() != JsonToken.END_OBJECT) {
      Token token = Token.from(parser.getCurrentName());
      if(token != null) {
        switch (token) {
          case FROM: {
            Set<JsonToken> expectedTokens = FROM_EXPECTEDTOKENS;
            builder.from(this.readValue(parser, jsonParser -> jsonParser.getText(), "from", expectedTokens));
            break;
          }
          case TO: {
            Set<JsonToken> expectedTokens = TO_EXPECTEDTOKENS;
            builder.to(this.readValue(parser, jsonParser -> jsonParser.getText(), "to", expectedTokens));
            break;
          }
          case MESSAGES: {
            ObjectValueReader reader = new ObjectValueReader();
            builder.messages(this.readListValue(parser, jsonParser -> reader.read(jsonParser), "messages"));
            break;
          }
          case FIELDS: {
            builder.fields(this.readListValue(parser, jsonParser -> jsonParser.getText(), "fields"));
            break;
          }
          case TIME: {
            Set<JsonToken> expectedTokens = TIME_EXPECTEDTOKENS;
            builder.time(this.readValue(parser, jsonParser -> jsonParser.getLongValue(), "time", expectedTokens));
            break;
          }
          case BUILT_QUERY: {
            Set<JsonToken> expectedTokens = BUILT_QUERY_EXPECTEDTOKENS;
            builder.built_query(this.readValue(parser, jsonParser -> jsonParser.getText(), "built_query", expectedTokens));
            break;
          }
          case DECORATION_STATS: {
            parser.nextToken();
            builder.decoration_stats(new ObjectValueReader().read(parser));
            break;
          }
          case TOTAL_RESULTS: {
            Set<JsonToken> expectedTokens = TOTAL_RESULTS_EXPECTEDTOKENS;
            builder.total_results(this.readValue(parser, jsonParser -> jsonParser.getLongValue(), "total_results", expectedTokens));
            break;
          }
          case USED_INDICES: {
            ObjectValueReader reader = new ObjectValueReader();
            builder.used_indices(this.readListValue(parser, jsonParser -> reader.read(jsonParser), "used_indices"));
            break;
          }
          case QUERY: {
            Set<JsonToken> expectedTokens = QUERY_EXPECTEDTOKENS;
            builder.query(this.readValue(parser, jsonParser -> jsonParser.getText(), "query", expectedTokens));
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

  public SearchResponse[] readArray(JsonParser parser) throws IOException {
    parser.nextToken();
    if (parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if (parser.currentToken() == JsonToken.START_ARRAY) {
      LinkedList<SearchResponse> listValue = new LinkedList<>();
      while (parser.nextToken() != JsonToken.END_ARRAY) {
        if(parser.currentToken() == JsonToken.VALUE_NULL) {
          listValue.add(null);
        } else {
          listValue.add(this.read(parser));
        }
      }
      return listValue.toArray(new SearchResponse[listValue.size()]);
    }
    throw new IOException(String.format("failed reading io.flexio.commons.graylog.api.types.SearchResponse array, current token was %s", parser.currentToken()));
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

    FROM("from", "from"),

    TO("to", "to"),

    MESSAGES("messages", "messages"),

    FIELDS("fields", "fields"),

    TIME("time", "time"),

    BUILT_QUERY("built_query", "built_query"),

    DECORATION_STATS("decoration_stats", "decoration_stats"),

    TOTAL_RESULTS("total_results", "total_results"),

    USED_INDICES("used_indices", "used_indices"),

    QUERY("query", "query");

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
