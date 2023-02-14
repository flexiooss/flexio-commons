package io.flexio.docker.cmd.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flexio.docker.api.types.Image;
import io.flexio.docker.api.types.optional.OptionalImage;
import io.flexio.docker.cmd.utils.JsonParser;
import io.flexio.docker.cmd.exceptions.Unparsable;

import java.util.Map;

public class DockerImageInspectParser {
    private final JsonParser jsonParser;

    public DockerImageInspectParser(ObjectMapper mapper) {
        this.jsonParser = new JsonParser(mapper);
    }

    public OptionalImage imageFor(String json) throws Unparsable {
        Map mapValue = this.jsonParser.readMapFromJsonListFirstElement(json);
        if(mapValue == null) return OptionalImage.of(null);
        return OptionalImage.of(this.parseMap(mapValue));
    }

    private Image parseMap(Map mapValue) {
        return Image.fromMap(mapValue).build();
    }
}
