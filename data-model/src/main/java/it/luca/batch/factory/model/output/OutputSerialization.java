package it.luca.batch.factory.model.output;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = OutputSerialization.FORMAT,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AvroSerialization.class, name = OutputSerialization.AVRO),
        @JsonSubTypes.Type(value = CsvSerialization.class, name = OutputSerialization.CSV)
})
public abstract class OutputSerialization {

    public static final String AVRO = "AVRO";
    public static final String CSV = "CSV";
    public final static String FORMAT = "format";

    protected final String type;

    public OutputSerialization(String type) {

        this.type = type;
    }
}
