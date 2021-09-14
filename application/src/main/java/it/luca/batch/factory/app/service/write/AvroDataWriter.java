package it.luca.batch.factory.app.service.write;

import it.luca.batch.factory.model.BatchDataSource;
import it.luca.batch.factory.model.output.AvroSerialization;
import it.luca.batch.factory.model.output.OutputSerialization;
import it.luca.batch.factory.model.output.avro.AvroRecordMapper;
import it.luca.batch.factory.model.output.avro.JavaTypeConversion;
import lombok.AllArgsConstructor;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;

import java.io.OutputStream;
import java.util.List;

import static it.luca.utils.functional.Stream.map;

/**
 * Extension of {@link DataWriter} for writing data in Avro format
 * @param <T> type of data to be written
 */

public class AvroDataWriter<T, R extends SpecificRecord> extends DataWriter<T> {

    private final Class<R> avroRecordClass;

    public AvroDataWriter(Class<T> dataClass,
                          Class<R> avroRecordClass) {
        super(dataClass);
        this.avroRecordClass = avroRecordClass;
    }

    @Override
    public void write(List<T> batch, OutputSerialization<T> serialization, OutputStream outputStream) throws Exception {

        AvroSerialization<T, R> avroSerialization = (AvroSerialization<T, R>) serialization;
        AvroRecordMapper<T, R> avroRecordMapper = avroSerialization.getAvroRecordMapperClass().newInstance();
        List<R> avroRecords = map(batch, avroRecordMapper::map);
        DatumWriter<R> datumWriter = new SpecificDatumWriter<>(avroRecordClass);
        DataFileWriter<R> dataFileWriter = new DataFileWriter<>(datumWriter);
        dataFileWriter.create(avroRecords.get(0).getSchema(), outputStream);
        for (R record : avroRecords) {
            dataFileWriter.append(record);
        }

        dataFileWriter.close();
    }
}
