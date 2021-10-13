package it.luca.batch.factory.model;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

@AllArgsConstructor
public abstract class GenerationTest<T> {

    protected final Class<T> tClass;
    protected final int SIZE = 100;

    protected abstract List<T> getInstances() throws Exception;

    @Test
    protected void testInstanceGeneration() throws Exception {

        List<T> instances = getInstances();
        instances.forEach(this::testAssertions);
    }

    protected abstract void testAssertions(T instance);
}
