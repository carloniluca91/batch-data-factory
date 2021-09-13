package it.luca.batch.factory.model.output.avro;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.avro.Conversion;

import java.util.Map;
import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = JavaTypeConversion.ID,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LocalDateTimeConversion.class, name = JavaTypeConversion.LOCAL_DATETIME),
        @JsonSubTypes.Type(value = LocalDateConversion.class, name = JavaTypeConversion.LOCAL_DATE)
})
public abstract class JavaTypeConversion<T> {

    public static final String ID = "id";
    public static final String PARAMETERS = "parameters";

    public static final String LOCAL_DATE = "LOCAL_DATE";
    public static final String LOCAL_DATETIME = "LOCAL_DATETIME";

    //protected final String id;
    protected final Map<String, String> parameters;

    public String getParameter(String key) {

        String parameter;
        if (parameters.containsKey(key)) {
            parameter = parameters.get(key);
        } else throw new NoSuchElementException(String.format("Unable to find %s parameter for %s class",
                key, this.getClass().getSimpleName()));

        return parameter;
    }

    public abstract Conversion<T> getConversion();
}
