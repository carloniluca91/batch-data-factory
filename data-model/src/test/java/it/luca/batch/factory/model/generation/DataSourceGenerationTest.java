package it.luca.batch.factory.model.generation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import it.luca.batch.factory.model.YamlSubTypeParsingTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataSourceGenerationTest extends YamlSubTypeParsingTest {

    private final JavaType type = mapper.getTypeFactory()
            .constructParametricType(DataSourceGeneration.class, TestBean.class);

    private final int SIZE = 500;

    @Override
    protected Map<String, Object> getCommonObjectMap() {

        return new HashMap<String, Object>() {{
            put(DataSourceGeneration.SIZE, SIZE);
            put(DataSourceGeneration.DATA_CLASS, TestBean.class.getName());
        }};
    }

    @Test
    void deserializeAsCustom() throws JsonProcessingException {

        Map<String, Object> map = getCommonObjectMap();
        map.put(DataSourceGeneration.TYPE, DataSourceGeneration.CUSTOM);
        map.put(CustomGeneration.GENERATOR_CLASS, TestBeanGenerator.class.getName());

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

        Map<String, Object> map = getCommonObjectMap();
        map.put(DataSourceGeneration.TYPE, DataSourceGeneration.STANDARD);

        DataSourceGeneration<TestBean> generation = mapper.readValue(getYamlString(map), type);
        assertTrue(generation instanceof StandardGeneration);
        StandardGeneration<TestBean> standardGeneration = (StandardGeneration<TestBean>) generation;
        assertEquals(standardGeneration.getType(), DataSourceGeneration.STANDARD);
        assertEquals(standardGeneration.getSize(), SIZE);
        assertEquals(standardGeneration.getDataClass(), TestBean.class);
    }
}