package it.luca.batch.factory.configuration.generation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.luca.data.factory.generator.bean.BeanGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Subclass of {@link Generation} that generates records relying only on field annotations (annotation-based)
 * @param <T> type of generated record
 */

public class StandardGeneration<T> extends Generation<T> {

    @SuppressWarnings("unchecked")
    @JsonCreator
    public StandardGeneration(@JsonProperty(Generation.TYPE) String type,
                              @JsonProperty(Generation.SIZE) Integer size,
                              @JsonProperty(Generation.DATA_CLASS) String dataClass) throws ClassNotFoundException {

        super(type, size, (Class<T>) Class.forName(dataClass));
    }

    @Override
    protected List<T> createBatch() throws Exception {

        List<T> batch = new ArrayList<>();
        for (int i=0; i < size; i++) {
            batch.add(BeanGenerator.generate(dataClass));
        }

        return batch;
    }
}