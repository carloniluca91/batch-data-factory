package it.luca.batch.factory.model;

import lombok.Data;

/**
 * Bean containing all information about a batch dataSource
 * @param <T> type of dataSource's record
 */

@Data
public class BatchDataSource<T> {

    private String id;
    private DataSourceConfiguration<T> configuration;
}
