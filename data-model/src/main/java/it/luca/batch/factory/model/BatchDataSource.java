package it.luca.batch.factory.model;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class BatchDataSource<T> {

    private String id;
    private DataSourceType type;
    private Class<T> dataClass;
    private Integer size;
    private FileSystemType fileSystemType;
    private String targetPath;
    private String fileName;

    @SuppressWarnings("unchecked")
    public void setDataClass(String dataClass) throws ClassNotFoundException {

        this.dataClass = (Class<T>) Class.forName(dataClass);
    }

    public void setType(String type) {

        this.type = DataSourceType.valueOf(type.toUpperCase());
    }

    public void setFileSystemType(String type) {
        this.fileSystemType = FileSystemType.valueOf(type.toUpperCase());
    }
}
