package it.luca.batch.factory.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.Function;

@Slf4j
@Getter
@SuppressWarnings("rawtypes")
public class CsvSerialization extends OutputSerialization {

    @Getter
    @AllArgsConstructor
    public enum CSVSeparator {

        COMMA(','),
        DOT_COMMA(';'),
        PIPE('|');

        private final char separator;
    }

    public static final String ZIP = "zip";
    public static final String ZIP_EXTENSION = ".gz";
    public static final String OPTIONS = "options";
    public static final String SEPARATOR = "separator";
    public static final String HEADER = "header";
    public static final String QUOTE_STRINGS = "quoteStrings";

    private final Boolean zip;
    private final Map<String, String> options;

    public CsvSerialization(@JsonProperty(FORMAT) String type,
                            @JsonProperty(FILE_NAME) String fileName,
                            @JsonProperty(DATE_PATTERN) String datePattern,
                            @JsonProperty(ZIP) Boolean zip,
                            @JsonProperty(OPTIONS) Map<String, String> options) {

        super(type, fileName, datePattern);
        this.zip = zip;
        this.options = options;
    }

    private <T> T getOrElse(String key, Function<String, T> function, T defaultValue) {

        T value;
        boolean usingDefault;
        if (options.containsKey(key)) {
            value = function.apply(options.get(key));
            usingDefault = false;
        } else {
            value = defaultValue;
            usingDefault = true;
        }

        log.info("Final value for key {} is '{}' ({})", key, value, usingDefault ? "DEFAULT" : "CUSTOM");
        return value;
    }

    public char getSeparator() {

       return getOrElse(SEPARATOR, s -> CSVSeparator.valueOf(s.toUpperCase()), CSVSeparator.COMMA)
               .getSeparator();
    }

    public boolean useHeader() {

        return getOrElse(HEADER, Boolean::parseBoolean, true);
    }

    public boolean quoteStrings() {

        return getOrElse(QUOTE_STRINGS, Boolean::parseBoolean, false);
    }

    @Override
    public String getFileNameWithDateAndExtension() {

        String fileName = super.getFileNameWithDateAndExtension();
        return zip ? fileName.concat(ZIP_EXTENSION) : fileName;
    }
}
