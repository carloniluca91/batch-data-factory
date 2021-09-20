package it.luca.batch.factory.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.luca.batch.factory.model.generation.Generation;
import it.luca.batch.factory.model.output.Output;
import lombok.Getter;

/**
 * Configuration of a batch dataSource
 * @param <T> type of dataSource's record
 */

@Getter
public class DataSourceConfiguration<T> {

    public static final String GENERATION = "generation";
    public static final String OUTPUT = "output";

    private final Generation<T> generation;
    private final Output<T> output;

    @JsonCreator
    public DataSourceConfiguration(@JsonProperty(GENERATION) Generation<T> generation,
                                   @JsonProperty(OUTPUT) Output<T> output) {

        this.generation = generation;
        this.output = output;
    }
}
