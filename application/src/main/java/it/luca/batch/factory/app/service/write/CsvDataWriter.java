package it.luca.batch.factory.app.service.write;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import it.luca.batch.factory.model.output.CsvSerialization;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * Extension of {@link DataWriter} for writing data in .csv format
 * @param <T> type of data to be written
 */

public class CsvDataWriter<T> extends DataWriter<T, CsvSerialization<T>> {

    public CsvDataWriter(CsvSerialization<T> serialization) {
        super(serialization);
    }

    @Override
    public void write(List<T> batch, OutputStream outputStream) throws IOException {
        
        CsvMapper csvMapper = new CsvMapper();
        //noinspection unchecked
        Class<T> dataClass = (Class<T>) batch.get(0).getClass();
        CsvSchema schemaWithSeparatorAndHeader = csvMapper.schemaFor(dataClass)
                .withUseHeader(serialization.useHeader())
                .withColumnSeparator(serialization.getSeparator());

        CsvSchema schema = serialization.quoteStrings() ?
                schemaWithSeparatorAndHeader :
                schemaWithSeparatorAndHeader.withoutQuoteChar();

        // Create compressed stream if necessary
        OutputStream stream = serialization.getZip() ?
                new GZIPOutputStream(outputStream) :
                outputStream;

        ObjectWriter writer = csvMapper.writer(schema);
        writer.writeValue(stream, batch);
        outputStream.close();
    }
}
