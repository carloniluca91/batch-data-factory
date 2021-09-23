package it.luca.batch.factory.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class YamlSubTypeParsingTest {

    protected final ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    protected String getYamlString(Map<String, Object> yamlObjectMap) {

        return yamlObjectMap.entrySet().stream()
                .map(entry -> String.format("%s: %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    protected abstract Map<String, Object> getCommonObjectMap();
}
