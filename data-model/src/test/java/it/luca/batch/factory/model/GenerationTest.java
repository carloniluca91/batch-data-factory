package it.luca.batch.factory.model;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

@AllArgsConstructor
public abstract class GenerationTest<T> {

    protected final Class<T> tClass;

    protected abstract T getInstance() throws Exception;

    @Test
    protected void testInstanceGeneration() throws Exception {

        T instance = getInstance();
        testAssertions(instance);
    }

    protected abstract void testAssertions(T instance);
}
