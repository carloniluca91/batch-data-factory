package it.luca.batch.factory.model.output.avro;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.avro.Conversion;
import org.apache.avro.LogicalType;
import org.apache.avro.Schema;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class LocalDateTimeConversion extends JavaTypeConversion<LocalDateTime> {

    public static final String PATTERN = "pattern";

    @JsonCreator
    public LocalDateTimeConversion(//@JsonProperty(ID) String id,
                                   @JsonProperty(PARAMETERS) Map<String, String> parameters) {
        super(//id,
                parameters);
    }

    @Override
    public Conversion<LocalDateTime> getConversion() {

        String pattern = getParameter(PATTERN);
        return new Conversion<LocalDateTime>() {
            @Override
            public Class<LocalDateTime> getConvertedType() {
                return LocalDateTime.class;
            }

            @Override
            public String getLogicalTypeName() {
                return "string";
            }

            @Override
            public CharSequence toCharSequence(LocalDateTime value, Schema schema, LogicalType type) {
                return value.format(DateTimeFormatter.ofPattern(pattern));
            }

            @Override
            public Schema getRecommendedSchema() {
                return Schema.create(Schema.Type.STRING);
            }
        };
    }
}
