package it.luca.batch.factory.model.generation;

import it.luca.data.factory.generator.bean.BeanGenerator;
import it.luca.data.factory.generator.bean.CustomGenerator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static it.luca.utils.functional.Optional.isPresent;

/**
 * Configuration for dataSource's record generation
 * @param <T> type of dataSource's record
 */

@Data
@Slf4j
public class DataSourceGeneration<T> {

    public enum GenerationType {

        CUSTOM, STANDARD
    }

    private GenerationType type;
    private Integer size;
    private Class<T> dataClass;
    private Class<? extends CustomGenerator<T>> generatorClass;

    public void setType(String type) {
        this.type = GenerationType.valueOf(type.toUpperCase());
    }

    @SuppressWarnings({"unchecked", "unused"})
    public void setDataClass(String dataClass) throws ClassNotFoundException {
        this.dataClass = (Class<T>) Class.forName(dataClass);
    }

    @SuppressWarnings({"unchecked", "unused"})
    public void setGeneratorClass(String generatorClass) throws ClassNotFoundException {
        this.generatorClass = (Class<? extends CustomGenerator<T>>) Class.forName(generatorClass);
    }

    /**
     * Generate a batch of random data of type T
     * @return {@link List} of random instances of T
     * @throws Exception if data creation fails
     */

    public List<T> getBatch() throws Exception {

        String dataClassName = dataClass.getSimpleName();
        log.info("Starting to generate {} instance(s) of {}", size, dataClassName);
        List<T> batch = new ArrayList<>();
        switch (type) {
            case CUSTOM:
                if (!isPresent(generatorClass)) {
                    throw new IllegalArgumentException(String.format("%s %s detected, but no subclass of %s<%s> was specified",
                            type, GenerationType.class.getSimpleName(), CustomGenerator.class.getSimpleName(),
                            dataClassName));
                }

                CustomGenerator<T> generator = generatorClass.newInstance();
                for (int i=0; i < size; i++) {
                    batch.add(generator.generate(dataClass));
                }
                break;
            case STANDARD:
                for (int i=0; i < size; i++) {
                    batch.add(BeanGenerator.generate(dataClass));
                }
                break;
            default: throw new NoSuchElementException(String.format("Unmatched %s: %s",
                    GenerationType.class.getSimpleName(),
                    type));
        }

        log.info("Successfully generated all of {} instance(s) of {}", size, dataClassName);
        return batch;
    }
}
