package it.luca.batch.factory.configuration.generation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Configuration for dataSource's record generation
 * @param <T> type of dataSource's record
 */

@Slf4j
@Getter
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = Generation.TYPE,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CustomGeneration.class, name = Generation.CUSTOM),
        @JsonSubTypes.Type(value = StandardGeneration.class, name = Generation.STANDARD)
})
public abstract class Generation<T> {

    public static final String CUSTOM = "CUSTOM";
    public static final String STANDARD = "STANDARD";

    public static final String TYPE = "type";
    public static final String SIZE = "size";
    public static final String DATA_CLASS = "dataClass";

    protected final String type;
    protected final Integer size;
    protected final Class<T> dataClass;

    /**
     * Generate a batch of random data of type T
     * @return {@link List} of random instances of T
     * @throws Exception if data creation fails
     */

    public List<T> getBatch() throws Exception {

        String dataClassName = dataClass.getSimpleName();
        log.info("Starting to generate {} instance(s) of {} ({})", size, dataClassName, type);
        List<T> batch = createBatch();
        log.info("Successfully generated all of {} instance(s) of {} ({})", size, dataClassName, type);
        return batch;
    }

    protected abstract List<T> createBatch() throws Exception;
}
