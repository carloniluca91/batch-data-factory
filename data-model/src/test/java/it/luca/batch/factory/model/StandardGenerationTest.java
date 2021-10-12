package it.luca.batch.factory.model;

import it.luca.data.factory.generator.bean.BeanGenerator;

public abstract class StandardGenerationTest<T> extends GenerationTest<T> {

    public StandardGenerationTest(Class<T> tClass) {
        super(tClass);
    }

    @Override
    protected T getInstance() throws Exception {
        return BeanGenerator.generateSingleTon(tClass);
    }
}
