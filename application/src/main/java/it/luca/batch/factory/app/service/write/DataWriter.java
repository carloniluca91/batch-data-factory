package it.luca.batch.factory.app.service.write;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Interface to be extended in order to write instances of T in a given format
 * @param <T> type of data to be written
 */

public interface DataWriter<T> {

    /**
     * Write a batch of data of type T to given {@link OutputStream}
     * @param dataClass class of data to be written
     * @param batch {@link List} of instances of T
     * @param outputStream {@link OutputStream}
     * @throws IOException if write operation fails
     */

    void write(Class<T> dataClass, List<T> batch, OutputStream outputStream) throws IOException;
}
