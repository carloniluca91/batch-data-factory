package it.luca.batch.factory.app.jdbc.dto;

import it.luca.batch.factory.model.BatchDataSource;
import lombok.Getter;

import java.sql.Date;
import java.sql.Timestamp;

import static it.luca.utils.functional.Optional.isPresent;
import static it.luca.utils.functional.Optional.orNull;
import static it.luca.utils.time.Supplier.now;

@Getter
public class BatchGenerationLogRecord {

    public static final String OK = "OK";
    public static final String KO = "KO";

    private final Timestamp eventTs = Timestamp.valueOf(now());
    private final Date eventDt = Date.valueOf(now().toLocalDate());
    private final String dataSourceId;
    private final String dataSourceType;
    private final Integer batchSize;
    private final String targetType;
    private final String targetPath;
    private final String fileName;
    private final String generationCode;
    private final String exceptionClass;
    private final String exceptionMessage;
    private final Timestamp insertTs = Timestamp.valueOf(now());
    private final Date insertDt = Date.valueOf(now().toLocalDate());

    public BatchGenerationLogRecord(BatchDataSource<?> dataSource, Exception exception) {

        this.dataSourceId = dataSource.getId();
        this.dataSourceType = dataSource.getType().name();
        this.batchSize = dataSource.getSize();
        this.targetType = dataSource.getFileSystemType().name();
        this.targetPath = dataSource.getTargetPath();
        this.fileName = dataSource.getFileName();
        this.generationCode = isPresent(exception) ? KO : OK;
        this.exceptionClass = orNull(exception, x -> x.getClass().getName());
        this.exceptionMessage = orNull(exception, Exception::getMessage);
    }
}
