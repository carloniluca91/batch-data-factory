package it.luca.batch.factory.app.jdbc.dto;

import it.luca.batch.factory.configuration.DataSource;
import it.luca.batch.factory.configuration.generation.CustomGeneration;
import it.luca.batch.factory.configuration.generation.Generation;
import it.luca.batch.factory.configuration.output.Output;
import lombok.Getter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static it.luca.utils.functional.Optional.isPresent;
import static it.luca.utils.functional.Optional.orNull;
import static it.luca.utils.time.Supplier.now;

/**
 * Bean representing log record to be inserted on application db
 */

@Getter
public class BatchGenerationLogRecord {

    public static final String OK = "OK";
    public static final String KO = "KO";

    private final Timestamp generationStartTime;
    private final Date generationStartDate;
    private final Timestamp generationEndTime;
    private final Date generationEndDate;
    private final Integer generationDurationInMinutes;
    private final String dataSourceId;
    private final String dataSourceClass;
    private final String generationType;
    private final String customGeneratorClass;
    private final String batchSizeType;
    private final Integer batchSizeValue;
    private final String serializationFormat;
    private final String fileSystemType;
    private final String fileSystemPath;
    private final String generatedFileName;
    private final String sampleGenerationCode;
    private final String exceptionClass;
    private final String exceptionMessage;

    public BatchGenerationLogRecord(LocalDateTime startTime, DataSource<?> dataSource, Exception exception) {

        Generation<?> generation = dataSource.getConfiguration().getGeneration();
        Output<?> output = dataSource.getConfiguration().getOutput();

        LocalDateTime now = now();
        this.generationStartTime = Timestamp.valueOf(startTime);
        this.generationStartDate = Date.valueOf(startTime.toLocalDate());
        this.generationEndTime = Timestamp.valueOf(now);
        this.generationEndDate = Date.valueOf(now.toLocalDate());
        this.generationDurationInMinutes = (int) (ChronoUnit.MINUTES.between(startTime, now));
        this.dataSourceId = dataSource.getId();
        this.dataSourceClass = generation.getDataClass().getName();
        this.generationType = generation.getType();
        this.customGeneratorClass = generation instanceof CustomGeneration ?
                orNull(((CustomGeneration<?>) generation).getGeneratorClass(), Class::getName) :
                null;

        this.batchSizeType = generation.getSize().getType();
        this.batchSizeValue = generation.getBatchSize();
        this.serializationFormat = output.getSerialization().getFormat().name();
        this.fileSystemType = output.getTarget().getFileSystemType().name();
        this.fileSystemPath = output.getTarget().getPath();
        this.generatedFileName = output.getSerialization().getFileNameWithDateAndExtension();
        this.sampleGenerationCode = isPresent(exception) ? KO : OK;
        this.exceptionClass = orNull(exception, x -> x.getClass().getName());
        this.exceptionMessage = orNull(exception, Exception::getMessage);
    }
}
