package it.luca.batch.factory.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Getter
public class CsvSerialization extends OutputSerialization {

    @Getter
    @AllArgsConstructor
    public enum CSVSeparator {

        COMMA(','),
        DOT_COMMA(';'),
        PIPE('|');

        private final char separator;
    }

    public final static String OPTIONS = "options";
    public final static String SEPARATOR = "separator";
    public final static String HEADER = "header";
    public final static String QUOTE_STRINGS = "quoteStrings";

    private final Map<String, String> options;

    public CsvSerialization(@JsonProperty(FORMAT) String type,
                            @JsonProperty(OPTIONS) Map<String, String> options) {

        super(type);
        this.options = options;
    }

    public char getSeparator() {

        CSVSeparator separator;
        boolean usingDefault;
        if (options.containsKey(SEPARATOR)) {
            separator = CSVSeparator.valueOf(options.get(SEPARATOR).toUpperCase());
            usingDefault = false;
        } else {
            separator = CSVSeparator.COMMA;
            usingDefault = true;
        }

        log.info("Using separator {} ({})", separator, usingDefault ? "DEFAULT" : "CUSTOM");
        return separator.getSeparator();
    }

    public boolean useHeader() {

        boolean writeHeader;
        boolean usingDefault;
        if (options.containsKey(HEADER)) {
            writeHeader = Boolean.parseBoolean(options.get(HEADER));
            usingDefault = false;
        } else {
            writeHeader = true;
            usingDefault = true;
        }

        log.info("Flag for writing csv header set to {} ({})", writeHeader, usingDefault ? "DEFAULT" : "CUSTOM");
        return writeHeader;
    }

    public boolean quoteStrings() {

        boolean quoteStrings;
        boolean usingDefault;
        if (options.containsKey(QUOTE_STRINGS)) {
            quoteStrings = Boolean.parseBoolean(options.get(QUOTE_STRINGS));
            usingDefault = false;
        } else {
            quoteStrings = false;
            usingDefault = true;
        }

        log.info("Flag for quoting strings set to {} ({})", quoteStrings, usingDefault ? "DEFAULT" : "CUSTOM");
        return quoteStrings;
    }
}
