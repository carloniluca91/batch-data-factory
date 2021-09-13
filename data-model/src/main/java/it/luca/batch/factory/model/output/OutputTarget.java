package it.luca.batch.factory.model.output;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

import static it.luca.utils.time.Supplier.now;

@Getter
public class OutputTarget {

    @Getter
    @AllArgsConstructor
    public enum FileSystemType {

        HDFS("distributed FS"),
        LOCAL("local FS");

        private final String description;
    }

    public final static String TYPE = "type";
    public final static String OVERWRITE = "overwrite";
    public final static String TARGET_PATH = "targetPath";
    public final static String FILE_NAME = "fileName";
    public final static String DATE_PATTERN = "datePattern";

    private final FileSystemType fileSystemType;
    private final Boolean overwrite;
    private final String targetPath;
    private final String fileName;
    private final String datePattern;

    @JsonCreator
    public OutputTarget(@JsonProperty(TYPE) String fileSystemType,
                        @JsonProperty(OVERWRITE) Boolean overwrite,
                        @JsonProperty(TARGET_PATH) String targetPath,
                        @JsonProperty(FILE_NAME) String fileName,
                        @JsonProperty(DATE_PATTERN) String datePattern) {

        this.fileSystemType = FileSystemType.valueOf(fileSystemType.toUpperCase());
        this.overwrite = overwrite;
        this.targetPath = targetPath;
        this.fileName = fileName;
        this.datePattern = datePattern;
    }

    /**
     * Get name for output file (containing formatted date)
     * @return name for output file
     */

    public String getFileNameWithDate() {

        return fileName.replace(datePattern, now().format(DateTimeFormatter.ofPattern(datePattern)));
    }

    public boolean isZipFile() {

        return fileName.endsWith(".gz");
    }
}
