package it.luca.batch.factory.model.generation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CustomGeneration.class, name = DataSourceGeneration.CUSTOM),
        @JsonSubTypes.Type(value = StandardGeneration.class, name = DataSourceGeneration.STANDARD)
})
public abstract class DataSourceGeneration<T> {

    public static final String CUSTOM = "CUSTOM";
    public static final String STANDARD = "STANDARD";

    protected Integer size;
    protected Class<T> dataClass;

    @SuppressWarnings({"unchecked", "unused"})
    public void setDataClass(String dataClass) throws ClassNotFoundException {
        this.dataClass = (Class<T>) Class.forName(dataClass);
    }

    /**
     * Generate a batch of random data of type T
     * @return {@link List} of random instances of T
     * @throws Exception if data creation fails
     */

    public List<T> getBatch() throws Exception {

        String dataClassName = dataClass.getSimpleName();
        log.info("Starting to generate {} instance(s) of {}", size, dataClassName);
        List<T> batch = createBatch();
        log.info("Successfully generated all of {} instance(s) of {}", size, dataClassName);
        return batch;
    }

    protected abstract List<T> createBatch() throws Exception;
}
