package it.luca.batch.factory.configuration.output;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.avro.specific.SpecificRecord;

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
                             @JsonProperty(AVRO_RECORD_MAPPER_CLASS) String avroMapperClass) throws ClassNotFoundException {

        super(type, fileName, datePattern);
        this.avroRecordClass = (Class<R>) Class.forName(avroRecordClass);
        this.avroRecordMapperClass = (Class<? extends AvroRecordMapper<T,R>>) Class.forName(avroMapperClass);
    }
}
