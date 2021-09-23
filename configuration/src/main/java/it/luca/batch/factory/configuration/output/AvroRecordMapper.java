package it.luca.batch.factory.configuration.output;

import org.apache.avro.specific.SpecificRecord;

/**
 * Interface for mapping a Java bean to an Avro bean
 * @param <T> type of Java bean
 * @param <R> type of Avro bean
 */

public interface AvroRecordMapper<T, R extends SpecificRecord> {

    R map(T input);
}
