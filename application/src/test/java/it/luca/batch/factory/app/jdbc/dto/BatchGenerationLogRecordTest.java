package it.luca.batch.factory.app.jdbc.dto;

import it.luca.batch.factory.model.DataSource;
import it.luca.batch.factory.model.DataSourceConfiguration;
import it.luca.batch.factory.model.generation.CustomGeneration;
import it.luca.batch.factory.model.generation.DataSourceGeneration;
import it.luca.batch.factory.model.generation.StandardGeneration;
import it.luca.batch.factory.model.output.CsvSerialization;
import it.luca.batch.factory.model.output.DataSourceOutput;
import it.luca.batch.factory.model.output.DataSourceSerialization;
import it.luca.batch.factory.model.output.DataSourceTarget;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatchGenerationLogRecordTest {

    private final String ID = "dataSourceId";
    private final int SIZE = 500;
    private final String DATA_CLASS = TestBean.class.getName();
    private final String OUTPUT_PATH = "/target/path";
    private final String DATE_PATTERN = "dd_MM_yyyy";
    private final String FILE_NAME = "file_name_".concat(DATE_PATTERN);

    private final String SERIALIZATION_FORMAT = DataSourceSerialization.CSV;
    private final String FILE_SYSTEM_TYPE = DataSourceTarget.FileSystemType.LOCAL.name();

    private final CsvSerialization<TestBean> SERIALIZATION = new CsvSerialization<>(SERIALIZATION_FORMAT,
            FILE_NAME, DATE_PATTERN, false, null);

    private final DataSourceTarget TARGET = new DataSourceTarget(FILE_SYSTEM_TYPE, false, OUTPUT_PATH);
    private final DataSourceOutput<TestBean> OUTPUT = new DataSourceOutput<>(TARGET, SERIALIZATION);

    @Test
    void initWithStandardGeneration() throws ClassNotFoundException {

        // Init record
        StandardGeneration<TestBean> standardGeneration = new StandardGeneration<>(DataSourceGeneration.STANDARD, SIZE, DATA_CLASS);
        DataSourceConfiguration<TestBean> configuration = new DataSourceConfiguration<>(standardGeneration, OUTPUT);
        DataSource<TestBean> dataSource = new DataSource<>(ID, configuration);
        BatchGenerationLogRecord record = new BatchGenerationLogRecord(dataSource, null);

        // Assertions
        assertEquals(ID, record.getDataSourceId());
        assertEquals(DATA_CLASS, record.getDataSourceClass());
        assertEquals(DataSourceGeneration.STANDARD, record.getGenerationType());
        assertNull(record.getCustomGeneratorClass());
        assertEquals(SIZE, record.getBatchSize());
        assertEquals(SERIALIZATION_FORMAT, record.getSerializationFormat());
        assertEquals(FILE_SYSTEM_TYPE, record.getFileSystemType());
        assertEquals(OUTPUT_PATH, record.getFileSystemPath());
        assertEquals(SERIALIZATION.getFileNameWithDateAndExtension(), record.getGeneratedFileName());
        assertEquals(BatchGenerationLogRecord.OK, record.getSampleGenerationCode());
        assertNull(record.getExceptionClass());
        assertNull(record.getExceptionMessage());
    }

    @Test
    void initWithCustomGeneration() throws ClassNotFoundException {

        // Init record
        String GENERATOR_CLASS = TestBeanGenerator.class.getName();
        CustomGeneration<TestBean> customGeneration = new CustomGeneration<>(DataSourceGeneration.CUSTOM, SIZE, DATA_CLASS, GENERATOR_CLASS);
        DataSourceConfiguration<TestBean> configuration = new DataSourceConfiguration<>(customGeneration, OUTPUT);
        DataSource<TestBean> dataSource = new DataSource<>(ID, configuration);
        BatchGenerationLogRecord record = new BatchGenerationLogRecord(dataSource, null);

        // Assertions
        assertNotNull(record.getCustomGeneratorClass());
        assertEquals(GENERATOR_CLASS, record.getCustomGeneratorClass());
    }

    @Test
    void initWithException() throws ClassNotFoundException {

        // Init record
        String GENERATION_TYPE = DataSourceGeneration.STANDARD;
        StandardGeneration<TestBean> standardGeneration = new StandardGeneration<>(GENERATION_TYPE, SIZE, DATA_CLASS);
        String EXCEPTION_CLASS = IllegalArgumentException.class.getName();
        String EXCEPTION_MSG = "exceptionMessage";
        IllegalArgumentException exception = new IllegalArgumentException(EXCEPTION_MSG);
        DataSourceConfiguration<TestBean> configuration = new DataSourceConfiguration<>(standardGeneration, OUTPUT);
        DataSource<TestBean> dataSource = new DataSource<>(ID, configuration);
        BatchGenerationLogRecord record = new BatchGenerationLogRecord(dataSource, exception);

        // Assertions
        assertNotNull(record.getExceptionClass());
        assertNotNull(record.getExceptionMessage());
        assertEquals(EXCEPTION_CLASS, record.getExceptionClass());
        assertEquals(EXCEPTION_MSG, record.getExceptionMessage());
    }
}