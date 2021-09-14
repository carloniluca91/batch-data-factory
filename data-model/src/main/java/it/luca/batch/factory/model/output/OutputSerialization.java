package it.luca.batch.factory.model.output;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

import static it.luca.utils.time.Supplier.now;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = OutputSerialization.FORMAT,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AvroSerialization.class, name = OutputSerialization.AVRO),
        @JsonSubTypes.Type(value = CsvSerialization.class, name = OutputSerialization.CSV)
})
public abstract class OutputSerialization<T> {

    @Getter
    @AllArgsConstructor
    public enum SerializationFormat {

        AVRO(".avro"),
        CSV(".csv");

        private final String extension;

    }

    public static final String AVRO = "AVRO";
    public static final String CSV = "CSV";

    public final static String FORMAT = "format";
    public final static String FILE_NAME = "fileName";
    public final static String DATE_PATTERN = "datePattern";

    protected final SerializationFormat format;
    protected final String fileName;
    protected final String datePattern;

    public OutputSerialization(String format, String fileName, String datePattern) {

        this.format = SerializationFormat.valueOf(format.toUpperCase());
        this.fileName = fileName;
        this.datePattern = datePattern;
    }

    /**
     * Get name for output file (containing formatted date)
     * @return name for output file
     */

    public String getFileNameWithDateAndExtension() {

        return fileName.replace(datePattern, now().format(DateTimeFormatter.ofPattern(datePattern)))
                .concat(format.getExtension());
    }
}
