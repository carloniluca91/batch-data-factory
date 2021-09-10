package it.luca.batch.factory.model;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class BatchDataSource<T> {

    private String id;
    private DataSourceConfiguration<T> configuration;
}
