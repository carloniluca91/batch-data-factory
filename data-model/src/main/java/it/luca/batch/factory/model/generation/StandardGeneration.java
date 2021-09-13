package it.luca.batch.factory.model.generation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.luca.data.factory.generator.bean.BeanGenerator;

import java.util.ArrayList;
import java.util.List;

public class StandardGeneration<T> extends DataSourceGeneration<T> {

    @SuppressWarnings("unchecked")
    @JsonCreator
    public StandardGeneration(@JsonProperty(DataSourceGeneration.TYPE) String type,
                              @JsonProperty(DataSourceGeneration.SIZE) Integer size,
                              @JsonProperty(DataSourceGeneration.DATA_CLASS) String dataClass) throws ClassNotFoundException {

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
