package it.luca.batch.factory.model.generation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataSourceGenerationTest {

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final JavaType type = mapper.getTypeFactory()
            .constructParametricType(DataSourceGeneration.class, TestBean.class);

    private final int SIZE = 500;

    protected String getYamlString(Map<String, Object> yamlObjectMap) {

        return yamlObjectMap.entrySet().stream()
                .map(entry -> String.format("%s: %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    @Test
    void deserializeAsCustom() throws JsonProcessingException {

        Map<String, Object> map = new HashMap<String, Object>() {{
            put(DataSourceGeneration.TYPE, DataSourceGeneration.CUSTOM);
            put(DataSourceGeneration.SIZE, SIZE);
            put(DataSourceGeneration.DATA_CLASS, TestBean.class.getName());
            put(CustomGeneration.GENERATOR_CLASS, TestBeanGenerator.class.getName());
        }};


        DataSourceGeneration<TestBean> generation = mapper.readValue(getYamlString(map), type);
        assertTrue(generation instanceof CustomGeneration);
        CustomGeneration<TestBean> customGeneration = (CustomGeneration<TestBean>) generation;
        assertEquals(customGeneration.getType(), DataSourceGeneration.CUSTOM);
        assertEquals(customGeneration.getSize(), SIZE);
        assertEquals(customGeneration.getDataClass(), TestBean.class);
        assertEquals(customGeneration.getDataClass(), TestBean.class);
        assertEquals(customGeneration.getGeneratorClass(), TestBeanGenerator.class);
    }

    @Test
    void deserializeAsStandard() throws JsonProcessingException {

        Map<String, Object> map = new HashMap<String, Object>() {{
            put(DataSourceGeneration.TYPE, DataSourceGeneration.STANDARD);
            put(DataSourceGeneration.SIZE, SIZE);
            put(DataSourceGeneration.DATA_CLASS, TestBean.class.getName());
        }};

        DataSourceGeneration<TestBean> generation = mapper.readValue(getYamlString(map), type);
        assertTrue(generation instanceof StandardGeneration);
        StandardGeneration<TestBean> standardGeneration = (StandardGeneration<TestBean>) generation;
        assertEquals(standardGeneration.getType(), DataSourceGeneration.STANDARD);
        assertEquals(standardGeneration.getSize(), SIZE);
        assertEquals(standardGeneration.getDataClass(), TestBean.class);
    }
}