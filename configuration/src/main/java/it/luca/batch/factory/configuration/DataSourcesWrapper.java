package it.luca.batch.factory.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static it.luca.utils.functional.Stream.filter;

public class DataSourcesWrapper {

    public static final String DATA_SOURCES = "dataSources";

    @Getter
    private final List<DataSource<?>> dataSources;

    public DataSourcesWrapper(@JsonProperty(DATA_SOURCES) List<DataSource<?>> dataSources) {

        this.dataSources = Objects.requireNonNull(dataSources, DATA_SOURCES);
    }

    public DataSource<?> getDataSourceWithId(String id) {

        List<DataSource<?>> matchingDataSources = filter(dataSources, x -> x.getId().equalsIgnoreCase(id));
        if (matchingDataSources.isEmpty()) {
            throw new NoSuchElementException(String.format("No %s with id '%s'",
                    DataSource.class.getSimpleName(), id));
        } else {
            return matchingDataSources.get(0);
        }
    }
}
