package it.luca.batch.factory.app.service.write;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class AvroDataWriter<T> implements DataWriter<T> {

    @Override
    public void write(Class<T> dataClass, List<T> objectList, OutputStream outputStream) throws IOException {

        DataFileWriter<T> dataFileWriter = new DataFileWriter<>(new ReflectDatumWriter<>(dataClass));
        Schema avroSchema = ReflectData.get().getSchema(dataClass);
        dataFileWriter.create(avroSchema, outputStream);
        for (T instance : objectList) {
            dataFileWriter.append(instance);
        }

        dataFileWriter.close();
    }
}
