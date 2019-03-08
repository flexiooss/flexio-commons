package io.flexio.docker.api.types.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.flexio.docker.api.types.Version;
import io.flexio.docker.api.types.Version.Builder;
import java.io.IOException;
import java.lang.FunctionalInterface;
import java.lang.String;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VersionReader {
  private static Set VERSION_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set APIVERSION_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set MINAPIVERSION_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set GITCOMMIT_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set GOVERSION_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set OS_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set ARCH_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set KERNELVERSION_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  private static Set EXPERIMENTAL_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_TRUE, JsonToken.VALUE_FALSE));

  private static Set BUILDTIME_EXPECTEDTOKENS = new HashSet(Arrays.asList(JsonToken.VALUE_STRING));

  public Version read(JsonParser parser) throws IOException {
    if(parser.getCurrentToken() == null) {
      parser.nextToken();
    }
    if(parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if(parser.currentToken() != JsonToken.START_OBJECT) {
      throw new IOException(
                  String.format("reading a %s object, was expecting %s, but was %s",
                          Version.class.getName(), JsonToken.START_OBJECT, parser.currentToken()
                  )
          );
    }
    Version.Builder builder = Version.builder();
    while (parser.nextToken() != JsonToken.END_OBJECT) {
      Token token = Token.from(parser.getCurrentName());
      if(token != null) {
        switch (token) {
          case VERSION: {
            Set<JsonToken> expectedTokens = VERSION_EXPECTEDTOKENS;
            builder.version(this.readValue(parser, jsonParser -> jsonParser.getText(), "version", expectedTokens));
            break;
          }
          case APIVERSION: {
            Set<JsonToken> expectedTokens = APIVERSION_EXPECTEDTOKENS;
            builder.apiVersion(this.readValue(parser, jsonParser -> jsonParser.getText(), "apiVersion", expectedTokens));
            break;
          }
          case MINAPIVERSION: {
            Set<JsonToken> expectedTokens = MINAPIVERSION_EXPECTEDTOKENS;
            builder.minAPIVersion(this.readValue(parser, jsonParser -> jsonParser.getText(), "minAPIVersion", expectedTokens));
            break;
          }
          case GITCOMMIT: {
            Set<JsonToken> expectedTokens = GITCOMMIT_EXPECTEDTOKENS;
            builder.gitCommit(this.readValue(parser, jsonParser -> jsonParser.getText(), "gitCommit", expectedTokens));
            break;
          }
          case GOVERSION: {
            Set<JsonToken> expectedTokens = GOVERSION_EXPECTEDTOKENS;
            builder.goVersion(this.readValue(parser, jsonParser -> jsonParser.getText(), "goVersion", expectedTokens));
            break;
          }
          case OS: {
            Set<JsonToken> expectedTokens = OS_EXPECTEDTOKENS;
            builder.os(this.readValue(parser, jsonParser -> jsonParser.getText(), "os", expectedTokens));
            break;
          }
          case ARCH: {
            Set<JsonToken> expectedTokens = ARCH_EXPECTEDTOKENS;
            builder.arch(this.readValue(parser, jsonParser -> jsonParser.getText(), "arch", expectedTokens));
            break;
          }
          case KERNELVERSION: {
            Set<JsonToken> expectedTokens = KERNELVERSION_EXPECTEDTOKENS;
            builder.kernelVersion(this.readValue(parser, jsonParser -> jsonParser.getText(), "kernelVersion", expectedTokens));
            break;
          }
          case EXPERIMENTAL: {
            Set<JsonToken> expectedTokens = EXPERIMENTAL_EXPECTEDTOKENS;
            builder.experimental(this.readValue(parser, jsonParser -> jsonParser.getBooleanValue(), "experimental", expectedTokens));
            break;
          }
          case BUILDTIME: {
            Set<JsonToken> expectedTokens = BUILDTIME_EXPECTEDTOKENS;
            builder.buildTime(this.readValue(parser, jsonParser -> jsonParser.getText(), "buildTime", expectedTokens));
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

  public Version[] readArray(JsonParser parser) throws IOException {
    parser.nextToken();
    if (parser.currentToken() == JsonToken.VALUE_NULL) return null;
    if (parser.currentToken() == JsonToken.START_ARRAY) {
      LinkedList<Version> listValue = new LinkedList<>();
      while (parser.nextToken() != JsonToken.END_ARRAY) {
        if(parser.currentToken() == JsonToken.VALUE_NULL) {
          listValue.add(null);
        } else {
          listValue.add(this.read(parser));
        }
      }
      return listValue.toArray(new Version[listValue.size()]);
    }
    throw new IOException(String.format("failed reading io.flexio.docker.api.types.Version array, current token was %s", parser.currentToken()));
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

    VERSION("version", "Version"),

    APIVERSION("apiVersion", "ApiVersion"),

    MINAPIVERSION("minAPIVersion", "MinAPIVersion"),

    GITCOMMIT("gitCommit", "GitCommit"),

    GOVERSION("goVersion", "GoVersion"),

    OS("os", "Os"),

    ARCH("arch", "Arch"),

    KERNELVERSION("kernelVersion", "KernelVersion"),

    EXPERIMENTAL("experimental", "Experimental"),

    BUILDTIME("buildTime", "BuildTime");

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
