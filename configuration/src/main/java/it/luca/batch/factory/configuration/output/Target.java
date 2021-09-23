package it.luca.batch.factory.configuration.output;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Target {

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

    private final FileSystemType fileSystemType;
    private final Boolean overwrite;
    private final String path;

    @JsonCreator
    public Target(@JsonProperty(TYPE) String fileSystemType,
                  @JsonProperty(OVERWRITE) Boolean overwrite,
                  @JsonProperty(TARGET_PATH) String path) {

        this.fileSystemType = FileSystemType.valueOf(fileSystemType.toUpperCase());
        this.overwrite = overwrite;
        this.path = path;
    }
}
