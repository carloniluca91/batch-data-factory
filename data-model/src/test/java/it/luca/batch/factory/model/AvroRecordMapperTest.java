package it.luca.batch.factory.model;

import it.luca.batch.factory.configuration.output.AvroRecordMapper;
import lombok.AllArgsConstructor;
import org.apache.avro.specific.SpecificRecord;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static it.luca.utils.functional.Stream.map;

@AllArgsConstructor
public abstract class AvroRecordMapperTest<T, R extends SpecificRecord, M extends AvroRecordMapper<T, R>> {

    protected final Class<T> recordsClass;
    protected final Class<R> avroRecordClass;
    protected final Class<M> mapperClass;

    @Test
    void test() throws Exception {

        List<T> inputRecords = getInputRecordList();
        M mapper = mapperClass.newInstance();
        List<R> avroRecords = map(inputRecords, mapper::map);
        IntStream.range(0, inputRecords.size()).forEach(i -> testRecordMapping(inputRecords.get(i), avroRecords.get(i)));
    }

    protected abstract List<T> getInputRecordList() throws Exception;

    protected abstract void testRecordMapping(T input, R output);

}