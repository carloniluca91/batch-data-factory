package it.luca.batch.factory.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

import static it.luca.utils.time.Supplier.now;

/**
 * Configuration for writing dataSource's records
 */

@Data
public class DataSourceOutput {

    @Getter
    @AllArgsConstructor
    public enum FileSystemType {

        HDFS("distributed FS"),
        LOCAL("local FS");

        private final String description;
    }


    public enum OutputType {

        AVRO,
        CSV
    }

    private OutputType type;
    private FileSystemType fileSystemType;
    private String path;
    private String fileName;
    private String datePattern;

    public void setType(String type) {
        this.type = OutputType.valueOf(type.toUpperCase());
    }

    public void setFileSystemType(String fileSystemType) {
        this.fileSystemType = FileSystemType.valueOf(fileSystemType.toUpperCase());
    }

    /**
     * Get name for output file (containing formatted date)
     * @return name for output file
     */

    public String getFileNameWithDate() {

        return fileName.replace(datePattern, now().format(DateTimeFormatter.ofPattern(datePattern)));
    }
}
