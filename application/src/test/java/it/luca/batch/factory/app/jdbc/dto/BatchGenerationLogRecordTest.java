package it.luca.batch.factory.app.jdbc.dto;

import it.luca.batch.factory.configuration.DataSource;
import it.luca.batch.factory.configuration.DataSourceConfiguration;
import it.luca.batch.factory.configuration.generation.CustomGeneration;
import it.luca.batch.factory.configuration.generation.Generation;
import it.luca.batch.factory.configuration.generation.StandardGeneration;
import it.luca.batch.factory.configuration.output.CsvSerialization;
import it.luca.batch.factory.configuration.output.Output;
import it.luca.batch.factory.configuration.output.Serialization;
import it.luca.batch.factory.configuration.output.Target;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatchGenerationLogRecordTest {

    private final String ID = "dataSourceId";
    private final int SIZE = 500;
    private final String DATA_CLASS = TestBean.class.getName();
    private final String OUTPUT_PATH = "/target/path";
    private final String DATE_PATTERN = "dd_MM_yyyy";
    private final String FILE_NAME = "file_name_".concat(DATE_PATTERN);

    private final String SERIALIZATION_FORMAT = Serialization.CSV;
    private final String FILE_SYSTEM_TYPE = Target.FileSystemType.LOCAL.name();

    private final CsvSerialization<TestBean> SERIALIZATION = new CsvSerialization<>(SERIALIZATION_FORMAT,
            FILE_NAME, DATE_PATTERN, false, null);

    private final Target TARGET = new Target(FILE_SYSTEM_TYPE, false, OUTPUT_PATH);
    private final Output<TestBean> OUTPUT = new Output<>(TARGET, SERIALIZATION);

    @Test
    void initWithStandardGeneration() throws ClassNotFoundException {

        // Init record
        StandardGeneration<TestBean> standardGeneration = new StandardGeneration<>(Generation.STANDARD, SIZE, DATA_CLASS);
        DataSourceConfiguration<TestBean> configuration = new DataSourceConfiguration<>(standardGeneration, OUTPUT);
        DataSource<TestBean> dataSource = new DataSource<>(ID, configuration);
        BatchGenerationLogRecord record = new BatchGenerationLogRecord(dataSource, null);

        // Assertions
        assertEquals(ID, record.getDataSourceId());
        assertEquals(DATA_CLASS, record.getDataSourceClass());
        assertEquals(Generation.STANDARD, record.getGenerationType());
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
        CustomGeneration<TestBean> customGeneration = new CustomGeneration<>(Generation.CUSTOM, SIZE, DATA_CLASS, GENERATOR_CLASS);
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
        String GENERATION_TYPE = Generation.STANDARD;
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