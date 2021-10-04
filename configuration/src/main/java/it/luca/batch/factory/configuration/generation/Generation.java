package it.luca.batch.factory.configuration.generation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Configuration for dataSource's record generation
 * @param <T> type of dataSource's record
 */

@Slf4j
@Getter
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
    protected final SizeType size;
    protected final Class<T> dataClass;

    @SuppressWarnings("unchecked")
    public Generation(String type, SizeType size, String dataClass) throws ClassNotFoundException {

        this.type = requireNonNull(type, TYPE);
        this.size = requireNonNull(size, SIZE);
        this.dataClass = (Class<T>) Class.forName(requireNonNull(dataClass, DATA_CLASS));
    }

    /**
     * Shortcut for getting actual batch size from {@link SizeType instance}
     * @return size of generated batch
     */

    public int getBatchSize() {

        return size.getBatchSize();
    }

    /**
     * Generate a batch of random data of type T
     * @return {@link List} of random instances of T
     * @throws Exception if data creation fails
     */

    public List<T> getBatch() throws Exception {

        String dataClassName = dataClass.getSimpleName();
        int size = this.size.getBatchSize();
        log.info("Starting to generate {} instance(s) of {} ({})", size, dataClassName, type);
        List<T> batch = createBatch();
        log.info("Successfully generated all of {} instance(s) of {} ({})", size, dataClassName, type);
        return batch;
    }

    protected abstract List<T> createBatch() throws Exception;
}
