package it.luca.batch.factory.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.luca.batch.factory.model.generation.DataSourceGeneration;
import it.luca.batch.factory.model.output.DataSourceOutput;
import lombok.Getter;

/**
 * Configuration of a batch dataSource
 * @param <T> type of dataSource's record
 */

@Getter
public class DataSourceConfiguration<T> {

    public static final String GENERATION = "generation";
    public static final String OUTPUT = "output";

    private final DataSourceGeneration<T> generation;
    private final DataSourceOutput output;

    @JsonCreator
    public DataSourceConfiguration(@JsonProperty(GENERATION) DataSourceGeneration<T> generation,
                                   @JsonProperty(OUTPUT) DataSourceOutput output) {

        this.generation = generation;
        this.output = output;
    }
}
