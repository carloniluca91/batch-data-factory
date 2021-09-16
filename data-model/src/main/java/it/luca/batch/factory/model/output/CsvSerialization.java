package it.luca.batch.factory.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.Function;

@Slf4j
@Getter
public class CsvSerialization<T> extends DataSourceSerialization<T> {

    @Getter
    @AllArgsConstructor
    public enum CSVSeparator {

        COMMA(','),
        DOT_COMMA(';'),
        PIPE('|');

        private final char separator;
    }

    public static final String COMPRESS = "compress";
    public static final String COMPRESSION_EXTENSION = ".gz";
    public static final String OPTIONS = "options";
    public static final String SEPARATOR = "separator";
    public static final String HEADER = "header";
    public static final String QUOTE_STRINGS = "quoteStrings";

    private final Boolean compress;
    private final Map<String, String> options;

    public CsvSerialization(@JsonProperty(FORMAT) String type,
                            @JsonProperty(FILE_NAME) String fileName,
                            @JsonProperty(DATE_PATTERN) String datePattern,
                            @JsonProperty(COMPRESS) Boolean compress,
                            @JsonProperty(OPTIONS) Map<String, String> options) {

        super(type, fileName, datePattern);
        this.compress = compress;
        this.options = options;
    }

    /**
     * Get value of given key, or default if missing
     * @param key key
     * @param function {@link Function} for converting string value to instance of T
     * @param defaultValue default value
     * @param <V> type of return value
     * @return value of given key if it exists, the default value otherwise
     */

    private <V> V getOrElse(String key, Function<String, V> function, V defaultValue) {

        V value;
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

    /**
     * Return separator to be used during .csv serialization
     * @return {@link Character}
     */

    public char getSeparator() {

       return getOrElse(SEPARATOR, s -> CSVSeparator.valueOf(s.toUpperCase()), CSVSeparator.COMMA)
               .getSeparator();
    }

    /**
     * Return whether field names should be written as header of .csv file
     * @return {@link Boolean} (default true)
     */

    public boolean useHeader() {

        return getOrElse(HEADER, Boolean::parseBoolean, true);
    }

    /**
     * Return whether string fields should be written in quotes
     * @return {@link Boolean} (default false)
     */

    public boolean quoteStrings() {

        return getOrElse(QUOTE_STRINGS, Boolean::parseBoolean, false);
    }

    @Override
    public String getFileNameWithDateAndExtension() {

        String fileName = super.getFileNameWithDateAndExtension();
        return compress ? fileName.concat(COMPRESSION_EXTENSION) : fileName;
    }
}
