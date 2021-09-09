package it.luca.batch.factory.app.jdbc.dao;

import it.luca.batch.factory.app.jdbc.dto.BatchGenerationLogRecord;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@UseClasspathSqlLocator
public interface BatchGenerationLogRecordDao {

    @SqlUpdate
    void save(@BindMethods("r") BatchGenerationLogRecord record);
}
