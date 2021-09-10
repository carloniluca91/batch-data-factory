package it.luca.batch.factory.model.generation;

import it.luca.data.factory.generator.bean.CustomGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomGeneration<T> extends DataSourceGeneration<T> {

    private Class<CustomGenerator<T>> generatorClass;

    @Override
    public List<T> createBatch() throws Exception {

        List<T> batch = new ArrayList<>();
        CustomGenerator<T> generator = generatorClass.newInstance();
        for (int i=0; i < size; i++) {
            batch.add(generator.generate(dataClass));
        }
        return batch;
    }
}
