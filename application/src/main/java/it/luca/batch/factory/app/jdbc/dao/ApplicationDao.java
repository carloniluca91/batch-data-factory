package it.luca.batch.factory.app.jdbc.dao;

import it.luca.batch.factory.app.jdbc.dto.BatchGenerationLogRecord;
import it.luca.batch.factory.configuration.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.time.LocalDateTime;

import static it.luca.utils.functional.Optional.isPresent;

@Slf4j
@Component
public class ApplicationDao {

    @Autowired
    private javax.sql.DataSource dataSource;

    private Jdbi jdbi;

    @PostConstruct
    private void initJdbi() {

        String jdbiClassName = Jdbi.class.getSimpleName();
        try {
            jdbi = Jdbi.create(dataSource)
                    .installPlugin(new SqlObjectPlugin())
                    .installPlugin(new PostgresPlugin());

            log.info("Successfully initialized {} instance", jdbiClassName);
        } catch (Exception e) {
            log.warn("Caught exception while initializing {}. Stack trace: ", jdbiClassName, e);
        }
    }

    /**
     * Save an instance of {@link BatchGenerationLogRecord} for given dataSource to application DB
     * @param startTime start time of batch generation process
     * @param dataSource {@link DataSource}
     * @param exception (potential) {@link Exception} raised by data generation process
     */

    public void saveLogRecordForDataSource(LocalDateTime startTime, DataSource<?> dataSource, Exception exception) {

        String recordClassName = BatchGenerationLogRecord.class.getSimpleName();
        try {
            if (isPresent(jdbi)) {
                log.info("Saving current instance of {}", recordClassName);
                BatchGenerationLogRecord record = new BatchGenerationLogRecord(startTime, dataSource, exception);
                jdbi.useHandle(handle -> handle.attach(BatchGenerationLogRecordDao.class).save(record));
                log.info("Successfully saved current instance of {}", recordClassName);
            } else {
                String jdbiClassName = Jdbi.class.getSimpleName();
                log.warn("Current instance of {} won't be saved due an error during initialization of {}",
                        recordClassName, jdbiClassName);
            }
        } catch (Exception e) {
            log.warn("Caught exception while saving current instance of {}. Stack trace: ",
                    recordClassName, e);
        }
    }
}
