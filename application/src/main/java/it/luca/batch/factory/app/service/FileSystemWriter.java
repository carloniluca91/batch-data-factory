package it.luca.batch.factory.app.service;

import it.luca.batch.factory.app.service.dto.FailedFsOperation;
import it.luca.batch.factory.app.service.dto.FsOperation;
import it.luca.batch.factory.app.service.dto.SucceededFsOperation;
import it.luca.batch.factory.app.service.write.AvroDataWriter;
import it.luca.batch.factory.app.service.write.CsvDataWriter;
import it.luca.batch.factory.app.service.write.DataWriter;
import it.luca.batch.factory.model.BatchDataSource;
import it.luca.batch.factory.model.DataSourceType;
import it.luca.batch.factory.model.FileSystemType;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RawLocalFileSystem;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Component
public class FileSystemWriter {

    @Value("${spring.hadoop.hdfs.uri}")
    private String HDFS_URI;

    @Value("${spring.hadoop.hdfs.user}")
    private String HDFS_USER;

    @Value("${spring.hadoop.hdfs.defaultPermissions}")
    private String HDFS_DEFAULT_PERMISSIONS;

    public <T> void writeData(BatchDataSource<T> dataSource, List<T> objectList) throws Exception {

        FileSystemType fileSystemType = dataSource.getFileSystemType();
        switch (fileSystemType) {
            case LOCAL: {

                // Initialize a local FS instance
                FileSystem fs = new RawLocalFileSystem();
                log.info("Successfully initialized instance of {}", RawLocalFileSystem.class.getSimpleName());
                writeToFileSystem(dataSource, objectList, fs);
                break;
            } case HDFS: {

                // Initialize a distributed FS instance
                System.setProperty("HADOOP_USER_NAME", HDFS_USER);
                Configuration configuration = new Configuration();
                configuration.set(FileSystem.FS_DEFAULT_NAME_KEY, HDFS_URI);
                FileSystem fs = DistributedFileSystem.get(configuration);
                log.info("Successfully initialized instance of {}", DistributedFileSystem.class.getSimpleName());
                UserGroupInformation ugi = UserGroupInformation.createRemoteUser(HDFS_USER);

                // Write data
                FsOperation fsOperation = ugi.doAs((PrivilegedAction<FsOperation>) () -> {
                    try {
                        writeToFileSystem(dataSource, objectList, fs);
                        return new SucceededFsOperation();
                    } catch (IOException e) {
                        return new FailedFsOperation(e);
                    }
                });

                // Thrown caught exception, if any
                if (fsOperation.isFailure()) {
                    throw fsOperation.asFailure().getException();
                }
                break;
            } default: throw new NoSuchElementException(String.format("Unmatched %s: %s", FileSystemType.class.getSimpleName(), fileSystemType));
        }
    }

    private <T> void writeToFileSystem(BatchDataSource<T> dataSource, List<T> objectList, FileSystem fs) throws IOException {

        Class<T> dataClass = dataSource.getDataClass();
        String dataClassName = dataClass.getSimpleName();
        String targetPathStr = dataSource.getTargetPath();
        Path targetDirectory = new Path(targetPathStr);
        String fsTypeDescription = dataSource.getFileSystemType().getDescription();

        // Create target directory if necessary
        if (!fs.exists(targetDirectory)) {
            log.warn("Target directory {} does not exist on {}. Creating it now with permissions {}",
                    targetDirectory, fsTypeDescription, HDFS_DEFAULT_PERMISSIONS);
            fs.mkdirs(targetDirectory, FsPermission.valueOf(HDFS_DEFAULT_PERMISSIONS));
            log.info("Successfully created target directory {} with permissions {} on {}",
                    targetDirectory, HDFS_DEFAULT_PERMISSIONS, fsTypeDescription);
        }

        String targetFileName = dataSource.getFileName();
        Path targetFilePath = new Path(String.join("/", targetPathStr, targetFileName));
        FSDataOutputStream fsDataOutputStream = fs.create(targetFilePath, false);
        DataSourceType dataSourceType = dataSource.getType();
        DataWriter<T> dataWriter;
        switch (dataSourceType) {
            case AVRO: dataWriter = new AvroDataWriter<>();break;
            case CSV: dataWriter = new CsvDataWriter<>(); break;
            default: throw new NoSuchElementException(String.format("Unmatched %s: %s", DataSourceType.class.getSimpleName(), dataSourceType));
        }

        dataWriter.write(dataSource, objectList, fsDataOutputStream);
        log.info("Successfully written all of {} instance(s) of {} as {}",
                objectList.size(), dataClassName, dataSource.getType().name().toLowerCase());
    }
}
