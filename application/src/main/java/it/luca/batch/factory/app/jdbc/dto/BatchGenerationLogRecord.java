package it.luca.batch.factory.app.jdbc.dto;

import it.luca.batch.factory.model.BatchDataSource;
import it.luca.batch.factory.model.generation.DataSourceGeneration;
import it.luca.batch.factory.model.output.DataSourceOutput;
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
    private final String dataSourceClass;
    private final Integer batchSize;
    private final String fileSystemType;
    private final String fileSystemPath;
    private final String generatedFileName;
    private final String sampleGenerationCode;
    private final String exceptionClass;
    private final String exceptionMessage;
    private final Timestamp insertTs = Timestamp.valueOf(now());
    private final Date insertDt = Date.valueOf(now().toLocalDate());

    public BatchGenerationLogRecord(BatchDataSource<?> dataSource, Exception exception) {

        DataSourceGeneration<?> generation = dataSource.getConfiguration().getGeneration();
        DataSourceOutput output = dataSource.getConfiguration().getOutput();

        this.dataSourceId = dataSource.getId();
        this.dataSourceType = output.getOutputType().name();
        this.dataSourceClass = generation.getDataClass().getName();
        this.batchSize = generation.getSize();
        this.fileSystemType = output.getFileSystemType().name();
        this.fileSystemPath = output.getPath();
        this.generatedFileName = output.getFileName();
        this.sampleGenerationCode = isPresent(exception) ? KO : OK;
        this.exceptionClass = orNull(exception, x -> x.getClass().getName());
        this.exceptionMessage = orNull(exception, Exception::getMessage);
    }
}
