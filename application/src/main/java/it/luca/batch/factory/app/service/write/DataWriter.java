package it.luca.batch.factory.app.service.write;

import it.luca.batch.factory.model.output.OutputSerialization;
import lombok.AllArgsConstructor;

import java.io.OutputStream;
import java.util.List;

/**
 * Abstract class to be extended in order to write instances of T in a given format
 * @param <T> type of data to write
 */

@AllArgsConstructor
public abstract class DataWriter<T> {
    protected final Class<T> dataClass;


    /**
     * Write a batch of records of type T to given {@link OutputStream}
     * @param batch {@link List} of records
     * @param outputStream {@link OutputStream}
     * @throws Exception if write operation fails
     */

    public abstract void write(List<T> batch, OutputSerialization<T> dataSource, OutputStream outputStream) throws Exception;
}
