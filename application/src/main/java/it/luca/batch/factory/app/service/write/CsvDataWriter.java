package it.luca.batch.factory.app.service.write;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import it.luca.batch.factory.model.output.CsvSerialization;
import it.luca.batch.factory.model.output.OutputSerialization;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Extension of {@link DataWriter} for writing data in .csv format
 * @param <T> type of data to be written
 */

@AllArgsConstructor
public class CsvDataWriter<T> implements DataWriter<T> {

    private final boolean isZip;

    @Override
    public void write(Class<T> dataClass, List<T> batch, OutputStream outputStream, OutputSerialization serialization) throws IOException {

        CsvSerialization csvSerialization = (CsvSerialization) serialization;
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schemaWithSeparatorAndHeader = csvMapper.schemaFor(dataClass)
                .withUseHeader(csvSerialization.useHeader())
                .withColumnSeparator(csvSerialization.getSeparator());

        CsvSchema schema = csvSerialization.quoteStrings() ?
                schemaWithSeparatorAndHeader :
                schemaWithSeparatorAndHeader.withoutQuoteChar();

        OutputStream stream;
        if (isZip) {
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            zipOutputStream.putNextEntry(new ZipEntry("extraction.csv"));
            stream = zipOutputStream;
        } else stream = outputStream;

        ObjectWriter writer = csvMapper.writer(schema);
        writer.writeValue(stream, batch);
        outputStream.close();
    }
}
