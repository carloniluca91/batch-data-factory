package it.luca.batch.factory.configuration.generation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RandomSize extends SizeType {

    public static final String MIN = "min";
    public static final String MAX = "max";

    private final Integer value;

    @JsonCreator
    public RandomSize(@JsonProperty(DESCRIPTION) String type,
                      @JsonProperty(MIN) Integer min,
                      @JsonProperty(MAX) Integer max) {

        super(type);
        int minimumSize = validate(min, MIN);
        int maximumSize = validate(max, MAX);
        if (maximumSize <= minimumSize) {
            throw new IllegalArgumentException(String.format("%s (%s) must be greater than %s (%s)",
                    MAX, maximumSize, MIN, minimumSize));
        }

        int delta = Math.abs(maximumSize - minimumSize);
        this.value = (int) Math.ceil(Math.random() * delta + minimumSize);
    }

    @Override
    public int getBatchSize() {
        return value;
    }
}
