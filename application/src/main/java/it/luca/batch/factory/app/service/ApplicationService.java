package it.luca.batch.factory.app.service;

import it.luca.batch.factory.app.jdbc.dao.ApplicationDao;
import it.luca.batch.factory.app.jdbc.dto.BatchGenerationLogRecord;
import it.luca.batch.factory.app.service.write.FileSystemWriter;
import it.luca.batch.factory.model.BatchDataSource;
import it.luca.batch.factory.model.DataSourceConfiguration;
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

    public <T> void generateBatch(BatchDataSource<T> dataSource) {

        Exception exception = null;
        try {
            DataSourceConfiguration<T> configuration = dataSource.getConfiguration();
            List<T> batch = configuration.getGeneration().createBatch();
            Class<T> dataClass = configuration.getGeneration().getDataClass();
            fileSystemWriter.writeData(dataClass, batch, configuration.getOutput());
        } catch (Exception e) {
            log.error("Caught exception while generating data for {}. Stack trace: ", dataSource.getId(), e);
            exception = e;
        }

        dao.save(new BatchGenerationLogRecord(dataSource, exception));
    }
}
