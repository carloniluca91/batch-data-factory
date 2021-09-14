package it.luca.batch.factory.model.output;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.luca.batch.factory.model.output.avro.AvroRecordMapper;
import lombok.Getter;
import org.apache.avro.specific.SpecificRecord;

@Getter
public class AvroSerialization<T, R extends SpecificRecord> extends OutputSerialization<T> {

    private final Class<R> avroRecordClass;
    private final Class<? extends AvroRecordMapper<T, R>> avroRecordMapperClass;
    //private final List<JavaTypeConversion<?>> conversions;

    @SuppressWarnings("unchecked")
    @JsonCreator
    public AvroSerialization(@JsonProperty(FORMAT) String type,
                             @JsonProperty(FILE_NAME) String fileName,
                             @JsonProperty(DATE_PATTERN) String datePattern,
                             @JsonProperty("avroRecordClass") String avroRecordClass,
                             @JsonProperty("avroMapperClass") String avroMapperClass) throws ClassNotFoundException {

        super(type, fileName, datePattern);
        //this.conversions = conversions;
        this.avroRecordClass = (Class<R>) Class.forName(avroRecordClass);
        this.avroRecordMapperClass = (Class<? extends AvroRecordMapper<T,R>>) Class.forName(avroMapperClass);
    }
}
