package it.luca.batch.factory.model.generation;

import it.luca.data.factory.generator.bean.BeanGenerator;

import java.util.ArrayList;
import java.util.List;

public class StandardGeneration<T> extends DataSourceGeneration<T> {

    @Override
    protected List<T> createBatch() throws Exception {

        List<T> batch = new ArrayList<>();
        for (int i=0; i < size; i++) {
            batch.add(BeanGenerator.generate(dataClass));
        }
        return batch;
    }
}
