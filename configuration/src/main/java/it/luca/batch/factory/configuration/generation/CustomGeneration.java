package it.luca.batch.factory.configuration.generation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.luca.data.factory.generator.bean.CustomGenerator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static it.luca.utils.functional.Optional.isPresent;
import static java.util.Objects.requireNonNull;

/**
 * Subclass of {@link Generation} that generates records with a mixed approach (both annotation-based and manual)
 * @param <T> type of generated records
 */

@Getter
public class CustomGeneration<T> extends Generation<T> {

    public static final String GENERATOR_CLASS = "generatorClass";

    private final Class<? extends CustomGenerator<T>> generatorClass;

    @JsonCreator
    @SuppressWarnings("unchecked")
    public CustomGeneration(@JsonProperty(Generation.TYPE) String type,
                            @JsonProperty(Generation.SIZE) SizeType sizeType,
                            @JsonProperty(Generation.DATA_CLASS) String dataClass,
                            @JsonProperty(GENERATOR_CLASS) String generatorClass) throws ClassNotFoundException {

        super(type, sizeType, dataClass);
        this.generatorClass = (Class<? extends CustomGenerator<T>>) Class.forName(requireNonNull(generatorClass, GENERATOR_CLASS));
    }

    @Override
    public List<T> createBatch() throws Exception {

        if (!isPresent(generatorClass)) {
            throw new IllegalArgumentException(String.format("%s detected, but no subclass of %s<%s> was specified",
                    this.getClass().getSimpleName(), CustomGenerator.class.getSimpleName(), dataClass.getSimpleName()));
        }

        List<T> batch = new ArrayList<>();
        CustomGenerator<T> generator = generatorClass.newInstance();
        for (int i=0; i < getBatchSize(); i++) {
            batch.add(generator.generate(dataClass));
        }

        return batch;
    }
}
