package it.luca.batch.factory.app.service.write;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import it.luca.batch.factory.configuration.output.serialization.CsvSerialization;
import org.apache.commons.compress.compressors.CompressorException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Extension of {@link DataWriter} for writing data in .csv format
 * @param <T> type of data to be written
 */

public class CsvDataWriter<T> extends DataWriter<T, CsvSerialization<T>> {

    public CsvDataWriter(CsvSerialization<T> serialization) {
        super(serialization);
    }

    @Override
    public void write(List<T> batch, OutputStream outputStream) throws IOException, CompressorException {
        
        CsvMapper csvMapper = new CsvMapper();
        //noinspection unchecked
        Class<T> dataClass = (Class<T>) batch.get(0).getClass();
        CsvSchema schemaWithSeparatorAndHeader = csvMapper.schemaFor(dataClass)
                .withUseHeader(serialization.useHeader())
                .withColumnSeparator(serialization.getSeparator());

        CsvSchema schema = serialization.quoteStrings() ?
                schemaWithSeparatorAndHeader :
                schemaWithSeparatorAndHeader.withoutQuoteChar();

        ObjectWriter writer = csvMapper.writer(schema);
        OutputStream maybeCompressedStream = serialization.getMaybeCompressedOutputStream(outputStream);
        writer.writeValue(maybeCompressedStream, batch);
        maybeCompressedStream.close();
    }
}
