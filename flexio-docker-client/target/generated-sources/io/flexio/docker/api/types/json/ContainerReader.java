package io.flexio.docker.api.types.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.flexio.docker.api.types.Container;
import io.flexio.docker.api.types.Container.Builder;
import io.flexio.docker.api.types.container.json.NetworkSettingsReader;
import io.flexio.docker.api.types.container.json.StateReader;
import java.io.IOException;
import java.lang.FunctionalInterface;
import java.lang.String;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ContainerReader {
  private static Set ID_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set IMAGE_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  public Container read(JsonParser parser) throws IOException {
    if(parser.getCurrentToken() == null) {
      parser.nextToken();
    }
    if(parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if(parser.currentToken() != JsonToken.START_OBJECT) {
      throw new IOException(
                  String.format("reading a %s object, was expecting %s, but was %s",
                          Container.class.getName(), JsonToken.START_OBJECT, parser.currentToken()
                  )
          );
    }
    Container.Builder builder = Container.builder();
    while (parser.nextToken() != JsonToken.END_OBJECT) {
      Token token = Token.from(parser.getCurrentName());
      if(token != null) {
        switch (token) {
          case ID: {
            Set<JsonToken> expectedTokens = ID_EXPECTEDTOKENS;
            builder.id(this.readValue(parser, jsonParser -> jsonParser.getText(), "id", expectedTokens));
            break;
          }
          case NAMES: {
            builder.names(this.readListValue(parser, jsonParser -> jsonParser.getText(), "names"));
            break;
          }
          case IMAGE: {
            Set<JsonToken> expectedTokens = IMAGE_EXPECTEDTOKENS;
            builder.image(this.readValue(parser, jsonParser -> jsonParser.getText(), "image", expectedTokens));
            break;
          }
          case STATE: {
            parser.nextToken();
            builder.state(new StateReader().read(parser));
            break;
          }
          case NETWORKSETTINGS: {
            parser.nextToken();
            builder.networkSettings(new NetworkSettingsReader().read(parser));
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

  public Container[] readArray(JsonParser parser) throws IOException {
    parser.nextToken();
    if (parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if (parser.currentToken() == JsonToken.START_ARRAY) {
      LinkedList<Container> listValue = new LinkedList<>();
      while (parser.nextToken() != JsonToken.END_ARRAY) {
        if(parser.currentToken() == JsonToken.VALUE_NULL) {
          listValue.add(null);
        } else {
          listValue.add(this.read(parser));
        }
      }
      return listValue.toArray(new Container[listValue.size()]);
    }
    throw new IOException(String.format("failed reading io.flexio.docker.api.types.Container array, current token was %s", parser.currentToken()));
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

    ID("id", "Id"),

    NAMES("names", "Names"),

    IMAGE("image", "Image"),

    STATE("state", "state"),

    NETWORKSETTINGS("networkSettings", "networkSettings");

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
