package it.luca.batch.factory.model;

import lombok.Data;

@Data
public class BatchDataSource<T> {

    private String id;
    private DataSourceConfiguration<T> configuration;
}
