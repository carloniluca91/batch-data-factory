package it.luca.batch.factory.app.service;

import it.luca.batch.factory.app.jdbc.dao.ApplicationDao;
import it.luca.batch.factory.app.jdbc.dto.BatchGenerationLogRecord;
import it.luca.batch.factory.app.service.write.FileSystemWriter;
import it.luca.batch.factory.model.BatchDataSource;
import it.luca.batch.factory.model.DataSourceConfiguration;
import it.luca.batch.factory.model.generation.DataSourceGeneration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ApplicationService {

   @Autowired
   private FileSystemWriter fileSystemWriter;

   @Autowired
   private ApplicationDao dao;

    /**
     * Generate and write a batch of random data of type T
     * @param dataSource instance of {@link BatchDataSource}
     * @param <T> type of generated data
     */

    public <T> void generateBatch(BatchDataSource<T> dataSource) {

        Exception exception = null;
        try {
            DataSourceConfiguration<T> configuration = dataSource.getConfiguration();
            DataSourceGeneration<T> generation = configuration.getGeneration();
            List<T> batch = generation.getBatch();
            Class<T> dataClass = generation.getDataClass();
            fileSystemWriter.writeData(dataClass, batch, configuration.getOutput());
        } catch (Exception e) {
            log.error("Caught exception while generating data for {}. Stack trace: ", dataSource.getId(), e);
            exception = e;
        }

        dao.save(new BatchGenerationLogRecord(dataSource, exception));
    }
}
