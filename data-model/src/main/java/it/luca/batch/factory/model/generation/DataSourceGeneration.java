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
        @JsonSubTypes.Type(value = CustomGeneration.class, name = "CUSTOM"),
        @JsonSubTypes.Type(value = StandardGeneration.class, name = "STANDARD")
})
public abstract class DataSourceGeneration<T> {

    protected Integer size;
    protected Class<T> dataClass;

    @SuppressWarnings("unchecked")
    public void setDataClass(String dataClass) throws ClassNotFoundException {
        this.dataClass = (Class<T>) Class.forName(dataClass);
    }

    public List<T> getBatch() throws Exception {

        String dataClassName = dataClass.getSimpleName();
        log.info("Starting to generate {} instance(s) of {}", size, dataClassName);
        List<T> batch = createBatch();
        log.info("Successfully generated all of {} instance(s) of {}", size, dataClassName);
        return batch;
    }

    public abstract List<T> createBatch() throws Exception;
}
