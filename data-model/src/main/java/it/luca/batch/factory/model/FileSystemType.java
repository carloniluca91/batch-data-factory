package it.luca.batch.factory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileSystemType {

    HDFS("distributed FS"),
    LOCAL("local FS");

    private final String description;
}
