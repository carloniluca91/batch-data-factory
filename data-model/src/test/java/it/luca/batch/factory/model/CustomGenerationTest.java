package it.luca.batch.factory.model;

import it.luca.data.factory.generator.bean.CustomGenerator;

public abstract class CustomGenerationTest<T, G extends CustomGenerator<T>> extends GenerationTest<T> {

    protected final Class<G> generatorClass;

    public CustomGenerationTest(Class<T> tClass,
                                Class<G> generatorClass) {

        super(tClass);
        this.generatorClass = generatorClass;
    }

    @Override
    protected T getInstance() throws Exception {

        return generatorClass.newInstance()
                .generateSingleTon(tClass);
    }
}
