package it.luca.batch.factory.model.output;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

import static it.luca.utils.time.Supplier.now;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = Serialization.FORMAT,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AvroSerialization.class, name = Serialization.AVRO),
        @JsonSubTypes.Type(value = CsvSerialization.class, name = Serialization.CSV),
        @JsonSubTypes.Type(value = CsvSerialization.class, name = Serialization.TXT)
})
public abstract class Serialization<T> {

    @Getter
    @AllArgsConstructor
    public enum SerializationFormat {

        AVRO(".avro"),
        CSV(".csv"),
        TXT(".txt");

        private final String extension;
    }

    public static final String AVRO = "AVRO";
    public static final String CSV = "CSV";
    public static final String TXT = "TXT";

    public final static String FORMAT = "format";
    public final static String FILE_NAME = "fileName";
    public final static String DATE_PATTERN = "datePattern";

    protected final SerializationFormat format;
    protected final String fileName;
    protected final String datePattern;

    public Serialization(String format, String fileName, String datePattern) {

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
