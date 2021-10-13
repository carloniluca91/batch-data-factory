package it.luca.batch.factory.model;

import it.luca.data.factory.generator.bean.BeanGenerator;

import java.util.List;

public abstract class StandardGenerationTest<T> extends GenerationTest<T> {

    public StandardGenerationTest(Class<T> tClass) {
        super(tClass);
    }

    @Override
    protected List<T> getInstances() throws Exception {
        return BeanGenerator.generateListOf(SIZE, tClass);
    }
}
