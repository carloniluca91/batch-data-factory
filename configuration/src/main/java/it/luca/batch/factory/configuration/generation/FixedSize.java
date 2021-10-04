package it.luca.batch.factory.configuration.generation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FixedSize extends SizeType {

    public static final String VALUE = "value";

    protected final Integer value;

    @JsonCreator
    public FixedSize(@JsonProperty(DESCRIPTION) String type,
                     @JsonProperty(VALUE) Integer value) {

        super(type);
        this.value = validate(value, VALUE);
    }

    @Override
    public int getBatchSize() {
        return value;
    }
}
