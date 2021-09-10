package it.luca.batch.factory.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

import static it.luca.utils.time.Supplier.now;

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

    private OutputType outputType;
    private FileSystemType fileSystemType;
    private String path;
    private String fileName;
    private String datePattern;

    public String getFileNameWithDate() {

        return fileName.replace(datePattern, now().format(DateTimeFormatter.ofPattern(datePattern)));
    }
}
