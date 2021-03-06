package it.luca.batch.factory.configuration.output.serialization;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.luca.batch.factory.configuration.output.compression.Compression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.CompressorException;

import java.io.OutputStream;
import java.util.Map;
import java.util.function.Function;

import static it.luca.utils.functional.Optional.isPresent;
import static java.util.Objects.requireNonNull;

@Slf4j
@Getter
public class CsvSerialization<T> extends Serialization<T> {

    @Getter
    @AllArgsConstructor
    public enum CSVSeparator {

        COMMA(','),
        DOT_COMMA(';'),
        PIPE('|');

        private final char separator;
    }

    public static final String COMPRESSION = "compression";
    public static final String OPTIONS = "options";
    public static final String SEPARATOR = "separator";
    public static final String HEADER = "header";
    public static final String QUOTE_STRINGS = "quoteStrings";

    private final Compression compression;
    private final Map<String, String> options;

    public CsvSerialization(@JsonProperty(FORMAT) String type,
                            @JsonProperty(FILE_NAME) String fileName,
                            @JsonProperty(DATE_PATTERN) String datePattern,
                            @JsonProperty(COMPRESSION) Compression compression,
                            @JsonProperty(OPTIONS) Map<String, String> options) {

        super(type, fileName, datePattern);
        this.compression = compression;
        this.options = requireNonNull(options, OPTIONS);
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
    public String getFileNameWithDateAndExtensions() {

        String fileNameWithDate = super.getFileNameWithDateAndExtensions();
        return isPresent(compression) ?
                compression.getCompressedFileName(fileNameWithDate) :
                fileNameWithDate;
    }

    public OutputStream getMaybeCompressedOutputStream(OutputStream outputStream) throws CompressorException {

        return isPresent(compression) ?
                compression.getCompressedStream(outputStream) :
                outputStream;
    }
}
