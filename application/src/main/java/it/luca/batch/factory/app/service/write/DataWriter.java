package it.luca.batch.factory.app.service.write;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface DataWriter<T> {

    void write(Class<T> dataClass, List<T> objectList, OutputStream outputStream) throws IOException;
}
