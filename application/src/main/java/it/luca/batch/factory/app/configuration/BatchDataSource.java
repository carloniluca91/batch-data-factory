package it.luca.batch.factory.app.configuration;

import lombok.Data;

@Data
public class BatchDataSource<T> {

    private String id;
    private BatchDataSourceType type;
    private Class<T> dataClass;
    private Integer size;
    private TargetType targetType;
    private String targetPath;
    private String fileName;

    @SuppressWarnings("unchecked")
    public void setDataClass(String dataClass) throws ClassNotFoundException {

        this.dataClass = (Class<T>) Class.forName(dataClass);
    }

    public void setType(String type) {

        this.type = BatchDataSourceType.valueOf(type.toUpperCase());
    }

    public void setTargetType(String type) {
        this.targetType = TargetType.valueOf(type.toUpperCase());
    }
}
