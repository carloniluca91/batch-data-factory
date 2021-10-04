package it.luca.batch.factory.configuration.generation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

import static java.util.Objects.requireNonNull;

/**
 * Configuration for size of batch to generate
 */

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = SizeType.DESCRIPTION,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FixedSize.class, name = SizeType.FIXED),
        @JsonSubTypes.Type(value = RandomSize.class, name = SizeType.RANDOM)
})
public abstract class SizeType {

    public static final String DESCRIPTION = "description";
    public static final String FIXED = "FIXED";
    public static final String RANDOM = "RANDOM";

    protected final String type;

    public SizeType(String type) {

        this.type = requireNonNull(type, DESCRIPTION);
    }

    /**
     * Check that given number is not null and not zero
     * @param number number to check
     * @param fieldName name of the field linked to given number
     * @return input number if not null and not zero
     * @throws IllegalArgumentException if number validation fails
     */

    public static int validate(Integer number, String fieldName) {

        if (requireNonNull(number, fieldName) != 0) {
            return number;
        } else throw new IllegalArgumentException(String.format("Illegal %s: %s", fieldName, number));
    }

    public abstract int getBatchSize();

}
