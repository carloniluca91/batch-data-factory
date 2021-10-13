package it.luca.batch.factory.configuration.output.compression;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.gzip.GzipUtils;

import java.io.OutputStream;

public class GzipCompression extends Compression {

    @Override
    public String getCompressedFileName(String fileName) {
        return GzipUtils.getCompressedFilename(fileName);
    }

    @Override
    public OutputStream getCompressedStream(OutputStream outputStream) throws CompressorException {
        return streamFactory.createCompressorOutputStream(CompressorStreamFactory.GZIP, outputStream);
    }
}
