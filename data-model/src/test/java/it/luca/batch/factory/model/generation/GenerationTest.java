package it.luca.batch.factory.model.generation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import it.luca.batch.factory.model.YamlSubTypeParsingTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerationTest extends YamlSubTypeParsingTest {

    private final JavaType type = mapper.getTypeFactory()
            .constructParametricType(Generation.class, TestBean.class);

    private final int SIZE = 500;

    @Override
    protected Map<String, Object> getCommonObjectMap() {

        return new HashMap<String, Object>() {{
            put(Generation.SIZE, SIZE);
            put(Generation.DATA_CLASS, TestBean.class.getName());
        }};
    }

    @Test
    void deserializeAsCustom() throws JsonProcessingException {

        Map<String, Object> map = getCommonObjectMap();
        map.put(Generation.TYPE, Generation.CUSTOM);
        map.put(CustomGeneration.GENERATOR_CLASS, TestBeanGenerator.class.getName());

        Generation<TestBean> generation = mapper.readValue(getYamlString(map), type);
        assertTrue(generation instanceof CustomGeneration);
        CustomGeneration<TestBean> customGeneration = (CustomGeneration<TestBean>) generation;
        assertEquals(customGeneration.getType(), Generation.CUSTOM);
        assertEquals(customGeneration.getSize(), SIZE);
        assertEquals(customGeneration.getDataClass(), TestBean.class);
        assertEquals(customGeneration.getDataClass(), TestBean.class);
        assertEquals(customGeneration.getGeneratorClass(), TestBeanGenerator.class);
    }

    @Test
    void deserializeAsStandard() throws JsonProcessingException {

        Map<String, Object> map = getCommonObjectMap();
        map.put(Generation.TYPE, Generation.STANDARD);

        Generation<TestBean> generation = mapper.readValue(getYamlString(map), type);
        assertTrue(generation instanceof StandardGeneration);
        StandardGeneration<TestBean> standardGeneration = (StandardGeneration<TestBean>) generation;
        assertEquals(standardGeneration.getType(), Generation.STANDARD);
        assertEquals(standardGeneration.getSize(), SIZE);
        assertEquals(standardGeneration.getDataClass(), TestBean.class);
    }
}