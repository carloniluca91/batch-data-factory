package it.luca.batch.factory.app.service.write;

import it.luca.batch.factory.configuration.output.AvroRecordMapper;
import it.luca.batch.factory.configuration.output.serialization.AvroSerialization;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;

import java.io.OutputStream;
import java.util.List;

import static it.luca.utils.functional.Stream.map;

/**
 * Extension of {@link DataWriter} for writing data in Avro format
 * @param <T> type of data to be written
 */

public class AvroDataWriter<T, R extends SpecificRecord> extends DataWriter<T, AvroSerialization<T, R>> {

    public AvroDataWriter(AvroSerialization<T, R> serialization) {
        super(serialization);
    }

    @Override
    public void write(List<T> batch, OutputStream outputStream) throws Exception {

        AvroRecordMapper<T, R> avroRecordMapper = serialization.getAvroRecordMapperClass().newInstance();
        List<R> avroRecords = map(batch, avroRecordMapper::map);
        DatumWriter<R> datumWriter = new SpecificDatumWriter<>(serialization.getAvroRecordClass());
        DataFileWriter<R> dataFileWriter = new DataFileWriter<>(datumWriter);
        dataFileWriter.create(avroRecords.get(0).getSchema(), outputStream);
        for (R record : avroRecords) {
            dataFileWriter.append(record);
        }

        dataFileWriter.close();
    }
}
