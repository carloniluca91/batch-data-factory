package it.luca.batch.factory.configuration.generation;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.luca.batch.factory.configuration.JsonSubTypeParsingTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class SizeTypeTest extends JsonSubTypeParsingTest {

    @Override
    protected Map<String, Object> getCommonObjectMap() {
        return new HashMap<>();
    }

    @Test
    void validate() {

        String FIELD_NAME = "fieldName";
        assertThrows(NullPointerException.class, () -> SizeType.validate(null, FIELD_NAME));
        assertThrows(IllegalArgumentException.class, () -> SizeType.validate(0, FIELD_NAME));
        assertDoesNotThrow(() -> SizeType.validate(50, FIELD_NAME));
    }

    @Test
    void deserializeAsFixed() throws JsonProcessingException {

        Function<Integer, String> jsonStringGenerator = size -> {
            Map<String, Object> map = new HashMap<String, Object>() {{
                put(SizeType.DESCRIPTION, SizeType.FIXED);
                put(FixedSize.VALUE, size);
            }};

            return mapToJsonString(map);
        };

        int FIXED_VALUE = 500;
        String jsonString = jsonStringGenerator.apply(FIXED_VALUE);
        SizeType fixedSize = mapper.readValue(jsonString, SizeType.class);
        assertTrue(fixedSize instanceof FixedSize);
        assertEquals(FIXED_VALUE, fixedSize.getBatchSize());

        assertThrows(Exception.class, () -> {
            String invalidFixedSizeString = jsonStringGenerator.apply(0);
            mapper.readValue(invalidFixedSizeString, SizeType.class);
        });
    }

    @Test
    void deserializeAsRandom() throws JsonProcessingException {

        int MIN_SIZE = 5;
        int MAX_SIZE = 10;
        BiFunction<Integer, Integer, String> mapGenerator = (min, max) -> {
            Map<String, Object> map = new HashMap<String, Object>() {{
                put(SizeType.DESCRIPTION, SizeType.RANDOM);
                put(RandomSize.MIN, min);
                put(RandomSize.MAX, max);
            }};

            return mapToJsonString(map);
        };

        BiConsumer<Integer, Integer> assertion = (min, max) ->
                assertThrows(Exception.class, () -> {
                    String invalidRandomSizeString = mapGenerator.apply(min, max);
                    mapper.readValue(invalidRandomSizeString, SizeType.class);
        });

        String jsonString = mapGenerator.apply(MIN_SIZE, MAX_SIZE);
        SizeType randomSize = mapper.readValue(jsonString, SizeType.class);
        assertTrue(randomSize instanceof RandomSize);
        int batchSize = randomSize.getBatchSize();
        assertTrue(MIN_SIZE <= batchSize);
        assertTrue(batchSize <= MAX_SIZE);

        assertion.accept(null, MAX_SIZE);
        assertion.accept(MIN_SIZE, null);
        assertion.accept(MAX_SIZE, MIN_SIZE);
    }
}