package it.luca.batch.factory.app.service.write;

import it.luca.batch.factory.model.BatchDataSource;
import org.apache.hadoop.fs.FSDataOutputStream;

import java.io.IOException;
import java.util.List;

public interface DataWriter<T> {

    void write(BatchDataSource<T> dataSource, List<T> objectList, FSDataOutputStream outputStream) throws IOException;
}
