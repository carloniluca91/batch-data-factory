package it.luca.batch.factory.configuration.output;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.avro.specific.SpecificRecord;

import static java.util.Objects.requireNonNull;

@Getter
public class AvroSerialization<T, R extends SpecificRecord> extends Serialization<T> {

    public static final String AVRO_RECORD_CLASS = "avroRecordClass";
    public static final String AVRO_RECORD_MAPPER_CLASS = "avroRecordMapperClass";

    private final Class<R> avroRecordClass;
    private final Class<? extends AvroRecordMapper<T, R>> avroRecordMapperClass;

    @SuppressWarnings("unchecked")
    @JsonCreator
    public AvroSerialization(@JsonProperty(FORMAT) String type,
                             @JsonProperty(FILE_NAME) String fileName,
                             @JsonProperty(DATE_PATTERN) String datePattern,
                             @JsonProperty(AVRO_RECORD_CLASS) String avroRecordClass,
                             @JsonProperty(AVRO_RECORD_MAPPER_CLASS) String avroRecordMapperClass) throws ClassNotFoundException {

        super(type, fileName, datePattern, false);
        this.avroRecordClass = (Class<R>) Class.forName(requireNonNull(avroRecordClass, AVRO_RECORD_CLASS));
        this.avroRecordMapperClass = (Class<? extends AvroRecordMapper<T,R>>) Class.forName(
                requireNonNull(avroRecordMapperClass, AVRO_RECORD_MAPPER_CLASS));
    }
}
