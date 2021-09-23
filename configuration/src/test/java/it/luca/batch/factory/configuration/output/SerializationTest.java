package it.luca.batch.factory.configuration.output;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import it.luca.batch.factory.configuration.YamlSubTypeParsingTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SerializationTest extends YamlSubTypeParsingTest {

    private final JavaType type = mapper.getTypeFactory()
            .constructParametricType(Serialization.class, TestBean.class);

    private final String FILE_NAME = "file_name";
    private final String DATE_PATTERN = "yyyyMMdd";

    @Override
    protected Map<String, Object> getCommonObjectMap() {

        return new HashMap<String, Object>() {{
            put(Serialization.FILE_NAME, FILE_NAME);
            put(Serialization.DATE_PATTERN, DATE_PATTERN);
        }};
    }

    @Test
    void deserializeAsAvro() throws JsonProcessingException {

        Map<String, Object> map = getCommonObjectMap();
        map.put(Serialization.FORMAT, Serialization.AVRO);
        map.put(AvroSerialization.AVRO_RECORD_CLASS, TestAvroRecord.class.getName());
        map.put(AvroSerialization.AVRO_RECORD_MAPPER_CLASS, TestAvroRecordMapper.class.getName());

        Serialization<TestBean> serialization = mapper.readValue(getYamlString(map), type);
        assertEquals(FILE_NAME, serialization.getFileName());
        assertEquals(DATE_PATTERN, serialization.getDatePattern());
        assertTrue(serialization instanceof AvroSerialization);
        assertEquals(serialization.getFormat(), Serialization.SerializationFormat.AVRO);

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
        map.put(Serialization.FORMAT, Serialization.CSV);
        map.put(CsvSerialization.COMPRESS, true);
        map.put(CsvSerialization.OPTIONS, "\n  ".concat(getYamlString(optionsMap)));

        Serialization<TestBean> serialization = mapper.readValue(getYamlString(map), type);
        assertEquals(FILE_NAME, serialization.getFileName());
        assertEquals(DATE_PATTERN, serialization.getDatePattern());
        assertTrue(serialization instanceof CsvSerialization);

        CsvSerialization<TestBean> csvSerialization = (CsvSerialization<TestBean>) serialization;
        assertTrue(csvSerialization.getCompress());
        assertEquals(csvSerialization.getSeparator(), CsvSerialization.CSVSeparator.PIPE.getSeparator());
    }
}