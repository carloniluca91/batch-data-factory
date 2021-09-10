package it.luca.batch.factory.model;

import it.luca.batch.factory.model.generation.DataSourceGeneration;
import it.luca.batch.factory.model.output.DataSourceOutput;
import lombok.Data;

@Data
public class DataSourceConfiguration<T> {

    private DataSourceGeneration<T> generation;
    private DataSourceOutput output;
}
