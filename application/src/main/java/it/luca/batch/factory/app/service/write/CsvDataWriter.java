package it.luca.batch.factory.app.service.write;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import it.luca.batch.factory.model.BatchDataSource;
import org.apache.hadoop.fs.FSDataOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class CsvDataWriter<T> implements DataWriter<T> {

    @Override
    public void write(BatchDataSource<T> dataSource, List<T> objectList, FSDataOutputStream outputStream) throws IOException {

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(dataSource.getDataClass())
                .withColumnSeparator(';')
                .withUseHeader(true);

        OutputStream stream = dataSource.getFileName().toLowerCase().endsWith(".gz") ?
                new ZipOutputStream(outputStream) :
                outputStream.getWrappedStream();

        ObjectWriter writer = csvMapper.writer(csvSchema);
        writer.writeValue(stream, objectList);
        outputStream.close();
    }
}
