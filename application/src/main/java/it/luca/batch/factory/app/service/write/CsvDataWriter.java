package it.luca.batch.factory.app.service.write;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * Extension of {@link DataWriter} for writing data in .csv format
 * @param <T> type of data to be written
 */

@AllArgsConstructor
public class CsvDataWriter<T> implements DataWriter<T> {

    private final boolean isZip;

    @Override
    public void write(Class<T> dataClass, List<T> batch, OutputStream outputStream) throws IOException {

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(dataClass)
                .withColumnSeparator(';')
                .withUseHeader(true);

        OutputStream stream = isZip ?
                new ZipOutputStream(outputStream) :
                outputStream;

        ObjectWriter writer = csvMapper.writer(csvSchema);
        writer.writeValue(stream, batch);
        outputStream.close();
    }
}
