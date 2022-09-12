package io.flexio.docker.cmd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.flexio.docker.api.types.Container;
import io.flexio.docker.api.types.optional.OptionalContainer;
import io.flexio.docker.cmd.exceptions.Unparsable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DockerInspectParser {

    private final ObjectMapper mapper;

    public DockerInspectParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public OptionalContainer containerFor(String json) throws Unparsable {
        List jsonList;
        try {
            jsonList = this.mapper.readValue(json, List.class);
        } catch (JsonProcessingException e) {
            throw new Unparsable("failed reading list from : " + json, e);
        }
        if(jsonList.isEmpty()) return OptionalContainer.of(null);

        if(jsonList.get(0) instanceof Map) {
            Map mapValue = (Map) jsonList.get(0);
            return OptionalContainer.of(this.parseMap(mapValue));
        } else {
            throw new Unparsable("failed reading map from first element: " + json);
        }
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
