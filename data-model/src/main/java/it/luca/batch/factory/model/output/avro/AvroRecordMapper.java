package it.luca.batch.factory.model.output.avro;

import org.apache.avro.specific.SpecificRecord;

public interface AvroRecordMapper<T, R extends SpecificRecord> {

    R map(T input);
}
