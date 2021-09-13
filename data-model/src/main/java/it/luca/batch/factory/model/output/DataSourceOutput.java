package it.luca.batch.factory.model.output;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Configuration for writing dataSource's records
 */

@Getter
public class DataSourceOutput {

    private static final String TARGET = "target";
    private static final String SERIALIZATION = "serialization";

    private final OutputTarget target;
    private final OutputSerialization serialization;

    @JsonCreator
    public DataSourceOutput(@JsonProperty(TARGET) OutputTarget target,
                            @JsonProperty(SERIALIZATION) OutputSerialization serialization) {

        this.target = target;
        this.serialization = serialization;
    }
}
