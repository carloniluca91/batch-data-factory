package it.luca.batch.factory.app.service.write;

import it.luca.batch.factory.model.output.OutputSerialization;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Interface to be extended in order to write instances of T in a given format
 * @param <T> type of data to write
 */

public interface DataWriter<T> {

    /**
     * Write a batch of records of type T to given {@link OutputStream}
     * @param dataClass record's class
     * @param batch {@link List} of records
     * @param outputStream {@link OutputStream}
     * @throws IOException if write operation fails
     */

    void write(Class<T> dataClass, List<T> batch, OutputStream outputStream, OutputSerialization serialization) throws IOException;
}
