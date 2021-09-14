package it.luca.batch.factory.model.output;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static it.luca.utils.functional.Optional.isPresent;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvSerializationTest {

    private final String TYPE = CsvSerialization.CSV;
    private final String DATE_PATTERN = "yyyyMMdd";
    private final String FILE_NAME = "file_name_".concat(DATE_PATTERN);

    private CsvSerialization<?> getInstance(Map<String, String> map) {
        return new CsvSerialization<>(TYPE, FILE_NAME, DATE_PATTERN, true, map);
    }

    private CsvSerialization<?> getInstance(Boolean zip) {
        return new CsvSerialization<>(TYPE, FILE_NAME, DATE_PATTERN, zip, new HashMap<>());
    }

    private <T> Map<String, String> initMap(String key, T value, Function<T, String> toString) {

        Map<String, String> map = new HashMap<>();
        if (isPresent(value)) {
            map.put(key, toString.apply(value));
        }

        return map;
    }

    @Test
    void getSeparator() {

        Consumer<CsvSerialization.CSVSeparator> consumer = csvSeparator -> {

            boolean isPresent = isPresent(csvSeparator);
            CsvSerialization<?> instance = getInstance(initMap(CsvSerialization.SEPARATOR, csvSeparator, x -> x.name().toUpperCase()));
            assertEquals((isPresent ?
                    csvSeparator :
                    CsvSerialization.CSVSeparator.COMMA).getSeparator(), instance.getSeparator());
        };

        consumer.accept(null);
        consumer.accept(CsvSerialization.CSVSeparator.COMMA);
        consumer.accept(CsvSerialization.CSVSeparator.PIPE);
    }

    @Test
    void useHeader() {

        Consumer<Boolean> consumer = aBoolean -> {

            boolean isPresent = isPresent(aBoolean);
            CsvSerialization<?> instance = getInstance(initMap(CsvSerialization.HEADER, aBoolean, String::valueOf));
            assertEquals(isPresent ? aBoolean : true, instance.useHeader());
        };

        consumer.accept(null);
        consumer.accept(true);
        consumer.accept(false);
    }

    @Test
    void quoteStrings() {

        Consumer<Boolean> consumer = aBoolean -> {

            boolean isPresent = isPresent(aBoolean);
            CsvSerialization<?> instance = getInstance(initMap(CsvSerialization.QUOTE_STRINGS, aBoolean, String::valueOf));
            assertEquals(isPresent ? aBoolean : false, instance.quoteStrings());
        };

        consumer.accept(null);
        consumer.accept(true);
        consumer.accept(false);
    }

    @Test
    void getFileNameWithDateAndExtension() {

        Consumer<Boolean> consumer = aBoolean -> {

            CsvSerialization<?> instance = getInstance(aBoolean);
            Consumer<Boolean> assertion = instance.getZip() ? Assertions::assertTrue : Assertions::assertFalse;
            assertion.accept(instance.getFileNameWithDateAndExtension().endsWith(CsvSerialization.ZIP_EXTENSION));
        };

        consumer.accept(true);
        consumer.accept(false);
    }
}