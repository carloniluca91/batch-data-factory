package it.luca.batch.factory.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Getter
public class DataSourcesWrapper {

    public static final String DATA_SOURCES = "dataSources";
    private final List<BatchDataSource<?>> dataSources;

    @JsonCreator
    public DataSourcesWrapper(@JsonProperty(DATA_SOURCES) List<BatchDataSource<?>> dataSources) {

        this.dataSources = dataSources;
    }

    public BatchDataSource<?> getDataSourceWithId(String id) {

        List<BatchDataSource<?>> matchingDataSources = dataSources.stream()
                .filter(x -> x.getId().equalsIgnoreCase(id))
                .collect(Collectors.toList());

        int numberOfMatches = matchingDataSources.size();
        if (numberOfMatches == 0) {
            throw new NoSuchElementException(String.format("Found no dataSource with id %s", id));
        } else if (numberOfMatches > 1) {
            throw new RuntimeException(String.format("Found %s dataSource(s) with id %s", numberOfMatches, id));
        } else return matchingDataSources.get(0);
    }
}
