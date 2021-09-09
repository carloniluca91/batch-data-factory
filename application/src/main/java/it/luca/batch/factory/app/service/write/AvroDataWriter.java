package it.luca.batch.factory.app.service.write;

import it.luca.batch.factory.model.BatchDataSource;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.hadoop.fs.FSDataOutputStream;

import java.io.IOException;
import java.util.List;

public class AvroDataWriter<T> implements DataWriter<T> {

    @Override
    public void write(BatchDataSource<T> dataSource, List<T> objectList, FSDataOutputStream outputStream) throws IOException {

        Class<T> dataClass = dataSource.getDataClass();
        DataFileWriter<T> dataFileWriter = new DataFileWriter<>(new ReflectDatumWriter<>(dataClass));
        Schema avroSchema = ReflectData.get().getSchema(dataClass);
        dataFileWriter.create(avroSchema, outputStream.getWrappedStream());
        for (T instance : objectList) {
            dataFileWriter.append(instance);
        }

        dataFileWriter.close();
    }
}
