package it.luca.batch.factory.app.service.write;

import it.luca.batch.factory.model.output.AvroSerialization;
import it.luca.batch.factory.model.output.OutputSerialization;
import it.luca.batch.factory.model.output.avro.JavaTypeConversion;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.reflect.ReflectData;

import java.io.OutputStream;
import java.util.List;

/**
 * Extension of {@link DataWriter} for writing data in Avro format
 * @param <T> type of data to be written
 */

public class AvroDataWriter<T> implements DataWriter<T> {

    @Override
    public void write(Class<T> dataClass, List<T> batch, OutputStream outputStream, OutputSerialization serialization) throws Exception {

        AvroSerialization avroSerialization = (AvroSerialization) serialization;
        Schema schema = ReflectData.get().getSchema(dataClass);
        GenericData genericData = GenericData.get();
        for (JavaTypeConversion<?> javaTypeConversion : avroSerialization.getConversions()) {
            genericData.addLogicalTypeConversion(javaTypeConversion.getConversion());
        }
        GenericDatumWriter<T> genericDatumWriter = new GenericDatumWriter<>(schema, genericData);
        DataFileWriter<T> dataFileWriter = new DataFileWriter<>(genericDatumWriter);
        dataFileWriter.create(schema, outputStream);
        for (T instance : batch) {
            dataFileWriter.append(instance);
        }

        dataFileWriter.close();
    }
}
