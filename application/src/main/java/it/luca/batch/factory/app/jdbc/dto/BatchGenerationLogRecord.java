package it.luca.batch.factory.app.jdbc.dto;

import it.luca.batch.factory.model.BatchDataSource;
import it.luca.batch.factory.model.generation.CustomGeneration;
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
    private final String dataSourceClass;
    private final String generationType;
    private final String customGeneratorClass;
    private final Integer batchSize;
    private final String dataSourceType;
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
        this.dataSourceClass = generation.getDataClass().getName();
        this.generationType = generation.getType();
        this.customGeneratorClass = generation instanceof CustomGeneration ?
                orNull(((CustomGeneration<?>) generation).getGeneratorClass(), Class::getName) :
                null;

        this.batchSize = generation.getSize();
        this.dataSourceType = output.getSerialization().getType();
        this.fileSystemType = output.getTarget().getFileSystemType().name();
        this.fileSystemPath = output.getTarget().getTargetPath();
        this.generatedFileName = output.getTarget().getFileNameWithDate();
        this.sampleGenerationCode = isPresent(exception) ? KO : OK;
        this.exceptionClass = orNull(exception, x -> x.getClass().getName());
        this.exceptionMessage = orNull(exception, Exception::getMessage);
    }
}
