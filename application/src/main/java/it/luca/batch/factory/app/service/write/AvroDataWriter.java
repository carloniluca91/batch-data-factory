package it.luca.batch.factory.app.service.write;

import it.luca.batch.factory.model.output.AvroSerialization;
import it.luca.batch.factory.model.output.OutputSerialization;
import org.apache.avro.Conversion;
import org.apache.avro.LogicalType;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.avro.specific.SpecificData;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Extension of {@link DataWriter} for writing data in Avro format
 * @param <T> type of data to be written
 */

public class AvroDataWriter<T> implements DataWriter<T> {

    @Override
    public void write(Class<T> dataClass, List<T> batch, OutputStream outputStream, OutputSerialization serialization) throws IOException {

        AvroSerialization avroSerialization = (AvroSerialization) serialization;
        DataFileWriter<T> dataFileWriter = new DataFileWriter<>(new ReflectDatumWriter<>(dataClass));
        SpecificData specificData = new SpecificData();
        specificData.addLogicalTypeConversion(new Conversion<LocalDateTime>() {
            @Override
            public Class<LocalDateTime> getConvertedType() {
                return LocalDateTime.class;
            }

            @Override
            public String getLogicalTypeName() {
                return "string";
            }

            @Override
            public CharSequence toCharSequence(LocalDateTime value, Schema schema, LogicalType type) {
                return value.format(DateTimeFormatter.ISO_LOCAL_DATE);
            }
        });

        Schema avroSchema = ReflectData.get().getSchema(dataClass);
        dataFileWriter.create(avroSchema, outputStream);
        for (T instance : batch) {
            dataFileWriter.append(instance);
        }

        dataFileWriter.close();
    }
}
