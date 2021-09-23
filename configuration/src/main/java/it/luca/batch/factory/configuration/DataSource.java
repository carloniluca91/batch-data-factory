package it.luca.batch.factory.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Bean containing all information about a batch dataSource
 * @param <T> type of dataSource's record
 */

@Getter
public class DataSource<T> {

    public static final String ID = "id";
    public static final String CONFIGURATION = "configuration";

    private final String id;
    private final DataSourceConfiguration<T> configuration;

    @JsonCreator
    public DataSource(@JsonProperty(ID) String id,
                      @JsonProperty(CONFIGURATION) DataSourceConfiguration<T> configuration) {

        this.id = id;
        this.configuration = configuration;
    }
}
