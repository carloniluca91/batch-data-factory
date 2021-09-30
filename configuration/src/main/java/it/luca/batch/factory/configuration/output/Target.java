package it.luca.batch.factory.configuration.output;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

import static it.luca.utils.functional.Optional.orElse;
import static java.util.Objects.requireNonNull;

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
    public final static String PATH = "path";

    private final FileSystemType fileSystemType;
    private final Boolean overwrite;
    private final String path;

    @JsonCreator
    public Target(@JsonProperty(TYPE) String fileSystemType,
                  @JsonProperty(OVERWRITE) Boolean overwrite,
                  @JsonProperty(PATH) String path) {

        this.fileSystemType = FileSystemType.valueOf(requireNonNull(fileSystemType, TYPE).toUpperCase());
        this.overwrite = orElse(overwrite, Function.identity(), true);
        this.path = requireNonNull(path, PATH);
    }
}
