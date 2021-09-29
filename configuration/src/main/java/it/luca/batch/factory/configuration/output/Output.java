package it.luca.batch.factory.configuration.output;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import static java.util.Objects.requireNonNull;

/**
 * Configuration for writing dataSource's records
 */

@Getter
public class Output<T> {

    private static final String TARGET = "target";
    private static final String SERIALIZATION = "serialization";

    private final Target target;
    private final Serialization<T> serialization;

    @JsonCreator
    public Output(@JsonProperty(TARGET) Target target,
                  @JsonProperty(SERIALIZATION) Serialization<T> serialization) {

        this.target = requireNonNull(target, TARGET);
        this.serialization = requireNonNull(serialization, SERIALIZATION);
    }
}
