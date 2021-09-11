package it.luca.batch.factory.app.service.write;

import it.luca.batch.factory.app.configuration.HDFSClientConfiguration;
import it.luca.batch.factory.app.service.dto.FailedFsOperation;
import it.luca.batch.factory.app.service.dto.FsOperation;
import it.luca.batch.factory.app.service.dto.SucceededFsOperation;
import it.luca.batch.factory.model.output.DataSourceOutput;
import it.luca.batch.factory.model.output.DataSourceOutput.FileSystemType;
import it.luca.batch.factory.model.output.DataSourceOutput.OutputType;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RawLocalFileSystem;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Component for writing data on a fileSystem
 */

@Slf4j
@Component
public class FileSystemWriter {

    @Autowired
    private HDFSClientConfiguration clientConfiguration;

    /**
     * Write batch of records
     * @param dataClass record's class
     * @param batch {@link List} of records instances of T
     * @param dataSourceOutput {@link DataSourceOutput}
     * @param <T> record's type
     * @throws Exception if write operation fails
     */

    public <T> void writeData(Class<T> dataClass, List<T> batch, DataSourceOutput dataSourceOutput) throws Exception {

        FileSystemType fileSystemType = dataSourceOutput.getFileSystemType();
        switch (fileSystemType) {
            case LOCAL: {

                // Initialize a local FS instance
                FileSystem fs = new RawLocalFileSystem();
                log.info("Successfully initialized instance of {}", RawLocalFileSystem.class.getSimpleName());
                writeToFileSystem(dataClass, batch, dataSourceOutput, fs);
                break;
            } case HDFS: {

                // Set up Hadoop client configuration and initialize a distributed FS instance
                String hadoopUserName = clientConfiguration.get(HDFSClientConfiguration.USER);
                System.setProperty("HADOOP_USER_NAME", hadoopUserName);
                Configuration configuration = new Configuration();
                configuration.set(FileSystem.FS_DEFAULT_NAME_KEY, clientConfiguration.get(HDFSClientConfiguration.URI));
                configuration.set("ipc.client.connect.timeout", clientConfiguration.get(HDFSClientConfiguration.CONNECTION_TIMEOUT_MILLIS));
                configuration.set("ipc.client.connect.max.retries.on.timeouts", clientConfiguration.get(HDFSClientConfiguration.MAX_RETRIES));
                FileSystem fs = DistributedFileSystem.get(configuration);
                log.info("Successfully initialized instance of {}", DistributedFileSystem.class.getSimpleName());
                UserGroupInformation ugi = UserGroupInformation.createRemoteUser(hadoopUserName);

                // Write data
                FsOperation fsOperation = ugi.doAs((PrivilegedAction<FsOperation>) () -> {
                    try {
                        writeToFileSystem(dataClass, batch, dataSourceOutput, fs);
                        return new SucceededFsOperation();
                    } catch (IOException e) {
                        return new FailedFsOperation(e);
                    }
                });

                // Thrown previously caught exception, if any (work around in order to run FS operation)
                if (fsOperation.isFailure()) {
                    throw fsOperation.asFailure().getException();
                }
                break;
            } default: throw new NoSuchElementException(String.format("Unmatched %s: %s", FileSystemType.class.getSimpleName(), fileSystemType));
        }
    }

    /**
     * Write batch of records on file system
     * @param dataClass record's class
     * @param batch {@link List} of records
     * @param output {@link DataSourceOutput}
     * @param fs {@link FileSystem} where data will be saved
     * @param <T> record's type
     * @throws IOException if write operation fails
     */

    private <T> void writeToFileSystem(Class<T> dataClass, List<T> batch, DataSourceOutput output, FileSystem fs) throws IOException {

        String targetPathStr = output.getPath();
        Path targetDirectory = new Path(targetPathStr);
        String fsTypeDescription = output.getFileSystemType().getDescription();

        // Create target directory if necessary
        if (!fs.exists(targetDirectory)) {
            String defaultPermissions = clientConfiguration.get(HDFSClientConfiguration.DEFAULT_PERMISSIONS);
            log.warn("Target directory {} does not exist on {}. Creating it now with permissions {}",
                    targetDirectory, fsTypeDescription, defaultPermissions);
            fs.mkdirs(targetDirectory, FsPermission.valueOf(defaultPermissions));
            log.info("Successfully created target directory {} with permissions {} on {}",
                    targetDirectory, defaultPermissions, fsTypeDescription);
        }

        String targetFileName = output.getFileNameWithDate();
        Path targetFilePath = new Path(String.join("/", targetPathStr, targetFileName));
        FSDataOutputStream fsDataOutputStream = fs.create(targetFilePath, false);
        OutputType outputType = output.getType();
        DataWriter<T> dataWriter;
        switch (outputType) {
            case AVRO: dataWriter = new AvroDataWriter<>();break;
            case CSV: dataWriter = new CsvDataWriter<>(targetFileName.toLowerCase().endsWith(".csv.gz")); break;
            default: throw new NoSuchElementException(String.format("Unmatched %s: %s", OutputType.class.getSimpleName(), outputType));
        }

        String dataClassName = dataClass.getSimpleName();
        String outputDataFormat = outputType.name().toLowerCase();
        String fsDescription = output.getFileSystemType().getDescription();
        log.info("Starting to write all of {} instance(s) of {} as .{} file on {} at path {}",
                batch.size(), dataClassName, outputDataFormat, fsDescription, targetFilePath);
        dataWriter.write(dataClass, batch, fsDataOutputStream);
        log.info("Successfully written all of {} instance(s) of {} as .{} file on {} at path {}",
                batch.size(), dataClassName, outputDataFormat, fsDescription, targetFilePath);
    }
}
