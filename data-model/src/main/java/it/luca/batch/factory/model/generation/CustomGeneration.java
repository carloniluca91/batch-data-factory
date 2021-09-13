package it.luca.batch.factory.model.generation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.luca.data.factory.generator.bean.CustomGenerator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static it.luca.utils.functional.Optional.isPresent;

@Getter
public class CustomGeneration<T> extends DataSourceGeneration<T> {

    public static final String GENERATOR_CLASS = "generatorClass";

    private final Class<? extends CustomGenerator<T>> generatorClass;

    @SuppressWarnings("unchecked")
    @JsonCreator
    public CustomGeneration(@JsonProperty(DataSourceGeneration.TYPE) String type,
                            @JsonProperty(DataSourceGeneration.SIZE) Integer size,
                            @JsonProperty(DataSourceGeneration.DATA_CLASS) String dataClass,
                            @JsonProperty(GENERATOR_CLASS) String generatorClass) throws ClassNotFoundException {

        super(type, size, (Class<T>) Class.forName(dataClass));
        this.generatorClass = (Class<? extends CustomGenerator<T>>) Class.forName(generatorClass);
    }

    @Override
    public List<T> createBatch() throws Exception {

        if (!isPresent(generatorClass)) {
            throw new IllegalArgumentException(String.format("%s detected, but no subclass of %s<%s> was specified",
                    this.getClass().getSimpleName(), CustomGenerator.class.getSimpleName(), dataClass.getSimpleName()));
        }

        List<T> batch = new ArrayList<>();
        CustomGenerator<T> generator = generatorClass.newInstance();
        for (int i=0; i < size; i++) {
            batch.add(generator.generate(dataClass));
        }

        return batch;
    }
}
