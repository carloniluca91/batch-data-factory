package it.luca.batch.factory.app.service;

import it.luca.batch.factory.app.jdbc.dao.ApplicationDao;
import it.luca.batch.factory.app.jdbc.dto.BatchGenerationLogRecord;
import it.luca.batch.factory.app.service.write.FileSystemWriter;
import it.luca.batch.factory.model.BatchDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ApplicationService {

   @Autowired
   private FileSystemWriter fileSystemWriter;

   @Autowired
   private ApplicationDao dao;

    public <T> void generateBatch(BatchDataSource<T> dataSource) {

        List<T> objectList = new ArrayList<>();
        int batchSize = dataSource.getSize();
        String dataClassName = dataSource.getDataClass().getSimpleName();
        log.info("Starting to generate {} instance(s) of {}", batchSize, dataClassName);
        Exception exception = null;
        try {

            // Generate n instances of T
            for (int i = 0; i < batchSize; i++) {
                T instance = dataSource.getDataClass().newInstance();
                objectList.add(instance);
            }

            log.info("Successfully generated all of {} instance(s) of {}", batchSize, dataClassName);
            fileSystemWriter.writeData(dataSource, objectList);
        } catch (Exception e) {
            log.error("Caught exception while generating data for {}. Stack trace: ", dataSource.getId(), e);
            exception = e;
        }

        dao.save(new BatchGenerationLogRecord(dataSource, exception));
    }
}
