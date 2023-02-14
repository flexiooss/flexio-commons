package io.flexio.docker.cmd.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flexio.docker.api.types.Container;
import io.flexio.docker.api.types.optional.OptionalContainer;
import io.flexio.docker.cmd.utils.JsonParser;
import io.flexio.docker.cmd.exceptions.Unparsable;

import java.util.Arrays;
import java.util.Map;

public class DockerInspectParser {
    private final JsonParser jsonParser;

    public DockerInspectParser(ObjectMapper mapper) {
        this.jsonParser = new JsonParser(mapper);
    }

    public OptionalContainer containerFor(String json) throws Unparsable {
        Map mapValue = this.jsonParser.readMapFromJsonListFirstElement(json);
        if(mapValue == null) return OptionalContainer.of(null);
        return OptionalContainer.of(this.parseMap(mapValue));
    }


    private Container parseMap(Map mapValue) {
        Container container = Container.fromMap(mapValue).build();
        if(mapValue.get("Name") != null && mapValue.get("Name") instanceof String) {
            String name = (String) mapValue.get("Name");
            if(name.startsWith("/")) {
                name = name.substring(1);
            }
            container = container.withNames(Arrays.asList(name));
        }
        return container;
    }
}
