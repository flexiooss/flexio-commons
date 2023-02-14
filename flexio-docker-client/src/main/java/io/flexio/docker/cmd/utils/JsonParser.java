package io.flexio.docker.cmd.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.flexio.docker.cmd.exceptions.Unparsable;

import java.util.List;
import java.util.Map;

public class JsonParser {
    private final ObjectMapper mapper;

    public JsonParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Map readMapFromJsonListFirstElement(String json) throws Unparsable {
        List jsonList;
        try {
            jsonList = this.mapper.readValue(json, List.class);
        } catch (JsonProcessingException e) {
            throw new Unparsable("failed reading list from : " + json, e);
        }

        Map mapValue = null;
        if(! jsonList.isEmpty()) {
            if (jsonList.get(0) instanceof Map) {
                mapValue = (Map) jsonList.get(0);
            } else {
                throw new Unparsable("failed reading map from first element: " + json);
            }
        }
        return mapValue;
    }
}
