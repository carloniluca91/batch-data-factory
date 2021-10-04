package it.luca.batch.factory.configuration.generation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import it.luca.batch.factory.configuration.JsonSubTypeParsingTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerationTest extends JsonSubTypeParsingTest {

    private final JavaType type = mapper.getTypeFactory()
            .constructParametricType(Generation.class, TestBean.class);

    private final int SIZE = 500;
    private final Map<String, Object> SIZE_TYPE = new HashMap<String, Object>() {{
        put(SizeType.DESCRIPTION, SizeType.FIXED);
        put(FixedSize.VALUE, SIZE);
    }};

    @Override
    protected Map<String, Object> getCommonObjectMap() {

        return new HashMap<String, Object>() {{
            put(Generation.SIZE, SIZE_TYPE);
            put(Generation.DATA_CLASS, TestBean.class.getName());
        }};
    }

    @Test
    void deserializeAsCustom() throws JsonProcessingException {

        Map<String, Object> map = getCommonObjectMap();
        map.put(Generation.TYPE, Generation.CUSTOM);
        map.put(CustomGeneration.GENERATOR_CLASS, TestBeanGenerator.class.getName());

        Generation<TestBean> generation = mapper.readValue(mapToJsonString(map), type);
        assertTrue(generation instanceof CustomGeneration);
        CustomGeneration<TestBean> customGeneration = (CustomGeneration<TestBean>) generation;
        assertEquals(customGeneration.getType(), Generation.CUSTOM);
        assertEquals(customGeneration.getBatchSize(), SIZE);
        assertEquals(customGeneration.getDataClass(), TestBean.class);
        assertEquals(customGeneration.getDataClass(), TestBean.class);
        assertEquals(customGeneration.getGeneratorClass(), TestBeanGenerator.class);
    }

    @Test
    void deserializeAsStandard() throws JsonProcessingException {

        Map<String, Object> map = getCommonObjectMap();
        map.put(Generation.TYPE, Generation.STANDARD);

        Generation<TestBean> generation = mapper.readValue(mapToJsonString(map), type);
        assertTrue(generation instanceof StandardGeneration);
        StandardGeneration<TestBean> standardGeneration = (StandardGeneration<TestBean>) generation;
        assertEquals(standardGeneration.getType(), Generation.STANDARD);
        assertEquals(standardGeneration.getBatchSize(), SIZE);
        assertEquals(standardGeneration.getDataClass(), TestBean.class);
    }
}