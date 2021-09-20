package it.luca.batch.factory.app.service;

import it.luca.batch.factory.app.jdbc.dao.ApplicationDao;
import it.luca.batch.factory.app.service.write.FileSystemWriter;
import it.luca.batch.factory.model.DataSource;
import it.luca.batch.factory.model.DataSourceConfiguration;
import it.luca.batch.factory.model.generation.Generation;
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
     * @param dataSource instance of {@link DataSource}
     * @param <T> type of generated data
     */

    public <T> void generateAndWriteDataForDataSource(DataSource<T> dataSource) {

        String dataSourceId = dataSource.getId();
        Exception exception = null;
        log.info("Starting to generate data for dataSource {}", dataSourceId);
        try {

            DataSourceConfiguration<T> configuration = dataSource.getConfiguration();
            Generation<T> generation = configuration.getGeneration();
            List<T> batch = generation.getBatch();
            fileSystemWriter.writeData(batch, configuration);
            log.info("Successfully generated data for dataSource {}", dataSourceId);

        } catch (Exception e) {
            log.error("Caught exception while generating data for {}. Stack trace: ", dataSourceId, e);
            exception = e;
        }

        dao.saveLogRecordForDataSource(dataSource, exception);
    }
}
