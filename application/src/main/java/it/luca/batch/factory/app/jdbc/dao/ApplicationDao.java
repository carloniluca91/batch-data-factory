package it.luca.batch.factory.app.jdbc.dao;

import it.luca.batch.factory.app.jdbc.dto.BatchGenerationLogRecord;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import static it.luca.utils.functional.Optional.isPresent;

@Slf4j
@Component
public class ApplicationDao {

    @Autowired
    private DataSource dataSource;

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
     * Save an instance of logging record to application DB
     * @param record {@link BatchGenerationLogRecord} to save
     */

    public void save(BatchGenerationLogRecord record) {

        String recordClassName = record.getClass().getSimpleName();
        log.info("Saving current instance of {}", recordClassName);
        try {
            if (isPresent(jdbi)) {
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
