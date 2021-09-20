package it.luca.batch.factory.app.service.write;

import it.luca.batch.factory.model.output.Serialization;
import lombok.AllArgsConstructor;

import java.io.OutputStream;
import java.util.List;

/**
 * Abstract class to be extended in order to write instances of T in a given format
 * @param <T> type of data to write
 */

@AllArgsConstructor
public abstract class DataWriter<T, D extends Serialization<T>> {

    protected final D serialization;

    /**
     * Write a batch of records of type T to given {@link OutputStream}
     * @param batch {@link List} of records
     * @param outputStream {@link OutputStream}
     * @throws Exception if write operation fails
     */

    public abstract void write(List<T> batch, OutputStream outputStream) throws Exception;
}
