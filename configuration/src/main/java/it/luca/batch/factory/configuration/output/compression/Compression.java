package it.luca.batch.factory.configuration.output.compression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import java.io.OutputStream;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = Compression.TYPE,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BZip2Compression.class, name = Compression.BZIP2),
        @JsonSubTypes.Type(value = GzipCompression.class, name = Compression.GZIP),
        @JsonSubTypes.Type(value = XZCompression.class, name = Compression.XZ)
})
public abstract class Compression {

    public static final String TYPE = "type";

    public static final String BZIP2 = "BZIP2";
    public static final String GZIP = "GZIP";
    public static final String XZ = "XZ";

    protected final CompressorStreamFactory streamFactory = new CompressorStreamFactory();

    public abstract String getCompressedFileName(String fileName);

    public abstract OutputStream getCompressedStream(OutputStream outputStream) throws CompressorException;
}
