package it.luca.batch.factory.model.output.avro;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.avro.Conversion;
import org.apache.avro.LogicalType;
import org.apache.avro.Schema;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class LocalDateConversion extends JavaTypeConversion<LocalDate> {

    @JsonCreator
    public LocalDateConversion(//@JsonProperty(ID) String id,
                               @JsonProperty(PARAMETERS) Map<String, String> parameters) {
        super(
                //id,
                parameters);
    }

    @Override
    public Conversion<LocalDate> getConversion() {

        String pattern = getParameter(LocalDateTimeConversion.PATTERN);
        return new Conversion<LocalDate>() {
            @Override
            public Class<LocalDate> getConvertedType() {
                return LocalDate.class;
            }

            @Override
            public String getLogicalTypeName() {
                return "string";
            }

            @Override
            public CharSequence toCharSequence(LocalDate value, Schema schema, LogicalType type) {
                return value.format(DateTimeFormatter.ofPattern(pattern));
            }
        };
    }
}
