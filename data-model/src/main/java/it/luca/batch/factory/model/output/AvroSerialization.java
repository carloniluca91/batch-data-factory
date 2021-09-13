package it.luca.batch.factory.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.luca.batch.factory.model.output.avro.JavaTypeConversion;
import lombok.Getter;

import java.util.List;

@Getter
public class AvroSerialization extends OutputSerialization {

    //private final Class<R> avroRecordClass;
    //private final Class<? extends AvroMapper<T, R>> avroMapperClass;
    private final List<JavaTypeConversion<?>> conversions;

    public AvroSerialization(@JsonProperty(FORMAT) String type,
                             @JsonProperty(FILE_NAME) String fileName,
                             @JsonProperty(DATE_PATTERN) String datePattern,
                             @JsonProperty("conversions") List<JavaTypeConversion<?>> conversions) {
                             //@JsonProperty("avroRecordClass") String avroRecordClass,
                             //@JsonProperty("avroMapperClass") String avroMapperClass) throws ClassNotFoundException {

        super(type, fileName, datePattern);
        this.conversions = conversions;
        //this.avroRecordClass = (Class<R>) Class.forName(avroRecordClass);
        //this.avroMapperClass = (Class<? extends AvroMapper<T,R>>) Class.forName(avroMapperClass);
    }
}
