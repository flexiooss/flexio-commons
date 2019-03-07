package io.flexio.docker.api.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.flexio.docker.api.CreateContainerPostResponse;
import io.flexio.docker.api.CreateContainerPostResponse.Builder;
import io.flexio.docker.api.createcontainerpostresponse.json.Status201Reader;
import io.flexio.docker.api.createcontainerpostresponse.json.Status400Reader;
import io.flexio.docker.api.createcontainerpostresponse.json.Status404Reader;
import io.flexio.docker.api.createcontainerpostresponse.json.Status409Reader;
import io.flexio.docker.api.createcontainerpostresponse.json.Status500Reader;
import java.io.IOException;
import java.lang.FunctionalInterface;
import java.lang.String;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateContainerPostResponseReader {
  public CreateContainerPostResponse read(JsonParser parser) throws IOException {
    if(parser.getCurrentToken() == null) {
      parser.nextToken();
    }
    if(parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if(parser.currentToken() != JsonToken.START_OBJECT) {
      throw new IOException(
                  String.format("reading a %s object, was expecting %s, but was %s",
                          CreateContainerPostResponse.class.getName(), JsonToken.START_OBJECT, parser.currentToken()
                  )
          );
    }
    CreateContainerPostResponse.Builder builder = CreateContainerPostResponse.builder();
    while (parser.nextToken() != JsonToken.END_OBJECT) {
      Token token = Token.from(parser.getCurrentName());
      if(token != null) {
        switch (token) {
          case STATUS201: {
            parser.nextToken();
            builder.status201(new Status201Reader().read(parser));
            break;
          }
          case STATUS400: {
            parser.nextToken();
            builder.status400(new Status400Reader().read(parser));
            break;
          }
          case STATUS404: {
            parser.nextToken();
            builder.status404(new Status404Reader().read(parser));
            break;
          }
          case STATUS409: {
            parser.nextToken();
            builder.status409(new Status409Reader().read(parser));
            break;
          }
          case STATUS500: {
            parser.nextToken();
            builder.status500(new Status500Reader().read(parser));
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

  public CreateContainerPostResponse[] readArray(JsonParser parser) throws IOException {
    parser.nextToken();
    if (parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if (parser.currentToken() == JsonToken.START_ARRAY) {
      LinkedList<CreateContainerPostResponse> listValue = new LinkedList<>();
      while (parser.nextToken() != JsonToken.END_ARRAY) {
        if(parser.currentToken() == JsonToken.VALUE_NULL) {
          listValue.add(null);
        } else {
          listValue.add(this.read(parser));
        }
      }
      return listValue.toArray(new CreateContainerPostResponse[listValue.size()]);
    }
    throw new IOException(String.format("failed reading io.flexio.docker.api.CreateContainerPostResponse array, current token was %s", parser.currentToken()));
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

    STATUS201("status201", "status201"),

    STATUS400("status400", "status400"),

    STATUS404("status404", "status404"),

    STATUS409("status409", "status409"),

    STATUS500("status500", "status500");

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
