package it.luca.batch.factory.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class JsonSubTypeParsingTest {

    protected final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    protected String mapToJsonString(Map<String, Object> map) {

        return "{".concat(map.entrySet().stream()
                .map(entry -> {

                    Object value = entry.getValue();
                    String valueString;
                    if (value instanceof String) {
                        valueString = String.format("\"%s\"", value);
                    } else if (value instanceof Map) {
                        //noinspection unchecked
                        valueString = mapToJsonString((Map<String, Object>) value);
                    } else valueString = value.toString();

                    return String.format("\"%s\": %s", entry.getKey(), valueString);})
                .collect(Collectors.joining(",\n")))
                .concat("}");
    }

    protected abstract Map<String, Object> getCommonObjectMap();
}
