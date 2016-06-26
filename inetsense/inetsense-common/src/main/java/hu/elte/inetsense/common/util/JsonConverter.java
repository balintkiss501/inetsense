package hu.elte.inetsense.common.util;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;

public class JsonConverter {

    private ObjectMapper mapper;
    
    public JsonConverter() {
        mapper = new ObjectMapper();
	}
    
    public <T> T json2Object(String jsonString, Class<T> clazz) throws Exception {
    	if(StringUtils.isBlank(jsonString)) {
    		raiseParserException(jsonString);
    	}
    	JsonNode json = string2Json(jsonString);
		return mapper.treeToValue(json, clazz);
    }

	private JsonNode string2Json(String jsonString) throws JsonParserException {
		JsonNode result = null;
        try {
            result = JsonLoader.fromString(jsonString);
        } catch (IOException e) {
            raiseParserException(jsonString);
        }
		return result;
	}

	private void raiseParserException(String jsonString) throws JsonParserException {
		String msg = "Incoming message is not in a valid JSON format!";
		throw new JsonParserException(msg);
	}
}
