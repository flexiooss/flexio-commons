package io.flexio.docker.api.types.container.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.flexio.docker.api.types.container.State;
import io.flexio.docker.api.types.container.State.Builder;
import java.io.IOException;
import java.lang.FunctionalInterface;
import java.lang.IllegalArgumentException;
import java.lang.String;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StateReader {
  private static Set STATUS_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set RUNNING_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_FALSE, JsonToken.VALUE_TRUE));

  private static Set PAUSED_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_FALSE, JsonToken.VALUE_TRUE));

  private static Set RESTARTING_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_FALSE, JsonToken.VALUE_TRUE));

  private static Set DEAD_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_FALSE, JsonToken.VALUE_TRUE));

  public State read(JsonParser parser) throws IOException {
    if(parser.getCurrentToken() == null) {
      parser.nextToken();
    }
    if(parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if(parser.currentToken() != JsonToken.START_OBJECT) {
      throw new IOException(
                  String.format("reading a %s object, was expecting %s, but was %s",
                          State.class.getName(), JsonToken.START_OBJECT, parser.currentToken()
                  )
          );
    }
    State.Builder builder = State.builder();
    while (parser.nextToken() != JsonToken.END_OBJECT) {
      Token token = Token.from(parser.getCurrentName());
      if(token != null) {
        switch (token) {
          case STATUS: {
            Set<JsonToken> expectedTokens = STATUS_EXPECTEDTOKENS;
            try {
              builder.status(this.readValue(parser, jsonParser -> State.Status.valueOf(jsonParser.getText()), "status", expectedTokens));
            } catch(IllegalArgumentException e) {
              throw new IOException("error reading enum property status, value is not one of the enum constants.", e);
            }
            break;
          }
          case RUNNING: {
            Set<JsonToken> expectedTokens = RUNNING_EXPECTEDTOKENS;
            builder.running(this.readValue(parser, jsonParser -> jsonParser.getBooleanValue(), "running", expectedTokens));
            break;
          }
          case PAUSED: {
            Set<JsonToken> expectedTokens = PAUSED_EXPECTEDTOKENS;
            builder.paused(this.readValue(parser, jsonParser -> jsonParser.getBooleanValue(), "paused", expectedTokens));
            break;
          }
          case RESTARTING: {
            Set<JsonToken> expectedTokens = RESTARTING_EXPECTEDTOKENS;
            builder.restarting(this.readValue(parser, jsonParser -> jsonParser.getBooleanValue(), "restarting", expectedTokens));
            break;
          }
          case DEAD: {
            Set<JsonToken> expectedTokens = DEAD_EXPECTEDTOKENS;
            builder.dead(this.readValue(parser, jsonParser -> jsonParser.getBooleanValue(), "dead", expectedTokens));
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

  public State[] readArray(JsonParser parser) throws IOException {
    parser.nextToken();
    if (parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if (parser.currentToken() == JsonToken.START_ARRAY) {
      LinkedList<State> listValue = new LinkedList<>();
      while (parser.nextToken() != JsonToken.END_ARRAY) {
        if(parser.currentToken() == JsonToken.VALUE_NULL) {
          listValue.add(null);
        } else {
          listValue.add(this.read(parser));
        }
      }
      return listValue.toArray(new State[listValue.size()]);
    }
    throw new IOException(String.format("failed reading io.flexio.docker.api.types.container.State array, current token was %s", parser.currentToken()));
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

    STATUS("status", "Status"),

    RUNNING("running", "Running"),

    PAUSED("paused", "Paused"),

    RESTARTING("restarting", "Restarting"),

    DEAD("dead", "Dead");

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
