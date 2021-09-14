package it.luca.batch.factory.model.output;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Configuration for writing dataSource's records
 */

@Getter
public class DataSourceOutput<T> {

    private static final String TARGET = "target";
    private static final String SERIALIZATION = "serialization";

    private final DataSourceTarget target;
    private final DataSourceSerialization<T> serialization;

    @JsonCreator
    public DataSourceOutput(@JsonProperty(TARGET) DataSourceTarget target,
                            @JsonProperty(SERIALIZATION) DataSourceSerialization<T> serialization) {

        this.target = target;
        this.serialization = serialization;
    }
}
