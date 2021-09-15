package it.luca.batch.factory.model.output;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import it.luca.batch.factory.model.YamlSubTypeParsingTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataSourceSerializationTest extends YamlSubTypeParsingTest {

    private final JavaType type = mapper.getTypeFactory()
            .constructParametricType(DataSourceSerialization.class, TestBean.class);

    private final String FILE_NAME = "file_name";
    private final String DATE_PATTERN = "yyyyMMdd";

    @Override
    protected Map<String, Object> getCommonObjectMap() {

        return new HashMap<String, Object>() {{
            put(DataSourceSerialization.FILE_NAME, FILE_NAME);
            put(DataSourceSerialization.DATE_PATTERN, DATE_PATTERN);
        }};
    }


    @Test
    void deserializeAsAvro() throws JsonProcessingException {

        Map<String, Object> map = getCommonObjectMap();
        map.put(DataSourceSerialization.FORMAT, DataSourceSerialization.AVRO);
        map.put(AvroSerialization.AVRO_RECORD_CLASS, TestAvroRecord.class.getName());
        map.put(AvroSerialization.AVRO_RECORD_MAPPER_CLASS, TestAvroRecordMapper.class.getName());

        DataSourceSerialization<TestBean> serialization = mapper.readValue(getYamlString(map), type);
        assertEquals(FILE_NAME, serialization.getFileName());
        assertEquals(DATE_PATTERN, serialization.getDatePattern());
        assertTrue(serialization instanceof AvroSerialization);
        assertEquals(serialization.getFormat(), DataSourceSerialization.SerializationFormat.AVRO);

        AvroSerialization<TestBean, TestAvroRecord> avroSerialization = (AvroSerialization<TestBean, TestAvroRecord>) serialization;
        assertEquals(avroSerialization.getAvroRecordClass(), TestAvroRecord.class);
        assertEquals(avroSerialization.getAvroRecordMapperClass(), TestAvroRecordMapper.class);
    }

    @Test
    void deserializeAsCsv() throws JsonProcessingException {

        Map<String, Object> optionsMap = new HashMap<String, Object>() {{
            put(CsvSerialization.SEPARATOR, CsvSerialization.CSVSeparator.PIPE.name());
        }};

        Map<String, Object> map = getCommonObjectMap();
        map.put(DataSourceSerialization.FORMAT, DataSourceSerialization.CSV);
        map.put(CsvSerialization.ZIP, true);
        map.put(CsvSerialization.OPTIONS, "\n  ".concat(getYamlString(optionsMap)));

        DataSourceSerialization<TestBean> serialization = mapper.readValue(getYamlString(map), type);
        assertEquals(FILE_NAME, serialization.getFileName());
        assertEquals(DATE_PATTERN, serialization.getDatePattern());
        assertTrue(serialization instanceof CsvSerialization);

        CsvSerialization<TestBean> csvSerialization = (CsvSerialization<TestBean>) serialization;
        assertTrue(csvSerialization.getZip());
        assertEquals(csvSerialization.getSeparator(), CsvSerialization.CSVSeparator.PIPE.getSeparator());
    }
}